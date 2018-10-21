import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class StaticRect extends StaticObject{

    double x;
    double y;
    double width;
    double height;
    Image sprite;

    ImageView spriteView = new ImageView();

    /**
     * constructor
     * @param x x coordinate of rectangle
     * @param y y coordinate of rectangle
     * @param width width of rectangle
     * @param height height of rectangle
     * @param sprite sprite of rectangle
     */
    public StaticRect(double x, double y, double width, double height, Image sprite){

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;

        spriteView.setImage(sprite);
        spriteView.setX(x);
        spriteView.setY(y);
        spriteView.setFitWidth(width);
        spriteView.setFitHeight(height);

        MainApplication.addToRoot(spriteView);

    }

    /**
     *
     *
     * @param player the player
     * @return returns colliding on left,right,top and bottom
     */
    @Override
    public ArrayList<CollisionType> checkCollision(Player player) {
        ArrayList<CollisionType> collisions = new ArrayList<>();

        boolean collided = false;

        if(player.getX() + player.getFitWidth() >= spriteView.getX() &&
                !(player.getX() >= spriteView.getX() + spriteView.getFitWidth()) &&
                !(player.getY() + player.getFitHeight() <= spriteView.getY()) &&
                !(player.getY() >= spriteView.getY() + spriteView.getFitHeight())){
            collisions.add(CollisionType.Right);
            collided = true;
        }
        if (player.getX() <= spriteView.getX() + spriteView.getFitWidth() &&
                !(player.getX() + player.getFitWidth() <= spriteView.getX()) &&
                !(player.getY() + player.getFitHeight() <= spriteView.getY()) &&
                !(player.getY() >= spriteView.getY() + spriteView.getFitHeight())){
            collisions.add(CollisionType.Left);
            collided = true;
        }
        if (player.getY() + player.getFitHeight() >= spriteView.getY() &&
                player.getX() + player.getFitWidth() > spriteView.getX() &&
                player.getX() < spriteView.getX() + spriteView.getFitWidth()){
            collisions.add(CollisionType.Bottom);
            collided = true;
        }

        if ( !collided ) {
            collisions.add(CollisionType.None);
        }
        return collisions;
    }

    /**
     *
     * moves rectangle to the front of stage
     */
    public void toFront(){
        this.spriteView.toFront();
    }

}
