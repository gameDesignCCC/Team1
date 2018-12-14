import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


public class StaticRect extends StaticObject{

    private double x;
    private double y;
    private double width;
    private double height;
    private boolean collisionEnabled = true;
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
     * @param type type of rectangle
     */
    public StaticRect(double x, double y, double width, double height, Image sprite, Type type){

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        collisionEnabled = type != Type.ITEM && type != Type.EXIT;

        ivSprite = new ImageView(sprite);
        ivSprite.setX(x);
        ivSprite.setY(y);
        ivSprite.setFitWidth(width);
        ivSprite.setFitHeight(height);
    }

    /**
     * constructor
     * @param x x coordinate of rectangle
     * @param y y coordinate of rectangle
     * @param width width of rectangle
     * @param height height of rectangle
     * @param sprite sprite of rectangle
     * @param type type of rectangle
     */
    public StaticRect(double x, double y, double width, double height, Image sprite, Type type, String itemID){

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.itemID = itemID;
        collisionEnabled = type != Type.ITEM && type != Type.EXIT;

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

        if (player.getX() + player.getFitWidth() >= x && player.getX() < x + width
                && player.getY() + player.getFitHeight() > y && player.getY() < y + height) {
            collisions.add(CollisionType.Right);
            collided = true;
        }

        if (player.getX() <= x + width && player.getX() + player.getFitWidth() > x
                && player.getY() + player.getFitHeight() > y && player.getY() < y + height) {
            collisions.add(CollisionType.Left);
            collided = true;
        }

        if (player.getY() <= y + height && player.getY() + player.getFitHeight() > y
                && player.getX() + player.getFitWidth() > x && player.getX() < x + width) {
            collisions.add(CollisionType.Top);
            collided = true;
        }

        if (player.getY() + player.getFitHeight() >= y && player.getY() < y + height
                && player.getX() + player.getFitWidth() > x && player.getX() < x + width) {
            collisions.add(CollisionType.Bottom);
            collided = true;
        }

        if (!collided) {
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

    public boolean getCollisionEnabled(){
        return collisionEnabled;
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

    public void setCollisionEnabled(boolean collisionEnabled) {
        this.collisionEnabled = collisionEnabled;
    }

    /**
     * Move ivSprite to the front of the stage
     */
    public void toFront(){
        this.ivSprite.toFront();
    }

}
