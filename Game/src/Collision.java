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
    public StaticRect isCollidingRight(Player player){

        for( StaticRect staticRect : MainApplication.mapObjects){
            if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Right)){
                return staticRect;
            }
        }
        return null;
    }

    /**
     * Left edge of player is colliding.
     * @param player The player.
     * @return The left edge of the player is colliding with any static map object (StaticRect) in mapObjects.
     */
    public StaticRect isCollidingLeft(Player player){

        for( StaticRect staticRect : MainApplication.mapObjects){
            if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Left)){
                return staticRect;
            }
        }
        return null;
    }

    /**
     * Top edge of player is colliding.
     * @param player The player.
     * @return The top edge of the player is colliding with any static map object (StaticRect) in mapObjects.
     */
    public StaticRect isCollidingTop(Player player){

        for( StaticRect staticRect : MainApplication.mapObjects){
            if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Top)){
                return staticRect;
            }
        }
        return null;
    }

    /**
     * Bottom edge of player is colliding.
     * @param player The player.
     * @return The bottom edge of the player is colliding with any static map object (StaticRect) in mapObjects.
     */
    public StaticRect isCollidingBottom(Player player){

        for( StaticRect staticRect : MainApplication.mapObjects){
            if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Bottom)){
                return staticRect;
            }
        }
        return null;
    }

}
