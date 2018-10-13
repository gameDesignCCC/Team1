/*
 * Author(s): Jacob Dixon @jacobrdixon.com
 * Date: 6/10/2018 - 13/10/2018
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Player extends ImageView {

    // Get Window Size
    private double WINDOWSIZEX = MainApplication.WINDOWSIZEX;
    private double WINDOWSIZEY = MainApplication.WINDOWSIZEY;

    // Player is in jump animation
    private static boolean inJumpAnimation = false;

    // Player Speed When Moved
    private static double playerSpeed = 10.0;

    // Player Velocity
    private static double vX = 0.0;
    private static double vY = 0.0;

    private boolean isMovingRight = false;
    private boolean isMovingLeft = false;

    // Gravity
    static double g = 0.5; /* This is really sensitive, no touchy touchy. */

    Collision playerCollision = new Collision();

    Rectangle box = MainApplication.box;

    Player(double x, double y, double width, double height, Image sprite) {
        this.setX(x);
        this.setY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setImage(sprite);

    }

    // Check Collisions

    public boolean checkStageCollisionTop(){
        return(this.getY() <= 0);
    }

    public boolean checkStageCollisionBottom(){
        return(this.getY()  + this.getFitHeight() >= WINDOWSIZEY);
    }

    public boolean checkStageCollisionLeft(){
        return(this.getX() <= 0);
    }

    public boolean checkStageCollisionRight(){
        return(this.getX() + this.getFitWidth() >= WINDOWSIZEX);
    }


    // Player Movement

    public void move(){
        this.setX(this.getX() + vX);
        this.setY(this.getY() + vY);
    }

    // Get Velocity
    // Position and size can be called fromm ImageView.

    public double getVelocityY() {
        return vY;
    }

    public double getVelocityX() {
        return vX;
    }

    // Player Update
    public void onUpdate(boolean up, boolean left, boolean right) {
        vX = 0;


        if(!inJumpAnimation) {
            vY = 0;
        }

        // Player Controls

        if (right && !checkStageCollisionRight() && !(this.getX() + this.getFitWidth() + playerSpeed >= WINDOWSIZEX) && !(playerCollision.isCollidingRight(box,this))) {
            vX += playerSpeed;
            isMovingRight = true;

        }else if(isMovingRight && this.getX() + this.getFitWidth() + playerSpeed >= WINDOWSIZEX) {
            setX(WINDOWSIZEX - this.getFitWidth());
            isMovingRight = false;

        }

        if (left && !checkStageCollisionLeft() && !(this.getX() - playerSpeed <= 0)
                && !(playerCollision.isCollidingLeft(box,this))){
            vX += playerSpeed * -1;
            isMovingLeft = true;

        }else if(isMovingLeft && this.getX() - playerSpeed <= 0) {
            setX(0);
            isMovingLeft = false;

        }

        if(inJumpAnimation){
            if(!(this.getY() + this.getFitHeight() >= WINDOWSIZEY)) {
                vY += g;
            }else if(this.getY() + this.getFitHeight() >= WINDOWSIZEY){
                vY = 0;
            }
        }

        if (up && !inJumpAnimation){
            vY += -10.0; /* This is also really sensitive, no touchy touchy. */
            inJumpAnimation = true;

        }

        if(this.getY() + this.getFitHeight() >= WINDOWSIZEY){
            inJumpAnimation = false;
            this.setY(WINDOWSIZEY - this.getFitHeight());
        }

        move();

    }

}