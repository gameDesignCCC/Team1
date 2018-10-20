/*
 * Author(s):
 * Date: 13/10/2018
 */

import javafx.scene.shape.Rectangle;

public class Collision {

    /**
     * TODO : Add descriptions
     *
     * @param other Any Rectangle the player could collide with.
     * @param player The player.
     * @return Returns whether the right edge of the player is colliding with the left edge of the Rectangle or not.
     */
    public boolean isCollidingRight(Rectangle other, Player player) {
        return (player.getX() + player.getFitWidth() >= other.getX() &&
                !(player.getX() >= other.getX() + other.getWidth()) &&
                !(player.getY() + player.getFitHeight() <= other.getY()) &&
                !(player.getY() >= other.getY() + other.getHeight()));
    }

    /**
     * TODO : Add descriptions
     *
     * @param other Any Rectangle the player could collide with.
     * @param player The player.
     * @return Returns whether the left edge of the player is colliding with the right edge of the Rectangle or not.
     */
    public boolean isCollidingLeft(Rectangle other, Player player) {
        return (player.getX() <= other.getX() + other.getWidth() &&
                !(player.getX() + player.getFitWidth() <= other.getX()) &&
                !(player.getY() + player.getFitHeight() <= other.getY()) &&
                !(player.getY() >= other.getY() + other.getHeight()));
    }

    /**
     * * TODO : Add descriptions
     *
     * @param other Any Rectangle the player could collide with.
     * @param player The player.
     * @return Returns whether the top edge of the player is colliding with the bottom edge of the Rectangle or not.
     */
    public boolean isCollidingTop(Rectangle other, Player player){
        return(player.getY() <= other.getY() + other.getHeight());
    }

    /**
     * * TODO : Add descriptions
     *
     * @param other Any Rectangle the player could collide with.
     * @param player The player.
     * @return Returns whether the bottom edge of the player is colliding with the top edge of the Rectangle or not.
     */
    public boolean isCollidingBottom(Rectangle other, Player player){
        return(player.getY() + player.getFitHeight() >= other.getY() &&
                player.getX() + player.getFitWidth() > other.getX() &&
                player.getX() < other.getX() + other.getWidth());
    }

    /**
     * * TODO : Add descriptions
     *
     * @param other Any Rectangle the player could collide with.
     * @param player The player.
     * @return Returns whether the player is going to collide with the Rectangle when returning from jumping.
     */
    public boolean willCollide(Rectangle other, Player player){
        return(player.getY() + player.getFitHeight() + player.getvY() >= other.getY() &&
                player.getX() + player.getFitWidth() > other.getX() &&
                player.getX() < other.getX() + other.getWidth());
    }
}
