import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class StaticObject {

    enum CollisionType {
        None, Left, Right, Top, Bottom
    }

    /**
     *
     * list of left, right, bottom, and top collisions
     * @param player the player
     * @return returns collisions
     */
    public abstract ArrayList<CollisionType> checkCollision(Player player );


}