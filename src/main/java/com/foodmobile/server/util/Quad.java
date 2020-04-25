package com.foodmobile.server.util;


import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of a QuadTree
 */
public class Quad {
    protected int MAX_NODES_PER_QUADRANT = 4;
    private Rect rect;
    private final Hashtable<String,Node> connectedNodes = new Hashtable<>();
    private final List<Node> nodes = new LinkedList<>();
    private boolean divided = false;
    private Quad parent = null;
    private Quad topLeft;
    private Quad topRight;
    private Quad botLeft;
    private Quad botRight;

    /**
     * Initialize a quad tree with an initial rectangle
     * @param rect Rectangle specifying the bounds of the tree.
     */
    public Quad(Rect rect){
        this.rect = rect;
        if(this.rect.w <= 1 || this.rect.h <= 1){
            // This level is so small that we shouldn't be subdividing too much.
            MAX_NODES_PER_QUADRANT = 100;
        }
    }
    private Quad(Rect rect, Quad parent){
        this.rect = rect;
        MAX_NODES_PER_QUADRANT = parent.MAX_NODES_PER_QUADRANT*2;
        this.parent = parent;
    }

    private void subdivide(){
        this.topLeft = new Quad(new Rect(this.rect.x,this.rect.y,0.5*this.rect.w,0.5*this.rect.h),this);
        this.topRight = new Quad(new Rect(this.rect.x+(0.5*this.rect.w),this.rect.y,0.5*this.rect.w,0.5*this.rect.h),this);
        this.botLeft = new Quad(new Rect(this.rect.x,this.rect.y - (0.5*this.rect.h),0.5*this.rect.w,0.5*this.rect.h),this);
        this.botRight = new Quad(new Rect(this.rect.x+(0.5*this.rect.w),this.rect.y - (0.5*this.rect.h),0.5*this.rect.w,0.5*this.rect.h),this);
    }

    /**
     * Inserts a node forcing updates to any node already present
     * @param n Node to insert
     * @return true if the node is inserted else false
     */
    public boolean insert(Node n){
        return this.insert(n,true);
    }

    /**
     * Inserts a node into the tree optionally forcing updates.
     * @param n Node to be inserted
     * @param shouldUpdate Specifies whether preexisting nodes should be updated based off of the information stored in n.
     * @return true if the node was inserted else false
     */
    public boolean insert(Node n, boolean shouldUpdate){
        if(!this.rect.contains(n)){
            return false;
        }
        if(this.parent == null && shouldUpdate){
            if(this.connectedNodes.containsKey(n.username)) {
                this.connectedNodes.get(n.username).update(n.lat, n.lon);
                return true;
            }else{
                this.connectedNodes.put(n.username,n);
            }
        }

        if(this.nodes.size() < MAX_NODES_PER_QUADRANT && !this.divided){
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

    /**
     * Removes a node with the specified username
     * @param username Username of the node that should be removed.
     * @return true if the node was removed else false
     */
    public boolean remove(String username){
        if(this.connectedNodes.containsKey(username)){
            var node = this.connectedNodes.remove(username);
            node.removeFromParent();
            return true;
        }
        return false;
    }

    /**
     * Finds all nodes in a certain region of the tree
     * @param p Point specifying the region
     * @param list List reference, populated with the does found by search
     */
    public void search(PointLike p, List<Node> list){
        if(!this.rect.contains(p)){return;}
        if(this.divided){
            if(this.rect.w <= 1 || this.rect.h <= 1){
                synchronized (this.nodes) {
                    list.addAll(this.nodes);
                }
            }
            this.topLeft.search(p,list);
            this.topRight.search(p,list);
            this.botLeft.search(p,list);
            this.botRight.search(p,list);

        }else{
            list.addAll(this.nodes);
        }
    }

    /**
     * Gets if the current tree, or subtree is divided.
     * @return true if the current tree or subtree is divided.
     */
    public boolean isDivided(){
        return this.divided;
    }

    /**
     * Checks if quadrant is divided.
     * @param i Quadrant number ordered clockwise starting at top left == 1
     * @return true if the specified quadrant is divided.
     */
    public boolean isQuadrantDivided(int i){
        if(!this.divided){return false;}
        if(i == 1){
            return topLeft.divided;
        }else if(i == 2){
            return topRight.divided;
        }else if(i == 3){
            return botRight.divided;
        }else if(i == 4){
            return botLeft.divided;
        }
        return false;
    }

    /**
     * Restructures a node after it has been updated.
     * @param n Node to be restructured (replaced in the tree).
     * @return true if the node was successfully restructured.
     */
    public boolean restructureNode(Node n){
        if(!this.nodes.contains(n)){return false;}else{this.nodes.remove(n);}
        var tree = this;
        while(!tree.insert(n,false)){
            if(tree.parent == null){
                return false;
            }
            tree = tree.parent;

        }
        return true;
    }

    /**
     * Gets the number of nodes in the tree
     * @return number of nodes in tree
     */
    public long getNodeCount(){
        long i = this.nodes.size();
        if(this.divided){
            i+= this.topLeft.getNodeCount();
            i+= this.topRight.getNodeCount();
            i+= this.botLeft.getNodeCount();
            i+= this.botRight.getNodeCount();
        }
        return i;
    }

    /**
     * Gets the number of nodes in a quadrant
     * @param qn quadrant number
     * @return number of nodes in the specified quadrant.
     * @throws Exception If tree is not divided, an exception is thrown.
     */
    public long getNodeCountForQuadrant(short qn)throws Exception{
        if(this.divided){
            if(qn == 1){
                return this.topLeft.getNodeCount();
            }else if(qn == 2){
                return this.topRight.getNodeCount();
            }else if(qn == 3){
                return this.botRight.getNodeCount();
            }else if(qn == 4){
                return this.botLeft.getNodeCount();
            }
        }
        throw new Exception("Tree not divided or improper quadrant provided");
    }

    /**
     * Removes a specific node from the tree.
     * @param n The node to be removed.
     */
    public void removeNode(Node n){
        synchronized (this.nodes) {
            this.nodes.removeIf(node -> node == n);
        }
        if(this.parent != null && this.parent.getNodeCount() < this.parent.MAX_NODES_PER_QUADRANT){
            this.parent.destroyDivision(parent);
        }
    }

    private void destroyDivision(Quad newParent){
        if(!this.divided){
            while(this.nodes.size() > 0){
                var node = this.nodes.remove(0);
                newParent.insert(node,false);
            }

        }else{
            this.divided = false;
            this.topLeft.destroyDivision(newParent);
            this.topRight.destroyDivision(newParent);
            this.botLeft.destroyDivision(newParent);
            this.botRight.destroyDivision(newParent);
            this.topLeft = null;
            this.topRight = null;
            this.botLeft = null;
            this.botRight = null;

        }
    }
}
