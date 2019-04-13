import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends ImageView implements GameObject {

    // Player Animations
    private boolean inJumpAnimation = false;
    private boolean inClimbAnimation = false;

    // Player Speed When Moved
    private static final double SPEED = 10.0;
    private static final double CLIMBING_SPEED = 5.0;

    // Player Velocity
    private double vX, vY = 0.0;

    // Gravity
    private static final double G = 1;

    // Player is Dead
    private boolean isDead = false;

    // Player Health Points
    private int hp = 100;

    // Player Health Bar
    Rectangle hpBar = new Rectangle((MainApplication.WINDOW_SIZE_X / 2) - 200, 10, 0, 20);
    Rectangle hpBarBG = new Rectangle((MainApplication.WINDOW_SIZE_X / 2) - 200, 10, hp * 4, 20);

    // Sprites
    private static Image sprite = new Image("assets/sprites_textures/player/player_placeholder.png");

    private static Image[] spriteFramesR = {
            new Image("assets/sprites_textures/player/r0.png"),
            new Image("assets/sprites_textures/player/r1.png"),
            new Image("assets/sprites_textures/player/r2.png"),
            new Image("assets/sprites_textures/player/r3.png"),
            new Image("assets/sprites_textures/player/r4.png")
    };

    private static Image[] spriteFramesL = {
            new Image("assets/sprites_textures/player/l0.png"),
            new Image("assets/sprites_textures/player/l1.png"),
            new Image("assets/sprites_textures/player/l2.png"),
            new Image("assets/sprites_textures/player/l3.png"),
            new Image("assets/sprites_textures/player/l4.png")
    };

    private static double lastSpriteUpdateTime = 0.0;
    private static int currentSpriteFrame = 0;

    Player(double x, double y, double width, double height) {
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
        return getFitHeight();
    }

    @Override
    public double getWidth() {
        return getFitWidth();
    }

    /**
     * Check Stage Collision Top
     *
     * @return Player colliding with stage top.
     */
    public boolean checkStageCollisionTop() {
        return (getY() <= 0);
    }

    /**
     * Check Stage Collision Bottom
     *
     * @return Player colliding with stage bottom.
     */
    public boolean checkStageCollisionBottom() {
        return (getY() + getFitHeight() >= MainApplication.WINDOW_SIZE_Y);
    }

    /**
     * Check Stage Collision Left
     *
     * @return Player colliding with stage left.
     */
    public boolean checkStageCollisionLeft() {
        return (getX() <= 0);
    }

    /**
     * Check Stage Collision Right
     *
     * @return Player colliding with stage right.
     */
    public boolean checkStageCollisionRight() {
        return (getX() + getFitWidth() >= MainApplication.WINDOW_SIZE_X);
    }

    /**
     * Player Movement
     * Moves the player every frame.
     */
    private void move() {
        setX(getX() + vX);
        setY(getY() + vY);
    }

    /**
     * Player update method called every frame.
     */
    void onUpdate() {

        if (vX < 0) {
            if (System.currentTimeMillis() - lastSpriteUpdateTime >= 100) {
                currentSpriteFrame = (currentSpriteFrame + 1) % spriteFramesL.length;
                setImage(spriteFramesL[currentSpriteFrame]);
                lastSpriteUpdateTime = System.currentTimeMillis();
            }
        } else if (vX > 0) {
            if (System.currentTimeMillis() - lastSpriteUpdateTime >= 100) {
                currentSpriteFrame = (currentSpriteFrame + 1) % spriteFramesR.length;
                setImage(spriteFramesR[currentSpriteFrame]);
                lastSpriteUpdateTime = System.currentTimeMillis();
            }
        } else {
            setImage(sprite);
            currentSpriteFrame = 0;
        }

        // Key Bindings
        boolean keyUp = controlUpPressed();
        boolean keyDown = controlDownPressed();
        boolean keyLeft = controlLeftPressed();
        boolean keyRight = controlRightPressed();

        // Reset Velocities
        vX = 0.0;
        vY = inJumpAnimation ? vY : 0.0;

        // Reset Animation
        inClimbAnimation = false;

        // Reset HP bar color & effects
        hpBar.setFill(Color.GREEN);
        hpBar.setEffect(null);

        // Move Left Controls
        if (keyRight && PlayerCollision.collidingRight(this) == null) {
            vX += SPEED;

        } else if (PlayerCollision.collidingRight(this) != null) {
            onCollision(PlayerCollision.collidingRight(this), StaticObject.CollisionDirection.Right);
        }

        // Move Right Controls
        if (keyLeft && PlayerCollision.collidingLeft(this) == null) {
            vX += -SPEED;

        } else if (PlayerCollision.collidingLeft(this) != null) {
            onCollision(PlayerCollision.collidingLeft(this), StaticObject.CollisionDirection.Left);
        }

        // Jumping Controls
        if (keyUp && !inJumpAnimation) {
            vY += -15.0;
            inJumpAnimation = true;
        }

        // Jump
        if (inJumpAnimation) {

            if (!(getY() + getFitHeight() >= MainApplication.WINDOW_SIZE_Y)) {
                vY += G;
            } else if (getY() + getFitHeight() >= MainApplication.WINDOW_SIZE_Y) {
                vY = 0.0;
                inJumpAnimation = false;
            }
        }

        // End jump animation when player collides with stage bottom
        if (checkStageCollisionBottom()) {
            inJumpAnimation = false;
            setY(MainApplication.WINDOW_SIZE_Y - this.getFitHeight());
            die();
        }

        // Falling
        if (!inJumpAnimation && !inClimbAnimation && PlayerCollision.collidingBottom(this) == null) {
            inJumpAnimation = true;
        }

        // Move the player
        move();

        // Player dies if hp drops to zero
        if (hp <= 0) {
            die();
        }

        // Preventing intersections with scene objects
        StaticRect collisionBottom = PlayerCollision.collidingBottom(this);
        StaticRect collisionTop = PlayerCollision.collidingTop(this);

        if (collisionTop != null && collisionBottom != null) {
            double diffBottom = getY() + getFitHeight() - collisionBottom.getY();
            double diffTop = collisionTop.getY() + collisionTop.getHeight() - getY();

            if (diffBottom < diffTop) {

                inJumpAnimation = false;
                vY = 0.0;
                setY(collisionTop.getY() - collisionTop.getHeight());
                onCollision(collisionBottom, StaticObject.CollisionDirection.Bottom);

            } else if (diffBottom > diffTop) {

                vY = 0.0;
                setY(collisionTop.getY() + collisionTop.getHeight());

                onCollision(collisionTop, StaticObject.CollisionDirection.Top);
            }

        } else if (collisionBottom != null) {
            inJumpAnimation = false;
            vY = 0.0;
            setY(collisionBottom.getY() - collisionBottom.getHeight());
            onCollision(collisionBottom, StaticObject.CollisionDirection.Bottom);

        } else if (collisionTop != null) {
            vY = 0.0;
            setY(collisionTop.getY() + collisionTop.getHeight());
        }

        // Reset HP bar width to match the player's HP
        hpBar.setWidth(((float) hp) * 4);

        // Enemy Collision
        if (PlayerCollision.enemyCollision(this)) {
            damage(5);
        }

        // Item Collision
        if (PlayerCollision.itemCollision(this) != null) {
            StaticRect s = PlayerCollision.itemCollision(this);

            s.setX(10 + (MainApplication.collectedPartsCurrent.size() * 100));
            s.setY(10);
            s.setWidth(90);
            s.setHeight(90);

            MainApplication.collectedPartsCurrent.add(s);
            MainApplication.sceneObjects.remove(s);

            inJumpAnimation = true;
        }

        // Exit Collision
        if (PlayerCollision.exitCollision(this) != null) {
            onLevelExit();
        }

    }

    /**
     * On Level Exit
     */
    private void onLevelExit() {

        // Stop game loop
        MainApplication.stopTimer();

        // Add current level to list of completed levels.
        MainApplication.completedLevels.add(MainApplication.levels.get(MainApplication.currentLevelIndex));

        // Add parts collected in current level to list of total collected parts.
        MainApplication.collectedParts.addAll(MainApplication.collectedPartsCurrent);

        // Clear list of parts collected in current level.
        MainApplication.collectedPartsCurrent.clear();

        // Increase the current level position.
        MainApplication.currentLevelIndex++;

        // Auto save if enabled.
        if (MainApplication.autoSave) MainApplication.saveGame(MainApplication.workingSaveFile, false);

        // Pause Music Player
        MainApplication.musicPlayer.pause();
        // Set scene to level completed menu.
        MainApplication.getStage().setScene(Menu.levelCompleted());
    }

    /**
     * Scene Object Collision Effects
     *
     * @param staticRect Any StaticRect.
     * @param type       Type of collision. (Left, Right, Top, Bottom)
     */
    private void onCollision(StaticRect staticRect, StaticObject.CollisionDirection type) {

        if (type == StaticObject.CollisionDirection.Bottom) {

            if (staticRect.getType() == StaticObject.Type.SPIKE) {
                damage(120);
            } else if (staticRect.getType() == StaticObject.Type.ENEMY) {
                damage(5);
            } else if (staticRect.getType() == StaticObject.Type.LAVA) {
                damage(120);
            }

        } else if (type == StaticObject.CollisionDirection.Left || type == StaticObject.CollisionDirection.Right) {

            if (staticRect.getType() == StaticObject.Type.LAVA) {
                damage(120);
            } else if (staticRect.getType() == StaticObject.Type.ENEMY) {
                damage(5);
            }

            if (type == StaticObject.CollisionDirection.Right) {

                if (staticRect.getType() == StaticObject.Type.LADDER) {
                    if (controlRightPressed() || controlUpPressed()) {
                        inClimbAnimation = true;
                        vY = -CLIMBING_SPEED;
                    } else if (controlDownPressed()) {
                        inClimbAnimation = true;
                        vY = CLIMBING_SPEED;
                    }
                }

            } else {

                if (staticRect.getType() == StaticObject.Type.LADDER) {
                    if (controlLeftPressed() || controlUpPressed()) {
                        inClimbAnimation = true;
                        vY = -CLIMBING_SPEED;
                    } else if (controlDownPressed()) {
                        inClimbAnimation = true;
                        vY = CLIMBING_SPEED;
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
    private void damage(int damage) {
        hp -= damage;
        hpBar.setFill(Color.RED);
        hpBar.setEffect(new GaussianBlur(5));

    }

    /**
     * Player Death
     */
    private void die() {
        isDead = true;
        hp = 0;
        MainApplication.musicPlayer.stop();
        MainApplication.collectedParts.clear();
        MainApplication.stopTimer();
        MainApplication.getStage().setScene(Menu.deathMenu());
        MainApplication.musicPlayer.stop();
    }


    /**
     * Get Y Velocity
     *
     * @return Returns value of Y velocity.
     */
    double getVY() {
        return vY;
    }

    /**
     * Get X Velocity
     *
     * @return Returns value of X velocity.
     */
    double getVX() {
        return vX;
    }

    private boolean controlUpPressed() {
        return (MainApplication.isPressed(KeyCode.UP) || MainApplication.isPressed(KeyCode.W) || MainApplication.isPressed(KeyCode.SPACE));
    }

    private static boolean controlDownPressed() {
        return (MainApplication.isPressed(KeyCode.DOWN) || MainApplication.isPressed(KeyCode.S));
    }

    private static boolean controlLeftPressed() {
        return (MainApplication.isPressed(KeyCode.LEFT) || MainApplication.isPressed(KeyCode.A));
    }

    private static boolean controlRightPressed() {
        return (MainApplication.isPressed(KeyCode.RIGHT) || MainApplication.isPressed(KeyCode.D));
    }

}
