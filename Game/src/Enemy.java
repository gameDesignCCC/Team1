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
        NONE, POINT_AB, FOLLOW, PATROL_AB
    }

    // Speed
    private double speed = 2.0;

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

    // Logic Options
    private double pointA, pointB, oPointA, oPointB;
    private boolean movingRight;
    private double patrolDistance;
    private boolean patrolTriggered = false;

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

        pointA = oPointA - MainApplication.distanceFromStart;
        pointB = oPointB - MainApplication.distanceFromStart;

        if (logicMode == LogicMode.POINT_AB) {
            if (getX() >= pointB) {
                movingRight = false;
            } else if (getX() <= pointA) {
                movingRight = true;
            }
            vX += movingRight ? speed : -speed;

        } else if (logicMode == LogicMode.FOLLOW) {
            if (getX() < MainApplication.player.getX()) {
                vX += speed;
            } else if (getX() > MainApplication.player.getX()) {
                vX -= speed;
            }
        } else if (logicMode == LogicMode.PATROL_AB){

            if (getX() + patrolDistance > MainApplication.player.getX() && getX() - patrolDistance < MainApplication.player.getX()) {
                patrolTriggered = true;
            }

            if (!patrolTriggered) {
                if (getX() >= pointB) {
                    movingRight = false;
                } else if (getX() <= pointA) {
                    movingRight = true;
                }
                vX += movingRight ? speed : -speed;
            } else {
                if (getX() < MainApplication.player.getX()) {
                    vX += speed;
                } else if (getX() > MainApplication.player.getX()) {
                    vX -= speed;
                }
            }

        }

        setX(getX() + vX);
        setY(getY() + vY);

        hpBar.setX(getX());
        hpBar.setY(getY() - 8);

    }

    public boolean checkCollision(Player player){

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

    public void setPatrolDistance(double distance){
        this.patrolDistance = distance;
    }

}
