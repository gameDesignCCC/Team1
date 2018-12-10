/*
 * Author(s): Jacob Dixon @jacobrdixon.com
 * Date: 24/10/2018
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Player extends ImageView {

    // Player is in Jump Animation
    private boolean inJumpAnimation = false;

    // Player Speed When Moved
    private double playerSpeed = 10.0;

    // Player Starting X and Y
    private double startX;
    private double startY;

    // Player Velocity
    private double vX = 0.0;
    private double vY = 0.0;

    // Gravity
    static final double G = 1;

    // Player is Dead
    private boolean isDead = false;

    // Player Health Points
    private int hp = 100;

    // Collected Items
    public ArrayList<StaticRect> collectedParts = new ArrayList<>();

    // Player Collision with Scene Objects
    private Collision playerCollision = new Collision();

    // Player Health Bar
    public Rectangle hpBar = new Rectangle();

    Player(double x, double y, double width, double height, Image sprite) {
        this.setX(x);
        this.startX = x;
        this.setY(y);
        this.startY = y;
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setImage(sprite);
        hpBar.setHeight(20);
        hpBar.setWidth(0);
        hpBar.setX((MainApplication.WINDOW_SIZE_X / 2) - 200);
        hpBar.setY(10);
        hpBar.setFill(Color.GREEN);
    }

    /**
     * Check Stage Collision Top
     *
     * @return
     */
    public boolean checkStageCollisionTop() {
        return (this.getY() <= 0);
    }

    /**
     * Check Stage Collision Bottom
     *
     * @return
     */
    public boolean checkStageCollisionBottom() {
        return (this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y);
    }

    /**
     * Check Stage Collision Left
     *
     * @return Whether the player is colliding with the left edge of the stage or not.
     */
    public boolean checkStageCollisionLeft() {
        return (this.getX() <= 0);
    }

    /**
     * Check Stage Collision Right
     *
     * @return Returns whether the player is colliding with the right edge of the stage or not.
     */
    public boolean checkStageCollisionRight() {
        return (this.getX() + this.getFitWidth() >= MainApplication.WINDOW_SIZE_X);
    }

    /**
     * Player Movement
     * Moves the player every frame.
     */
    public void move() {
        this.setX(this.getX() + vX);
        this.setY(this.getY() + vY);
    }

    /**
     * Get Y Velocity
     * Position and size can be called from ImageView.
     *
     * @return Returns value of Y velocity.
     */
    public double getVY() {
        return vY;
    }

    /**
     * Get X Velocity
     *
     * @return Returns value of X velocity.
     */
    public double getVX() {
        return vX;
    }

    /**
     * Player update method called every frame.
     */
    public void onUpdate() {

        if (isDead) {
            return;
        }

        boolean keyUp = MainApplication.isPressed(KeyCode.UP) || MainApplication.isPressed(KeyCode.W) || MainApplication.isPressed(KeyCode.SPACE);
        boolean keyDown = MainApplication.isPressed(KeyCode.DOWN) || MainApplication.isPressed(KeyCode.S);
        boolean keyLeft = MainApplication.isPressed(KeyCode.LEFT) || MainApplication.isPressed(KeyCode.A);
        boolean keyRight = MainApplication.isPressed(KeyCode.RIGHT) || MainApplication.isPressed(KeyCode.D);

        // Reset Velocities
        vX = 0.0;
        vY = inJumpAnimation ? vY : 0.0;

        // Reset HP Bar
        hpBar.setFill(Color.GREEN);

        // Move Left Controls
        if (keyRight && playerCollision.isCollidingRight(this) == null) {
            vX += playerSpeed;
        }

        // Move Right Controls
        if (keyLeft && playerCollision.isCollidingLeft(this) == null) {
            vX += playerSpeed * -1;
        }

        // Jumping Controls
        if (inJumpAnimation) {

            if (!(this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y)) {
                vY += G;
            } else if (this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y) {
                vY = 0.0;
                inJumpAnimation = false;
            }
        }

        // Jump
        if (keyUp && !inJumpAnimation) {
            vY += -15.0;
            inJumpAnimation = true;
        }

        // End jump animation when player collides with stage bottom
        if (this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y) {
            inJumpAnimation = false;
            this.setY(MainApplication.WINDOW_SIZE_Y - this.getFitHeight());
        }

        // Falling
        if (!inJumpAnimation && playerCollision.isCollidingBottom(this) != null && !checkStageCollisionBottom()) {
            inJumpAnimation = true;

        }

        // Move the player
        move();

        // Set HP bar width to match the player's HP
        hpBar.setWidth(((float) hp) * 4);


        // Preventing intersections with scene objects
        StaticRect bottom = playerCollision.isCollidingBottom(this);
        StaticRect top = playerCollision.isCollidingTop(this);

        if (top != null && bottom != null) {
            double dBot = getY() + getFitHeight() - bottom.getY();
            double dTop = top.getY() + top.getHeight() - getY();

            if (dBot < dTop) {
                inJumpAnimation = false;
                vY = 0.0;
                setY(bottom.getY() - bottom.getHeight());
            } else {
                vY = 0.0;
                setY(top.getY() + top.getHeight());
            }

        } else if (bottom != null) {
            inJumpAnimation = false;
            vY = 0.0;
            setY(bottom.getY() - bottom.getHeight());

            // Scene object collision effects
            if (bottom.getType() == StaticObject.Type.SPIKE) {
                damage(120);
            } else if (bottom.getType() == StaticObject.Type.ENEMY) {
                damage(5);
            } else if (bottom.getType() == StaticObject.Type.LAVA) {
                damage(120);
            }
        } else if (top != null) {
            vY = 0.0;
            setY(top.getY() + top.getHeight());
        }

        // Item Collision
        if (playerCollision.itemCollision(this) != null) {
            StaticRect s = playerCollision.itemCollision(this);

            s.setX(10 + (collectedParts.size() * 100));
            s.setY(10);
            s.setWidth(90);
            s.setHeight(90);

            collectedParts.add(s);
            MainApplication.sceneObjects.remove(s);
            inJumpAnimation = true;
        }

        // Player dies when hp drops to zero
        if (hp <= 0 && !isDead) {
            die();
        }

    }

    /**
     * Player Damage
     *
     * @param damage Damage dealt to the player
     */
    public void damage(int damage) {
        hp -= damage;
        hpBar.setFill(Color.RED);
    }

    /**
     * Player Death
     */
    public void die() {
        isDead = true;
        MainApplication.stopTimer();
        MainApplication.getStage().setScene(Menu.deathMenu());
    }

}