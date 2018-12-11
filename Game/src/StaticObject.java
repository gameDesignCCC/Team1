import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class StaticObject {

    enum CollisionType {
        None, Left, Right, Top, Bottom
    }

    public static enum Type{
        BLOCK, GROUND, LAVA, SPIKE, ENEMY, EXIT, ITEM
    }

    /**
     * list of left, right, bottom, and top collisions
     * @param player the player
     * @return returns collisions
     */
    public abstract ArrayList<CollisionType> checkCollision(Player player );

    /**
     * Get Sprite
     * @return Object's texture/sprite
     */
    public abstract Node getSprite();


}
