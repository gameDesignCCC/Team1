/*
 * Author(s):
 * Date: 24/10/2018
 */

public class Collision{

    /**
     * Right edge of player is colliding.
     * @param player The player.
     * @return The right edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect isCollidingRight(Player player){

        for( Object object : MainApplication.sceneObjects){
            if ( object instanceof StaticRect ) {
                StaticRect staticRect = (StaticRect) object;
                if (staticRect.checkCollision(player).contains(StaticRect.CollisionType.Right)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    /**
     * Left edge of player is colliding.
     * @param player The player.
     * @return The left edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect isCollidingLeft(Player player){

        for( Object object: MainApplication.sceneObjects){
            if ( object instanceof StaticRect ) {
                StaticRect staticRect = (StaticRect) object;

                if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Left)){
                    return staticRect;
                }
            }

        }
        return null;
    }

    /**
     * Top edge of player is colliding.
     * @param player The player.
     * @return The top edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect isCollidingTop(Player player){

        for( Object object: MainApplication.sceneObjects){
            if ( object instanceof StaticRect ) {
                StaticRect staticRect = (StaticRect) object;

                if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Top)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    /**
     * Bottom edge of player is colliding.
     * @param player The player.
     * @return The bottom edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect isCollidingBottom(Player player){

        for( Object object : MainApplication.sceneObjects){
            if( object instanceof  StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.checkCollision(player).contains(StaticRect.CollisionType.Bottom)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    public boolean enemyCollision(Player player){
        for(Enemies enemy : MainApplication.enemies){
            if(enemy.checkCollision(player)){
                return true;
            }
        }
        return false;
    }

}
