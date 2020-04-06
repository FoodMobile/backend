package com.foodmobile.server.util;

import com.foodmobile.server.websocket.Node;


import org.springframework.web.socket.TextMessage;

import java.util.LinkedList;
import java.util.List;

public class Quad {
    private int MAX_NODES_PER_QUADRANT = 4;
    Rect rect;
    List<Node> nodes = new LinkedList<>();
    private boolean divided = false;
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

    private void subdivide(){
        this.topLeft = new Quad(new Rect(this.rect.x,this.rect.y,0.5*this.rect.w,0.5*this.rect.h));
        this.topRight = new Quad(new Rect(this.rect.x+(0.5*this.rect.w),this.rect.y,0.5*this.rect.w,0.5*this.rect.h));
        this.botLeft = new Quad(new Rect(this.rect.x,this.rect.y - (0.5*this.rect.h),0.5*this.rect.w,0.5*this.rect.h));
        this.botRight = new Quad(new Rect(this.rect.x+(0.5*this.rect.w),this.rect.y - (0.5*this.rect.h),0.5*this.rect.w,0.5*this.rect.h));
    }

    public boolean insert(Node n){
        if(!this.rect.contains(n)){
            return false;
        }

        if(this.nodes.size() < MAX_NODES_PER_QUADRANT){
            this.nodes.add(n);
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

    public boolean broadCast(double lat, double lon, TextMessage message){
        if(!this.rect.contains(new PointLike() {
            @Override
            public double getX() {
                return lon;
            }

            @Override
            public double getY() {
                return lat;
            }
        })){return false;}

        if(this.divided){
            if(this.rect.w <= 1 || this.rect.y <= 1){
                //send message they are roughly within 69 miles
                this.nodes.forEach(n -> n.sendMessage(message));
            }
            return this.topLeft.broadCast(lat,lon,message) || this.topRight.broadCast(lat,lon,message) ||
                    this.botLeft.broadCast(lat,lon,message) || this.botRight.broadCast(lat,lon,message);
        }else{
            this.nodes.forEach(n -> n.sendMessage(message));
            return true;
        }

    }

    public void removeNode(Node n){

    }
}
