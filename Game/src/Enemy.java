/*
 * Author(s):
 * Date: 6/10/2018
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// NOTE: Renamed class from "Enemies" to "Enemy" because I'm assuming that all the enemies will be instantiated and therefore the singular form makes more sense but idk.
public class Enemy extends ImageView {

    public enum LogicMode{
        NONE, POINT_AB, FOLLOW
    }

    // Velocities
    private double vX, vY;

    // Health Points
    private int hp = 100;

    // Enemy is dead
    private boolean isDead = false;

    // HP Display
    public Rectangle hpBar = new Rectangle();

    // Enemy Logic
    private LogicMode logicMode;

    // Point A-B
    private double pointA, pointB, oPointA, oPointB;
    private boolean flag;

    Enemy(double x, double y, double width, double height, Image sprite, LogicMode logicMode) {
        this.logicMode = logicMode;

        this.setX(x);
        this.setY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setImage(sprite);

        hpBar.setWidth(width);
        hpBar.setHeight(4);
        hpBar.setFill(Color.GREEN);
    }

    Enemy(double x, double y, Image sprite, LogicMode logicMode) {
        this(x, y, MapLoader.GRID_SIZE, MapLoader.GRID_SIZE, sprite, logicMode);
    }

    Enemy(double x, double y, Image sprite) {
        this(x, y, MapLoader.GRID_SIZE, MapLoader.GRID_SIZE, sprite, LogicMode.NONE);
    }

    public void onUpdate() {

        vX = 0.0;
        vY = 0.0;

        pointA = oPointA - MainApplication.vStart;
        pointB = oPointB - MainApplication.vStart;

        if (logicMode == LogicMode.POINT_AB) {
            if (getX() >= pointB) {
                flag = false;
            } else if (getX() <= pointA) {
                flag = true;
            }

            vX += flag ? 2.0 : -2.0;

        } else if (logicMode == LogicMode.FOLLOW) {
            if (getX() < MainApplication.player.getX()) {
                vX += 2.0;
            } else if (getX() > MainApplication.player.getX()) {
                vX -= 2.0;
            }
        }

        setX(getX() + vX);
        setY(getY() + vY);

        hpBar.setX(getX());
        hpBar.setY(getY() - 8);

    }

    public boolean checkCollision(Player player){

        boolean collided = false;

        if(player.getX() + player.getFitWidth() >= this.getX() &&
                !(player.getX() >= this.getX() + this.getFitWidth()) &&
                !(player.getY() + player.getFitHeight() <= this.getY()) &&
                !(player.getY() >= this.getY() + this.getFitHeight())){
            collided = true;
        }
        if (player.getX() <= this.getX() + this.getFitWidth() &&
                !(player.getX() + player.getFitWidth() <= this.getX()) &&
                !(player.getY() + player.getFitHeight() <= this.getY()) &&
                !(player.getY() >= this.getY() + this.getFitHeight())){
            collided = true;
        }
        if(player.getY() <= this.getY() + this.getFitHeight() &&
                !(player.getY() + player.getFitHeight() <= this.getY()) &&
                !(player.getX() + player.getFitWidth() <= this.getX()) &&
                !(player.getX() >= this.getX() + this.getFitWidth())){
            collided = true;
        }
        if (player.getY() + player.getFitHeight() >= this.getY() &&
                !(player.getY() >= this.getY() + this.getFitHeight()) &&
                !(player.getX() + player.getFitWidth() <= this.getX()) &&
                !(player.getX() >= this.getX() + this.getFitWidth())){
            collided = true;
        }

        return collided;

    }

    public double getWidth(){
        return getFitWidth();
    }

    public double getHeight(){
        return getFitHeight();
    }

    public LogicMode getLogicMode(){
        return logicMode;
    }

    public void setLogicMode(LogicMode logicMode){
        this.logicMode = logicMode;
    }

    public void setPointAB(double a, double b){
        this.pointA = a;
        this.pointB = b;
        this.oPointA = a;
        this.oPointB = b;
    }

}