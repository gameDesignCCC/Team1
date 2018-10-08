// I don't know if this is what we're going to end up using.

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

    //Check Stage Collision

    public boolean checkStageCollisionT(){
        return(minY <= 0);
    }

    public boolean checkStageCollisionB(){
        return(maxY >= windowSizeY);
    }

    public boolean checkStageCollisionL(){
        return(minX <= 0);
    }

    public boolean checkStageCollisionR(){
        return(maxX >= windowSizeX);
    }

    //Getters

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

    //Setters

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

