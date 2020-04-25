package com.foodmobile.server.util;

/**
 * Generic rectangle
 */
public class Rect{
    double x;
    double y;
    double w;
    double h;

    /**
     * Creates a rectangle with an origin (x,y specifying the top left corner), a width, and a height.
     * @param x origin x coordinate
     * @param y origin y coordinate
     * @param w width of the rectangle
     * @param h height of the rectangle
     */
    public Rect(double x, double y, double w, double h){
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    /**
     * Checks if the rectangle contains a specific point n.
     * @param n
     * @return true if the rectangle contains the point, else false
     */
    public boolean contains(PointLike n){
        if(n.getX() >= this.x && n.getX() <= this.x+w){
            if(n.getY() <= this.y && n.getY() >= this.y - h){
                return true;
            }
        }
        return false;
    }
}