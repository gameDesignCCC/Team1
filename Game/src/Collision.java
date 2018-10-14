/*
 * Author(s):
 * Date: 13/10/2018
 */

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Collision {

    /**
     * TODO Add descriptions
     *
     * @param box
     * @param player
     * @return
     */
    public boolean isCollidingRight(Rectangle box, ImageView player) {
        return (player.getX() + player.getFitWidth() >= box.getX() && !(player.getX() >= box.getX() + box.getWidth()));
    }

    /**
     * TODO Add descriptions
     *
     * @param box
     * @param player
     * @return
     */
    public boolean isCollidingLeft(Rectangle box, ImageView player) {
        return (player.getX() <= box.getX() + box.getWidth() && !(player.getX() + player.getFitWidth() <= box.getX()));
    }
}
