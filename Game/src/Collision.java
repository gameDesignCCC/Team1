/*
 * Author(s):
 * Date: 6/10/2018
 */

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Collision {
    public boolean isCollidingRight(Rectangle box, ImageView player) {


        if (player.getX() + player.getFitWidth() >= box.getX() && !(player.getX() >= box.getX() + box.getWidth())) {
            return true;

        } else {
            return false;
        }

    }

    public boolean isCollidingLeft(Rectangle box, ImageView player) {


        if (player.getX() <= box.getX() + box.getWidth() && !(player.getX() + player.getFitWidth() <= box.getX())) {
            return true;
        } else {
            return false;
        }
    }
}
