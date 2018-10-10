/*
 * Author(s): Jacob Dixon @jacobrdixon.com
 * Date: 6/10/2018 - 10/10/2018
 */

// I don't know if we're going to use this.

public class BoundingBox {

    // Get Window Size
    double windowSizeX = MainApplication.windowSizeX;
    double windowSizeY = MainApplication.windowSizeY;

    double minX, minY, maxX, maxY;

    public BoundingBox(double minX, double minY, double maxX, double maxY){
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    // Check Other BoundingBox Collision

    public boolean checkCollision(BoundingBox other){
        return(maxX >= other.minX && minX <= other.maxX && maxY >= other.minY && minY <= other.maxX);
    }

    public boolean checkCollisionTop(BoundingBox other){
        return(minY <= other.maxY);
    }

    public boolean checkCollisionBottom(BoundingBox other){
        return(maxY <= other.minY);
    }

    public boolean checkCollisionLeft(BoundingBox other){
        return(minX >= other.maxX);
    }

    public boolean checkCollisionRight(BoundingBox other){
        return(maxX <= other.minX);
    }

    //Check Stage Collision

    public boolean checkStageCollisionTop(){
        return(minY <= 0);
    }

    public boolean checkStageCollisionBottom(){
        return(maxY >= windowSizeY);
    }

    public boolean checkStageCollisionLeft(){
        return(minX <= 0);
    }

    public boolean checkStageCollisionRight(){
        return(maxX >= windowSizeX);
    }

    //Get Bounds

    public double getMinX(){
        return this.minX;
    }

    public double getMinY(){
        return this.minY;
    }

    public double getMaxX(){
        return this.maxX;
    }

    public double getMaxY(){
        return this.maxY;
    }

    //Set Bounds

    public void setMinX(double newMinX){
        this.minX = newMinX;
    }

    public void setMinY(double newMinY){
        this.minY = newMinY;
    }

    public void setMaxX(double newMaxX){
        this.maxX = newMaxX;
    }

    public void setMaxY(double newMaxY){
        this.maxY = newMaxY;
    }

    public void setBounds(double newMinX, double newMinY, double newMaxX, double newMaxY){
        this.minX = newMinX;
        this.minY = newMinY;
        this.maxX = newMaxX;
        this.maxY = newMaxY;

    }


}

