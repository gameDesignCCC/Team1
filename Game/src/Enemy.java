/*
 * Author(s):
 * Date: 6/10/2018
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

// NOTE: Renamed class from "Enemies" to "Enemy" because I'm assuming that all the enemies will be instantiated and therefore the singular form makes more sense but idk.
public class Enemy extends ImageView implements GameObject {

    // Speed
    private double speed = 2.0;

    // Velocities
    private double vX, vY;

    // Health Points
    private int hp = 100;

    // Enemy is dead
    private boolean isDead = false;

    // Enemy Player Following
    private boolean isTriggered = false;
    private double triggerDistance = 50.0;

    // HP Display
    public Rectangle hpBar = new Rectangle();

    EnemyCollision enemyCollision = new EnemyCollision();

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

    Enemy(double x, double y, Image sprite) {
        this(x, y, MapLoader.GRID_SIZE, MapLoader.GRID_SIZE, sprite);
    }

    public void onUpdate() {


        vX = 0.0;
        vY = 0.0;

        setX(getX() + vX);
        setY(getY() + vY);

        hpBar.setX(getX());
        hpBar.setY(getY() - 8);

        if(!isTriggered && getPlayerDistance(this.getX(), this.getY(), MainApplication.player) <= triggerDistance){
            isTriggered = true;
        }

        if(isTriggered){
            if(getX() < MainApplication.player.getX()){
                vX += speed;
            } else if (getX() > MainApplication.player.getX()){
                vX -= speed;
            }
        }

        setX(getX() + vX);
        setY(getY() + vY);

    }

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

    @Override
    public double getHeight() {
        return this.getFitHeight();
    }

    @Override
    public double getWidth() {
        return this.getFitWidth();
    }

    private double getPlayerDistance(double x, double y, Player player){
        double centerX = x + this.getWidth() / 2;
        double centerY = y + this.getHeight() / 2;

        return (Math.abs(centerX - (player.getX() + (player.getFitWidth() / 2))) + Math.abs(centerY - (player.getY() + (player.getFitHeight() / 2))));
    }

}
