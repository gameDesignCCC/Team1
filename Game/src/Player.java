/*
 * Author(s): Jacob Dixon @jacobrdixon.com
 * Date: 24/10/2018
 */

/*
 * KNOW ISSUES:
 * Player does not use top or bottom collision detection.
 */


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sun.applet.Main;

public class Player extends ImageView {

    // Player is in jump animation
    private static boolean inJumpAnimation = false;

    // Player Speed When Moved
    private static double playerSpeed = 10.0;

    // Player Velocity
    private static double vX = 0.0;
    private static double vY = 0.0;

    // Don't rely on these doing anything useful elsewhere in the application.
    private boolean isMovingRight = false;
    private boolean isMovingLeft = false;

    // Gravity
    static double g = 1;

    private static boolean isDead = false;
    private static int hp = 100;

    Collision playerCollision = new Collision();

    Rectangle hpBar = new Rectangle();

    Player(double x, double y, double width, double height, Image sprite) {
        this.setX(x);
        this.setY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setImage(sprite);
        hpBar.setHeight(10);
        hpBar.setWidth(width);
        hpBar.setFill(Color.GREEN);
        MainApplication.addToRoot(hpBar);
    }

    /**
     * Check Stage Collision Top
     * @return
     */
    public boolean checkStageCollisionTop(){
        return(this.getY() <= 0);
    }

    /**
     * Check Stage Collision Bottom
     * @return
     */
    public boolean checkStageCollisionBottom(){
        return(this.getY()  + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y);
    }

    /**
     * Check Stage Collision Left
     * @return Whether the player is colliding with the left edge of the stage or not.
     */
    public boolean checkStageCollisionLeft() {
        return (this.getX() <= 0);
    }

    /**
     * Check Stage Collision Right
     * @return Returns whether the player is colliding with the right edge of the stage or not.
     */
    public boolean checkStageCollisionRight(){
        return(this.getX() + this.getFitWidth() >= MainApplication.WINDOW_SIZE_X);
    }

    /**
     * Player Movement
     * Moves the player every frame.
     */
    public void move(){
        this.setX(this.getX() + vX);
        this.setY(this.getY() + vY);
    }

    /**
     * Get Y Velocity
     * Position and size can be called fromm ImageView.
     * @return Returns value of Y velocity.
     */
    public double getvY() {
        return vY;
    }

    /**
     * Get X Velocity
     * @return Returns value of X velocity.
     */
    public double getvX() {
        return vX;
    }

    /**
     * Player update method called every frame.
     */
    public void onUpdate(boolean up, boolean left, boolean right) {

        if(playerCollision.enemyCollision(this)){
            hp -= 5;
        }

        if (!isDead) {

            vX = 0;

            if (!inJumpAnimation) {
                vY = 0;
            }

            hpBar.setWidth(hp);

            // Player Controls
            //  |- L&R Controls
            if (right &&
                    !checkStageCollisionRight() &&
                    !(this.getX() + this.getFitWidth() + playerSpeed >= MainApplication.WINDOW_SIZE_X) &&
                    playerCollision.isCollidingRight(this) == null) {

                vX += playerSpeed;
                isMovingRight = true;

            } else if (isMovingRight && this.getX() + this.getFitWidth() + playerSpeed >= MainApplication.WINDOW_SIZE_X) {
                setX(MainApplication.WINDOW_SIZE_X - this.getFitWidth());
                isMovingRight = false;

            }

            if (left &&
                    !checkStageCollisionLeft() &&
                    !(this.getX() - playerSpeed <= 0) &&
                    playerCollision.isCollidingLeft(this) == null) {

                vX += playerSpeed * -1;
                isMovingLeft = true;

            } else if (isMovingLeft && this.getX() - playerSpeed <= 0) {
                setX(0);
                isMovingLeft = false;

            }

            // |- Jumping Controls

            if (inJumpAnimation) {

                if (!(this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y)) {
                    vY += g;
                } else if (this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y) {
                    vY = 0;
                    inJumpAnimation = false;
                }
            }

            // Jump
            if (up && !inJumpAnimation) {
                vY += -15.0;
                inJumpAnimation = true;

            }

            // Ends jump animation when the player collides with stage bottom.
            if (this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y) {
                inJumpAnimation = false;
                this.setY(MainApplication.WINDOW_SIZE_Y - this.getFitHeight());
            }

            // TODO : Update player falling.
            if (!inJumpAnimation && playerCollision.isCollidingBottom(this) != null && !checkStageCollisionBottom()) {
                inJumpAnimation = true;

            }

            // TODO : Fix top & bottom collisions with map objects.

            move();

            hpBar.setX(this.getX());
            hpBar.setY(this.getY() - 20);

            StaticRect bottom = playerCollision.isCollidingBottom(this);
            StaticRect top = playerCollision.isCollidingTop(this);
            StaticRect cLeft = playerCollision.isCollidingLeft(this);
            StaticRect cRight = playerCollision.isCollidingRight(this);

            if (top != null && bottom != null) {
                double dBot = getY() + getFitHeight() - bottom.getY();
                double dTop = top.getY() + top.getHeight() - getY();

                if (dBot < dTop) {
                    inJumpAnimation = false;
                    vY = 0.0;
                    setY(bottom.getY() - bottom.getHeight());
                } else {
                    vY = 0;
                    setY(top.getY() + top.getHeight());
                }
            } else if (bottom != null) {
                inJumpAnimation = false;
                vY = 0.0;
                setY(bottom.getY() - bottom.getHeight());
                if(bottom.getType() == "spike"){
                    hp -= 120;
                }else if(bottom.getType() == "enemy"){
                    hp -= 5;
                }
            } else if (top != null) {
                vY = 0;
                setY(top.getY() + top.getHeight());
            }

        }

        if( hp <= 0 && !isDead ){
            die();
        }

    }

    public void die(){
        isDead = true;
        System.out.println("player died");
        MainApplication.rmFromRoot(this);
        MainApplication.rmFromRoot(hpBar);
        MainApplication.getStage().setScene(Menu.deathMenu());
    }

    public void reset(){
        this.setX(0);
        this.setY(MainApplication.WINDOW_SIZE_Y - this.getFitHeight());
        this.hp = 100;
    }

}