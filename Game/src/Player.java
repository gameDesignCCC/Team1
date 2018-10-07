import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {

    private double windowSizeX = MainApplication.windowSizeX;
    private double windowSizeY = MainApplication.windowSizeY;

    private static double vX = 4.0;
    private static double vY = 2.0;

    // Gravity

    static double g = 2;

    Player(double x, double y, double width, double height){
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);

    }

    // Check Collisions

    public boolean isCollidingLeft(){

        if(this.getX() <= 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean isCollidingRight(){

        if(this.getX() + this.getWidth() >= windowSizeX){
            return true;
        }else {
            return false;
        }
    }

    public boolean isCollidingBottom(){

        if(this.getY() + this.getHeight() >= windowSizeY){
            return true;
        }else {
            return false;
        }
    }

    public boolean isCollidingTop(){

        if(this.getY() <= 0){
            return true;
        }else {
            return false;
        }
    }


    // Player Movement

    public void moveRight(){
        this.setX( this.getX() + vX);
    }

    public void moveLeft(){
        this.setX( this.getX() - vX);
    }

    // Player Jump

    public void jump(){

        if( vY > -20 ) {
            vY -= g ;
            this.setY(this.getY() + vY);
        }

    }

    // Player Gravity

    public void enforceGravity(boolean enabled){

        if(enabled) {

            //this.setY(this.getY() + g);

            if (this.getY() + this.getHeight() >= windowSizeY) {
                vY = 0;
                this.setY(windowSizeY - this.getHeight());
            }else{
                this.setY(this.getY() + g);
            }

        }
    }

    // Getters

    public double getVelocityY(){
        return vY;
    }

    public double getVelocityX(){
        return vX;
    }

}
