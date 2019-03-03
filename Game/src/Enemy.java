/*
 * Author(s):
 * Date: 6/10/2018
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends ImageView implements GameObject {

    // Speed
    private static final double SPEED = 2.0;

    // Velocities
    private double vX, vY;

    // Health Points
    private int hp = 100;

    // Enemy is dead
    private boolean isDead = false;

    // Enemy AI
    private boolean isTriggered = false;
    private double triggerDistance = 200.0;

    // HP Display
    Rectangle hpBar = new Rectangle();

    // Enemy Collision
    private EnemyCollision enemyCollision = new EnemyCollision();

    /**
     * Default Constructor
     * @param x x position for new enemy
     * @param y y position for new enemy
     * @param width Width for new enemy
     * @param height Height for new enemy
     * @param sprite Sprite for new enemy
     */
    Enemy(double x, double y, double width, double height, Image sprite) {

        this.setX(x);
        this.setY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setImage(sprite);

        hpBar.setWidth(width);
        hpBar.setHeight(4);
        hpBar.setFill(Color.GREEN);
    }

    /**
     * Constructor
     * @param x x position for new enemy
     * @param y y position for new enemy
     * @param sprite Sprite for new enemy
     */
    Enemy(double x, double y, Image sprite) {
        this(x, y, MapLoader.GRID_SIZE, MapLoader.GRID_SIZE, sprite);
    }

    /**
     * Update on every frame
     */
    public void onUpdate() {

        vX = 0.0;
        vY = 0.0;

        if(!isTriggered && getPlayerDistance(this.getX(), this.getY(), MainApplication.player) <= triggerDistance){
            isTriggered = true;
        }

        if(isTriggered){
            if(getPlayerDistance(getX(), getY(), MainApplication.player) >= triggerDistance){
                isTriggered = false;
            }
            if(getX() < MainApplication.player.getX()){
                if(enemyCollision.collidingRight(this) == null) {
                    vX += SPEED;
                }
            } else if (getX() > MainApplication.player.getX()){
                if(enemyCollision.collidingLeft(this) == null) {
                    vX -= SPEED;
                }
            }
        }

        setX(getX() + vX);
        setY(getY() + vY);

        hpBar.setX(getX());
        hpBar.setY(getY() - 8);

    }

    /**
     * Check if this enemy is colliding with the player
     * @param player The player
     * @return Colliding with the player [T|F]
     */
    public boolean checkPlayerCollision(Player player){

        boolean collided = false;

        if (player.getX() + player.getFitWidth() >= getX() && player.getX() < getX() + getWidth()
                && player.getY() + player.getFitHeight() > getY() && player.getY() < getY() + getHeight()) {
            collided = true;

        } else if (player.getX() <= getX() + getWidth() && player.getX() + player.getFitWidth() > getX()
                && player.getY() + player.getFitHeight() > getY() && player.getY() < getY() + getHeight()) {
            collided = true;

        } else if (player.getY() <= getY() + getHeight() && player.getY() + player.getFitHeight() > getY()
                && player.getX() + player.getFitWidth() > getX() && player.getX() < getX() + getWidth()) {
            collided = true;

        } else if (player.getY() + player.getFitHeight() >= getY() && player.getY() < getY() + getHeight()
                && player.getX() + player.getFitWidth() > getX() && player.getX() < getX() + getWidth()) {
            collided = true;
        }

        return collided;

    }

    /**
     * Get enemy height
     * @return Enemy's height
     */
    @Override
    public double getHeight() {
        return this.getFitHeight();
    }

    /**
     * Get enemy width
     * @return Enemy's width
     */
    @Override
    public double getWidth() {
        return this.getFitWidth();
    }

    /**
     * Get enemy SPEED
     * @return Enemy's SPEED
     */
    public double getSpeed(){
        return SPEED;
    }

    /**
     * Get distance from player
     * @param x x position of enemy
     * @param y y position of enemy
     * @param player The player
     * @return Enemy's distance from the player
     */
    private double getPlayerDistance(double x, double y, Player player){
        double centerX = x + this.getWidth() / 2;
        double centerY = y + this.getHeight() / 2;

        return (Math.abs(centerX - (player.getX() + (player.getFitWidth() / 2))) + Math.abs(centerY - (player.getY() + (player.getFitHeight() / 2))));
    }

}
