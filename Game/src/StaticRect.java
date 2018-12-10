import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


public class StaticRect extends StaticObject{

    private double x;
    private double y;
    private double width;
    private double height;
    private String itemID;
    private Type type;

    private ImageView ivSprite;

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
        this.type = type;

        ivSprite = new ImageView(sprite);
        ivSprite.setX(x);
        ivSprite.setY(y);
        ivSprite.setFitWidth(width);
        ivSprite.setFitHeight(height);
    }

    public StaticRect(double x, double y, double width, double height, Image sprite, Type type, String itemID){

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.itemID = itemID;

        ivSprite = new ImageView(sprite);
        ivSprite.setX(x);
        ivSprite.setY(y);
        ivSprite.setFitWidth(width);
        ivSprite.setFitHeight(height);
    }


    /**
     * Collision Detection
     *
     * @param player the player
     * @return returns colliding on left, right, top, and bottom
     */
    @Override
    public ArrayList<CollisionType> checkCollision(Player player) {
        ArrayList<CollisionType> collisions = new ArrayList<>();

        boolean collided = false;

        if(player.getX() + player.getFitWidth() >= ivSprite.getX() &&
                !(player.getX() >= ivSprite.getX() + ivSprite.getFitWidth()) &&
                !(player.getY() + player.getFitHeight() <= ivSprite.getY()) &&
                !(player.getY() >= ivSprite.getY() + ivSprite.getFitHeight())){
            collisions.add(CollisionType.Right);
            collided = true;
        }
        if (player.getX() <= ivSprite.getX() + ivSprite.getFitWidth() &&
                !(player.getX() + player.getFitWidth() <= ivSprite.getX()) &&
                !(player.getY() + player.getFitHeight() <= ivSprite.getY()) &&
                !(player.getY() >= ivSprite.getY() + ivSprite.getFitHeight())){
            collisions.add(CollisionType.Left);
            collided = true;
        }
        if(player.getY() <= ivSprite.getY() + ivSprite.getFitHeight() &&
                !(player.getY() + player.getFitHeight() <= ivSprite.getY()) &&
                !(player.getX() + player.getFitWidth() <= ivSprite.getX()) &&
                !(player.getX() >= ivSprite.getX() + ivSprite.getFitWidth())){
            collisions.add(CollisionType.Top);
            collided = true;
        }
        if (player.getY() + player.getFitHeight() >= ivSprite.getY() &&
                !(player.getY() >= ivSprite.getY() + ivSprite.getFitHeight()) &&
                !(player.getX() + player.getFitWidth() <= ivSprite.getX()) &&
                !(player.getX() >= ivSprite.getX() + ivSprite.getFitWidth())){
            collisions.add(CollisionType.Bottom);
            collided = true;
        }

        if ( !collided ) {
            collisions.add(CollisionType.None);
        }
        return collisions;
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getItemID(){
        return itemID;
    }

    public Node getSprite() {
        return ivSprite;
    }

    public Type getType(){ return type; }

    public void setX(double x){
        ivSprite.setX(x);
        this.x = x;
    }

    public void setY(double y){
        ivSprite.setY(y);
        this.y = y;
    }

    public void setWidth(double w){
        width = w;
        ivSprite.setFitWidth(w);
    }

    public void setHeight(double h){
        width = h;
        ivSprite.setFitHeight(h);
    }

    /**
     * Move ivSprite to the front of the stage
     */
    public void toFront(){
        this.ivSprite.toFront();
    }

}
