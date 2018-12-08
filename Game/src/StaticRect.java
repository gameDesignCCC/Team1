import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


public class StaticRect extends StaticObject{

    private double x;
    private double y;
    private double width;
    private double height;
    private Image sprite;
    private Type type;

    ImageView rSprite = new ImageView();

    /**
     * constructor
     * @param x x coordinate of rectangle
     * @param y y coordinate of rectangle
     * @param width width of rectangle
     * @param height height of rectangle
     * @param sprite sprite of rectangle
     */
    public StaticRect(double x, double y, double width, double height, Image sprite, Type type){

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.type = type;

        rSprite.setImage(sprite);
        rSprite.setX(x);
        rSprite.setY(y);
        rSprite.setFitWidth(width);
        rSprite.setFitHeight(height);

        /**
        MainApplication.addToRoot(rSprite);
        MainApplication.sceneObjects.add(this);
         **/

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

        if(player.getX() + player.getFitWidth() >= rSprite.getX() &&
                !(player.getX() >= rSprite.getX() + rSprite.getFitWidth()) &&
                !(player.getY() + player.getFitHeight() <= rSprite.getY()) &&
                !(player.getY() >= rSprite.getY() + rSprite.getFitHeight())){
            collisions.add(CollisionType.Right);
            collided = true;
        }
        if (player.getX() <= rSprite.getX() + rSprite.getFitWidth() &&
                !(player.getX() + player.getFitWidth() <= rSprite.getX()) &&
                !(player.getY() + player.getFitHeight() <= rSprite.getY()) &&
                !(player.getY() >= rSprite.getY() + rSprite.getFitHeight())){
            collisions.add(CollisionType.Left);
            collided = true;
        }
        if(player.getY() <= rSprite.getY() + rSprite.getFitHeight() &&
                !(player.getY() + player.getFitHeight() <= rSprite.getY()) &&
                !(player.getX() + player.getFitWidth() <= rSprite.getX()) &&
                !(player.getX() >= rSprite.getX() + rSprite.getFitWidth())){
            collisions.add(CollisionType.Top);
            collided = true;
        }
        if (player.getY() + player.getFitHeight() >= rSprite.getY() &&
                !(player.getY() >= rSprite.getY() + rSprite.getFitHeight()) &&
                !(player.getX() + player.getFitWidth() <= rSprite.getX()) &&
                !(player.getX() >= rSprite.getX() + rSprite.getFitWidth())){
            collisions.add(CollisionType.Bottom);
            collided = true;
        }

        if ( !collided ) {
            collisions.add(CollisionType.None);
        }
        return collisions;
    }

    public double getX(){
        return rSprite.getX();
    }

    public double getY() {
        return rSprite.getY();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Node getSprite() {
        return rSprite;
    }

    public Type getType(){ return type; }

    public void setX(double x){
        rSprite.setX(x);
        this.x = x;
    }

    public void setY(double y){
        rSprite.setY(y);
        this.y = y;
    }

    /**
     *
     * moves rectangle to the front of stage
     */
    public void toFront(){
        this.rSprite.toFront();
    }

}
