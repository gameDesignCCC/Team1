/*
 * Author(s):
 * Date: 13/10/2018
 */

import javafx.scene.shape.Rectangle;

public class Collision {
    public boolean isCollidingRight(Rectangle box, Player player) {
        return (player.getX() + player.getFitWidth() >= box.getX() && !(player.getX() >= box.getX() + box.getWidth()));
    }

    public boolean isCollidingLeft(Rectangle box, Player player) {
        return (player.getX() <= box.getX() + box.getWidth() && !(player.getX() + player.getFitWidth() <= box.getX()));
    }
}
