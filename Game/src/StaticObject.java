import javafx.scene.Node;

import java.util.ArrayList;

public abstract class StaticObject {

    enum CollisionDirection {
        None, Left, Right, Top, Bottom
    }

    public enum Type{
        BLOCK, GROUND, LAVA, SPIKE, LADDER, ENEMY, EXIT, ITEM
    }

    /**
     * list of left, right, bottom, and top collisions
     * @param gameObject game object
     * @return returns collisions
     */
    public abstract ArrayList<CollisionDirection> checkCollision(GameObject gameObject);

    /**
     * Get Sprite
     * @return Object's texture/sprite
     */
    public abstract Node getSprite();


}
