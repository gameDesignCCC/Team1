import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {

    // Get Window Size
    private double windowSizeX = MainApplication.windowSizeX;
    private double windowSizeY = MainApplication.windowSizeY;

    // Player is in jump animation
    private static boolean inJumpAnimaion = false;

    // Player "HitBox"
    private BoundingBox bbox = new BoundingBox(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight());

    // Player Speed When Moved
    private static double playerSpeed = 10.0;

    // Player Velocity
    private static double vX = 0.0;
    private static double vY = 0.0;

    // Gravity
    static double g = 0.5;

    Player(double x, double y, double width, double height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);

    }

    // Check Collisions

    public boolean isCollidingLeft() {
        return bbox.checkStageCollisionL();
    }

    public boolean isCollidingRight() {
        return bbox.checkStageCollisionR();
    }

    public boolean isCollidingBottom() {
        return bbox.checkStageCollisionB();
    }

    public boolean isCollidingTop() {
        return bbox.checkStageCollisionT();
    }


    // Player Movement

    public void move(){
        this.setX(this.getX() + vX);
        this.setY(this.getY() + vY);
    }

    // Player Jump
    public void jump() {
    }

    // Player Gravity
    /*public void enforceGravity() {

        if (this.getY() + this.getHeight() >= windowSizeY) {
            vY = 0;
            this.setY(windowSizeY - this.getHeight());
        } else {
            this.setY(this.getY() + g);
        }

    }*/

    // Velocity Getters
    // Position and size can be called fromm Rectangle.

    public double getVelocityY() {
        return vY;
    }

    public double getVelocityX() {
        return vX;
    }

    // Player Update
    // Not sure if this should be put in MainApplication.
    public void onUpdate(boolean up, boolean left, boolean right) {


        vX = 0;
        if(!inJumpAnimaion) {
            vY = 0;
        }

        // Player Controls

        if (right && !isCollidingRight()) {
            vX += playerSpeed;
        }

        if (left && !isCollidingLeft()) {
            vX += playerSpeed * -1;
        }

        if(inJumpAnimaion){
            vY += g;
        }

        if (up && !inJumpAnimaion){

            vY += -10.0;
            inJumpAnimaion = true;

        }

        if(this.getY() + this.getHeight() >= windowSizeY){
            inJumpAnimaion = false;
            this.setY(windowSizeY - this.getHeight());
        }

        move();

        // Reset BoundingBox bounds to current position.
        bbox.setBounds(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight());

        // I couldn't figure out how to get jumping to work so I made this.
        //enforceGravity();

        System.out.println(vX + " " + vY);
    }

}