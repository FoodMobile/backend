package com.foodmobile.server.util;

public class Rect{
    double x;
    double y;
    double w;
    double h;
    public Rect(double x, double y, double w, double h){
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }
    public boolean contains(PointLike n){
        if(n.getX() >= this.x && n.getX() <= this.x+w){
            if(n.getY() <= this.y && n.getY() >= this.y - h){
                return true;
            }
        }
        return false;
    }
}