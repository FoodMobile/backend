package com.foodmobile.server.util;

public class Node implements PointLike {
    double lat;
    double lon;
    public String username;

    private Quad parent;


    public Node(String username, double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.username = username;
    }

    public void setParent(Quad parent) {
        this.parent = parent;
    }


    public void removeFromParent(){
        if(parent == null)
            return;
        this.parent.removeNode(this);
    }

    public void update(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.parent.restructureNode(this);
    }

    @Override
    public double getX() {
        return lat;
    }

    @Override
    public double getY() {
        return lon;
    }


}
