/*
 * Author(s):
 * Date: 6/10/2018
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemies extends ImageView {
    double x;
    double y;
    double width;
    double height;
    double velocity = 2.0;
    int hp = 100;
    boolean isDead = true;

    Image sprite;

    Rectangle hpBar = new Rectangle();

    Enemies(double x, double y, double width, double height, Image sprite) {
        this.setX(x);
        this.setY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setImage(sprite);

        hpBar.setWidth(width);
        hpBar.setHeight(10);
        hpBar.setFill(Color.GREEN);
    }

    public void onUpdate(){
       if(this.getX() + this.getFitWidth() >= MainApplication.WINDOW_SIZE_X){
           velocity *= -1.0;
       }else if(this.getX() <= 0){
           velocity *= -1.0;
       }
        this.setX(this.getX() + velocity);

       hpBar.setX(this.getX());
       hpBar.setY(this.getY() - 20);

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

    // TODO: Needs to be implemented
    public void die(){
        isDead  = true;
        System.out.println("enemy killed");
    }

}