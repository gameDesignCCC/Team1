import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {

    private double windowSizeX = MainApplication.windowSizeX;
    private double windowSizeY = MainApplication.windowSizeY;

    private static double vX = 4.0;
    private static double vY = 2.0;

    BoundingBox bbox = new BoundingBox(this.getX(), this.getX() + this.getWidth(), this.getY(), this.getY() + this.getHeight());

    // Gravity

    static double g = 2;

    Player(double x, double y, double width, double height){
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);

    }

    // Check Collisions

    public boolean isCollidingStageLeft(){
        System.out.println("called colliding stage left");
        return bbox.checkStageCollisionL();
    }

    public boolean isCollidingStageRight(){
        return bbox.checkStageCollisionR();
    }

    public boolean isCollidingStageBottom(){

        if(this.getY() + this.getHeight() >= windowSizeY){
            return true;
        }else {
            return false;
        }
    }

    public boolean isCollidingStageTop(){

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

    // Player Jump [WIP]

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

            if (isCollidingStageBottom()) {
                vY = 0;
                this.setY(windowSizeY - this.getHeight());
            }else{
                this.setY(this.getY() + g);
            }

        }
    }

    public void onUpdate(boolean up, boolean left, boolean right) {

        bbox.setBounds(this.getX(), this.getX() + this.getWidth(), this.getY(), this.getY() + this.getHeight());

        // Player Controls
        if (right) {
            if (!isCollidingStageRight()) {
                this.moveRight();
            }
        }
        if (left) {
            if (!isCollidingStageLeft()) {
                this.moveLeft();
            }
        }
        if (up) {
            if (!isCollidingStageTop()) {
                this.jump();
            }
        }

        // I couldn't figure out how to get jumping to work so I made this for now.
        this.enforceGravity(true);

    }

    // Getters

    public double getVelocityY(){
        return vY;
    }

    public double getVelocityX(){
        return vX;
    }

}
