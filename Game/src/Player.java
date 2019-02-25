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

public class Player extends ImageView implements GameObject {

    // Player is in Jump Animation
    private boolean inJumpAnimation = false;

    // Player Speed When Moved
    private double playerSpeed = 10.0;
    private double climbingSpeed = 5.0;

    // Player Velocity
    private double vX, vY = 0.0;

    // Gravity
    private static final double G = 1;

    // Player is Dead
    private boolean isDead = false;

    // Player Health Points
    private int hp = 100;

    // Player Collision with Scene Objects
    private PlayerCollision playerCollision = new PlayerCollision();

    // Player Health Bar
    public Rectangle hpBar = new Rectangle((MainApplication.WINDOW_SIZE_X / 2) - 200, 10, 0, 20);
    public Rectangle hpBarBG = new Rectangle((MainApplication.WINDOW_SIZE_X / 2) - 200, 10, hp * 4, 20);

    Player(double x, double y, double width, double height, Image sprite) {
        this.setX(x);
        this.setY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setImage(sprite);
        hpBar.setY(10);
        hpBar.setFill(Color.GREEN);
        hpBarBG.setFill(Color.color(1.0, 1.0, 1.0, 0.2));
    }

    @Override
    public double getHeight() {
        return this.getFitHeight();
    }

