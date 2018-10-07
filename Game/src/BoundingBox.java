
public class BoundingBox {

    double windowSizeX = MainApplication.windowSizeX;
    double windowSizeY = MainApplication.windowSizeY;

    double minX, minY, maxX, maxY;

    public BoundingBox(double minX, double minY, double maxX, double maxY){

        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

    }

    public boolean checkCollision(BoundingBox other){
        return(maxX >= other.minX && minX <= other.maxX && maxY >= other.minY && minY <= other.maxX);
    }

    public boolean checkStageCollisionL(){
        return(minX <= 0);
    }

    public boolean checkStageCollisionR(){
        return(maxX >= windowSizeX);
    }

    public boolean checkStageCollisionT(){
        return(minY <= 0);
    }

    public boolean checkStageCollisionB(){
        return(maxY >= windowSizeY);
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

    public void setMaxX(double newMaxX){
        this.maxX = newMaxX;
    }

    public void setMinY(double newMinY){
        this.minY = newMinY;
    }

    public void setMaxY(double newMaxY){
        this.maxY = newMaxY;
    }

    public void setBounds(double newMinX, double newMinY, double newMaxX, double newMaxY){
        System.out.println("setbounds");
        this.minX = newMinX;
        this.minY = newMinY;
        this.maxX = newMaxX;
        this.maxY = newMaxY;

    }


}

