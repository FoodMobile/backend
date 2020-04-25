package com.foodmobile.server.util;

/**
 * Node to be used in the Quad tree
 */
public class Node implements PointLike {
    double lat;
    double lon;
    /**
     * The username of the truck
     */
    public String username;
    private Quad parent;

    /**
     * Creates a node with a username and location
     * @param username Username of the truck
     * @param lat latitude of the truck
     * @param lon longitude of the truck
     */
    public Node(String username, double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.username = username;
    }

    /**
     * Sets the parent tree of the node
     * @param parent Parent tree for this node
     */
    public void setParent(Quad parent) {
        this.parent = parent;
    }

    /**
     * Removes this node from its parent.
     */
    public void removeFromParent(){
        if(parent == null)
            return;
        this.parent.removeNode(this);
    }

    /**
     * Updates the information of the node
     * @param lat new latitude
     * @param lon new longitude
     */
    public void update(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.parent.restructureNode(this);
    }

    @Override
    /**
     * Gets the latitude (x coordinate)
     */
    public double getX() {
        return lat;
    }

    @Override
    /**
     * Gets the longitude (y coordinate)
     */
    public double getY() {
        return lon;
    }


}