    @Override
    public double getWidth() {
        return this.getFitWidth();
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
     * Player update method called every frame.
     */
    public void onUpdate() {

        // Key Bindings
        boolean keyUp = MainApplication.isPressed(KeyCode.UP) || MainApplication.isPressed(KeyCode.W) || MainApplication.isPressed(KeyCode.SPACE);
        boolean keyDown = MainApplication.isPressed(KeyCode.DOWN) || MainApplication.isPressed(KeyCode.S);
        boolean keyLeft = MainApplication.isPressed(KeyCode.LEFT) || MainApplication.isPressed(KeyCode.A);
        boolean keyRight = MainApplication.isPressed(KeyCode.RIGHT) || MainApplication.isPressed(KeyCode.D);

        // Reset Velocities
        vX = 0.0;
        vY = inJumpAnimation ? vY : 0.0;

        // Reset HP bar color & effects
        hpBar.setFill(Color.GREEN);
        hpBar.setEffect(null);

        // Move Left Controls
        if (keyRight && playerCollision.collidingRight(this) == null) {
            vX += playerSpeed;

        } else if (playerCollision.collidingRight(this) != null) {
            onCollision(playerCollision.collidingRight(this), StaticObject.CollisionType.Right);
        }

        // Move Right Controls
        if (keyLeft && playerCollision.collidingLeft(this) == null) {
            vX += -playerSpeed;

        } else if (playerCollision.collidingLeft(this) != null) {
            onCollision(playerCollision.collidingLeft(this), StaticObject.CollisionType.Left);
        }

        // Jumping Controls
        if (keyUp && !inJumpAnimation) {
            vY += -15.0;
            inJumpAnimation = true;
        }

        // Jump
        if (inJumpAnimation) {

            if (!(this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y)) {
                vY += G;
            } else if (this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y) {
                vY = 0.0;
                inJumpAnimation = false;
            }
        }

        // End jump animation when player collides with stage bottom
        if (this.getY() + this.getFitHeight() >= MainApplication.WINDOW_SIZE_Y) {
            inJumpAnimation = false;
            setY(MainApplication.WINDOW_SIZE_Y - this.getFitHeight());
            die();
        }

        // Falling
        if (!inJumpAnimation && playerCollision.collidingBottom(this) == null && !checkStageCollisionBottom()) {
            inJumpAnimation = true;
        }

        // Move the player
        move();

        // Player dies if hp drops to zero
        if (hp <= 0) {
            die();
        }

        // Preventing intersections with scene objects
        StaticRect collisionBottom = playerCollision.collidingBottom(this);
        StaticRect collisionTop = playerCollision.collidingTop(this);

        if (collisionTop != null && collisionBottom != null) {
            double diffBottom = getY() + getFitHeight() - collisionBottom.getY();
            double diffTop = collisionTop.getY() + collisionTop.getHeight() - getY();

            if (diffBottom < diffTop) {

                inJumpAnimation = false;
                vY = 0.0;
                setY(collisionTop.getY() - collisionTop.getHeight());
                onCollision(collisionBottom, StaticObject.CollisionType.Bottom);

            } else if (diffBottom > diffTop) {

                vY = 0.0;
                setY(collisionTop.getY() + collisionTop.getHeight());

                onCollision(collisionTop, StaticObject.CollisionType.Top);
            }

        } else if (collisionBottom != null) {
            inJumpAnimation = false;
            vY = 0.0;
            setY(collisionBottom.getY() - collisionBottom.getHeight());
            onCollision(collisionBottom, StaticObject.CollisionType.Bottom);

        } else if (collisionTop != null) {
            vY = 0.0;
            setY(collisionTop.getY() + collisionTop.getHeight());
        }

        // Reset HP bar width to match the player's HP
        hpBar.setWidth(((float) hp) * 4);

        // Enemy Collision
        if (playerCollision.enemyCollision(this)) {
            damage(5);
        }

        // Item Collision
        if (playerCollision.itemCollision(this) != null) {
            StaticRect s = playerCollision.itemCollision(this);

            s.setX(10 + (MainApplication.collectedParts.size() * 100));
            s.setY(10);
            s.setWidth(90);
            s.setHeight(90);

            MainApplication.collectedParts.add(s);
            MainApplication.sceneObjects.remove(s);
            inJumpAnimation = true;
        }

        // Exit Collision
        if (playerCollision.exitCollision(this) != null) {
            // Load Next Level
            MainApplication.stopTimer();
            MainApplication.getStage().setScene(Menu.levelCompleted());
        }

    }

    /**
     * Scene Object Collision Effects
     *
     * @param staticRect Any StaticRect.
     * @param type       Type of collision. (Left, Right, Top, Bottom)
     */
    private void onCollision(StaticRect staticRect, StaticObject.CollisionType type) {

        if (type == StaticObject.CollisionType.Bottom) {

            if (staticRect.getType() == StaticObject.Type.SPIKE) {
                damage(120);
            } else if (staticRect.getType() == StaticObject.Type.ENEMY) {
                damage(5);
            } else if (staticRect.getType() == StaticObject.Type.LAVA) {
                damage(120);
            }

        } else if (type == StaticObject.CollisionType.Left || type == StaticObject.CollisionType.Right) {

            if (staticRect.getType() == StaticObject.Type.LAVA) {
                damage(120);
            } else if (staticRect.getType() == StaticObject.Type.ENEMY) {
                damage(5);
            }

            // Ladders
            if (type == StaticObject.CollisionType.Left) {
                if (staticRect.getType() == StaticObject.Type.LADDER) {
                    if (MainApplication.isPressed(KeyCode.LEFT) || MainApplication.isPressed(KeyCode.UP) || MainApplication.isPressed(KeyCode.SPACE)) {
                        inJumpAnimation = true;
                        vY = -climbingSpeed;
                    }
                }
            } else if (type == StaticObject.CollisionType.Right) {
                if (staticRect.getType() == StaticObject.Type.LADDER) {

                    if (MainApplication.isPressed(KeyCode.RIGHT) || MainApplication.isPressed(KeyCode.UP) || MainApplication.isPressed(KeyCode.SPACE)) {
                        inJumpAnimation = true;
                        vY = -climbingSpeed;
                    }
                }
            }
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
        hpBar.setEffect(new GaussianBlur(5));

    }

    /**
     * Player Death
     */
    public void die() {
        isDead = true;
        hp = 0;
        MainApplication.collectedParts.clear();
        MainApplication.stopTimer();
        MainApplication.getStage().setScene(Menu.deathMenu());
    }


    /**
     * Get Y Velocity
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

}