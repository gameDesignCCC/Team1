/*
 * Author(s):
 * Date: 13/10/2018
 */

public class Collision {

    public boolean isCollidingRight(Player player){

        for( StaticRect staticRect : MainApplication.mapObjects){
            if(staticRect.checkCollision(player).contains(StaticRect.CollisionType.Right)){
                return true;
            }
        }
            return false;
    }

    /**
     * TODO : Add desc
     * @param player
     * @return
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
     * TODO : Add desc
     * @param player
     * @return
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
