/*
 * Author(s):
 * Date: 24/10/2018
 */

public class Collision{

    /**
     * Right edge of player is colliding.
     * @param player The player.
     * @return The right edge of the player is colliding with any static map object (StaticRect) in mapObjects.
     */
    public boolean isCollidingRight(Player player){

        for( StaticRect staticRect : MainApplication.mapObjects){
            if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Right)){
                return true;
            }
        }
        return false;
    }

    /**
     * Left edge of player is colliding.
     * @param player The player.
     * @return The left edge of the player is colliding with any static map object (StaticRect) in mapObjects.
     */
    public boolean isCollidingLeft(Player player){

        for( StaticRect staticRect : MainApplication.mapObjects){
            if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Left)){
                return true;
            }
        }
        return false;
    }

    /**
     * Top edge of player is colliding.
     * @param player The player.
     * @return The top edge of the player is colliding with any static map object (StaticRect) in mapObjects.
     */
    public boolean isCollidingTop(Player player){

        for( StaticRect staticRect : MainApplication.mapObjects){
            if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Top)){
                return true;
            }
        }
        return false;
    }

    /**
     * Bottom edge of player is colliding.
     * @param player The player.
     * @return The bottom edge of the player is colliding with any static map object (StaticRect) in mapObjects.
     */
    public boolean isCollidingBottom(Player player){

        for( StaticRect staticRect : MainApplication.mapObjects){
            if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Bottom)){
                return true;
            }
        }
        return false;
    }

}
