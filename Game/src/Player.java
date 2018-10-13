/*
 * Author(s): Jacob Dixon @jacobrdixon.com
 * Date: 6/10/2018 - 10/10/2018
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

// This should probably be changed to ImageView.
public class Player extends ImageView {

    // Get Window Size
    private double WINDOWSIZEX = MainApplication.WINDOWSIZEX;
    private double WINDOWSIZEY = MainApplication.WINDOWSIZEY;

    // Player is in jump animation
    private static boolean inJumpAnimaion = false;

    // Player Speed When Moved
    private static double playerSpeed = 10.0;

    // Player Velocity
    private static double vX = 0.0;
    private static double vY = 0.0;

    private boolean isMovingRight = false;
    private boolean isMovingLeft = false;

    // Gravity
    static double g = 0.5; /* This is really sensitive, no touchy touchy. */

    Collision c = new Collision();
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

    // Player Update
    // Not sure if this should be put in MainApplication.
    public void onUpdate(boolean up, boolean left, boolean right) {
        vX = 0;


        if(!inJumpAnimaion) {
            vY = 0;
        }

        // Player Controls

        if (right && !checkStageCollisionRight() && !(this.getX() + this.getFitWidth() + playerSpeed >= WINDOWSIZEX) && !(c.isCollidingRight(box,this))) {
            vX += playerSpeed;
            isMovingRight = true;
        }else if(isMovingRight && this.getX() + this.getFitWidth() + playerSpeed >= WINDOWSIZEX) {
            setX(WINDOWSIZEX - this.getFitWidth());
            isMovingRight = false;

        }

        if (left && !checkStageCollisionLeft() && !(this.getX() - playerSpeed <= 0) && !(c.isCollidingLeft(box,this))){
            vX += playerSpeed * -1;
            isMovingLeft = true;
        }else if(isMovingLeft && this.getX() - playerSpeed <= 0) {
            setX(0);
            isMovingLeft = false;

        }

        if(inJumpAnimaion){
            if(!(this.getY() + this.getFitHeight() >= WINDOWSIZEY)) {
                vY += g;
            }else if(this.getY() + this.getFitHeight() >= WINDOWSIZEY){
                vY = 0;
            }
        }

        if (up && !inJumpAnimaion){

            vY += -10.0; /* This is also really sensitive, no touchy touchy. */
            inJumpAnimaion = true;

        }

        if(this.getY() + this.getFitHeight() >= WINDOWSIZEY){
            inJumpAnimaion = false;
            this.setY(WINDOWSIZEY - this.getFitHeight());
        }

        move();

    }

}