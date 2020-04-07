package com.foodmobile.server.util;

import com.foodmobile.server.persistence.models.LocationUpdate;
import com.foodmobile.server.websocket.Node;


import org.springframework.web.socket.TextMessage;

import java.util.LinkedList;
import java.util.List;

public class Quad {
    private int MAX_NODES_PER_QUADRANT = 4;
    Rect rect;
    final List<Node> nodes = new LinkedList<>();
    private boolean divided = false;
    Quad parent = null;
    Quad topLeft;
    Quad topRight;
    Quad botLeft;
    Quad botRight;

    public Quad(Rect rect){
        this.rect = rect;
        if(this.rect.w <= 1 || this.rect.h <= 1){
            // This level is so small that we shouldn't be subdividing too much.
            MAX_NODES_PER_QUADRANT = 100;
        }
    }
    public Quad(Rect rect, Quad parent){
        this.rect = rect;
        if(this.rect.w <= 1 || this.rect.h <= 1){
            // This level is so small that we shouldn't be subdividing too much.
            MAX_NODES_PER_QUADRANT = 100;
        }
        this.parent = parent;
    }

    private void subdivide(){
        this.topLeft = new Quad(new Rect(this.rect.x,this.rect.y,0.5*this.rect.w,0.5*this.rect.h),this);
        this.topRight = new Quad(new Rect(this.rect.x+(0.5*this.rect.w),this.rect.y,0.5*this.rect.w,0.5*this.rect.h),this);
        this.botLeft = new Quad(new Rect(this.rect.x,this.rect.y - (0.5*this.rect.h),0.5*this.rect.w,0.5*this.rect.h),this);
        this.botRight = new Quad(new Rect(this.rect.x+(0.5*this.rect.w),this.rect.y - (0.5*this.rect.h),0.5*this.rect.w,0.5*this.rect.h),this);
    }

    public boolean insert(Node n){
        if(!this.rect.contains(n)){
            return false;
        }

        if(this.nodes.size() < MAX_NODES_PER_QUADRANT){
            synchronized (this.nodes) {
                n.setParent(this);
                this.nodes.add(n);
            }
            return true;
        }else{
            if(!this.divided){this.subdivide();this.divided = true;}
            while(this.nodes.size() > 0){
                var node = this.nodes.remove(0);
                var _ignore = topLeft.insert(node) || topRight.insert(node) || botLeft.insert(node) || botRight.insert(node);
                if(!_ignore){System.out.println("Couldnt restructure quadrant!");}
            }
            return topLeft.insert(n) || topRight.insert(n) || botLeft.insert(n) || botRight.insert(n);
        }
    }

    public boolean broadCast(LocationUpdate update){
        if(!this.rect.contains(update)){return false;}

        if(this.divided){
            if(this.rect.w <= 1 || this.rect.y <= 1){
                //send message they are roughly within 69 miles
                this.nodes.forEach(n -> n.sendMessage(update));
            }
            return this.topLeft.broadCast(update) || this.topRight.broadCast(update) ||
                    this.botLeft.broadCast(update) || this.botRight.broadCast(update);
        }else{
            this.nodes.forEach(n -> n.sendMessage(update));
            return true;
        }

    }

    public boolean restructureNode(Node n){
        if(!this.nodes.contains(n)){return false;}else{this.nodes.remove(n);}
        var tree = this;
        while(!tree.insert(n)){
            tree = tree.parent;
            if(tree.parent == null){
                return false;
            }
        }
        return true;
    }

    public void removeNode(Node n){
        synchronized (this.nodes) {
            this.nodes.removeIf(node -> node == n);
        }
    }
}
