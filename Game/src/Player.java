/*
 * Author(s): Jacob Dixon @jacobrdixon.com
 * Date: 24/10/2018
 */

import javafx.scene.effect.GaussianBlur;
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
    public Rectangle hpBar = new Rectangle((MainApplication.WINDOW_SIZE_X / 2) - 200, 10, 0, 20);
    public Rectangle hpBarBG = new Rectangle((MainApplication.WINDOW_SIZE_X / 2) - 200, 10, hp * 4, 20);

    Player(double x, double y, double width, double height, Image sprite) {
        this.setX(x);
        this.setY(y);
        this.startX = x;
        this.startY = y;
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setImage(sprite);
        hpBar.setY(10);
        hpBar.setFill(Color.GREEN);
        hpBarBG.setFill(Color.color(1.0, 1.0, 1.0, 0.2));
    }

    /**
     * Check Stage Collision Top
     * @return
     */
    public boolean checkStageCollisionTop() {
        return (this.getY() <= 0);
    }

    /**
     * Check Stage Collision Bottom
     * @return
     */
    public boolean checkStageCollisionBottom() {
        return (this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y);
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
     * @return Returns value of Y velocity.
     */
    public double getVY() {
        return vY;
    }

    /**
     * Get X Velocity
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

        // Reset Sprite

        // Reset HP bar color & effects
        hpBar.setFill(Color.GREEN);
        hpBar.setEffect(null);

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
            setY(MainApplication.WINDOW_SIZE_Y - this.getFitHeight());
        }

        // Falling
        if (!inJumpAnimation && playerCollision.isCollidingBottom(this) != null && !checkStageCollisionBottom()) {
            inJumpAnimation = true;

        }

        // Move the player
        move();

        // Player dies if hp drops to zero
        if (hp <= 0 && !isDead) {
            die();
        }

        // Preventing intersections with scene objects
        StaticRect bottom = playerCollision.isCollidingBottom(this);
        StaticRect top = playerCollision.isCollidingTop(this);

        if (top != null && bottom != null) {
            double diffBottom = getY() + getFitHeight() - bottom.getY();
            double diffTop = top.getY() + top.getHeight() - getY();

            if (diffBottom < diffTop) {
                inJumpAnimation = false;
                vY = 0.0;
                setY(top.getY() - top.getHeight());
                collisionEffect(bottom);

            } else if (diffBottom > diffTop) {
                vY = 0.0;
                setY(top.getY() + top.getHeight());
            }

        } else if (bottom != null) {
            inJumpAnimation = false;
            vY = 0.0;
            setY(bottom.getY() - bottom.getHeight());
            collisionEffect(bottom);

        } else if (top != null) {
            vY = 0.0;
            setY(top.getY() + top.getHeight());
        }

        // Reset HP bar width to match the player's HP
        hpBar.setWidth(((float) hp) * 4);

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

    }

    private void collisionEffect(StaticRect sr){

        // Scene object collision effects
        if (sr.getType() == StaticObject.Type.SPIKE) {
            damage(120);
        } else if (sr.getType() == StaticObject.Type.ENEMY) {
            damage(5);
        } else if (sr.getType() == StaticObject.Type.LAVA) {
            damage(120);
        } else if(sr.getType() == StaticObject.Type.EXIT) {
            // Load Next Level
        }
    }

    /**
     * Player Damage
     * @param damage Damage dealt to the player
     */
    public void damage(int damage) {
        hp -= damage;
        hpBar.setFill(Color.RED);
        hpBar.setEffect(new GaussianBlur(5));

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