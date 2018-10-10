/*
 * Author(s): Jacob Dixon @jacobrdixon.com
 * Date: 6/10/2018 - 10/10/2018
 */

import javafx.scene.shape.Rectangle;

// This should probably be changed to ImageView.
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

    private boolean isMovingRight = false;
    private boolean isMovingLeft = false;

    // Gravity
    static double g = 0.5; /* This is really sensitive, no touchy touchy. */

    Player(double x, double y, double width, double height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);

        this.bbox.setBounds(x, y, width, height);

        this.setOpacity(0);

    }

    // Check Collisions

    public boolean isCollidingLeft() {
        return bbox.checkStageCollisionLeft();
    }

    public boolean isCollidingRight() {
        return bbox.checkStageCollisionRight();
    }

    public boolean isCollidingBottom() {
        return bbox.checkStageCollisionBottom();
    }

    public boolean isCollidingTop() {
        return bbox.checkStageCollisionTop();
    }


    // Player Movement

    public void move(){
        this.setX(this.getX() + vX);
        this.setY(this.getY() + vY);
    }

    // Player Jump
    public void jump() {
    }


    // Get Velocity
    // Position and size can be called fromm Rectangle.

    public double getVelocityY() {
        return vY;
    }

    public double getVelocityX() {
        return vX;
    }

    // Get BoundingBox
    public BoundingBox getBbox(){
        return bbox;
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
            isMovingRight = true;
        }

        if (left && !isCollidingLeft()) {
            vX += playerSpeed * -1;
            isMovingLeft = true;
        }

        if(inJumpAnimaion){
            vY += g;
        }

        if (up && !inJumpAnimaion){

            vY += -10.0; /* This is also really sensitive, no touchy touchy. */
            inJumpAnimaion = true;

        }

        if(this.getY() + this.getHeight() >= windowSizeY){
            inJumpAnimaion = false;
            this.setY(windowSizeY - this.getHeight());
        }

        move();

        // Reset BoundingBox bounds to current position.
        bbox.setBounds(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight());

    }

}