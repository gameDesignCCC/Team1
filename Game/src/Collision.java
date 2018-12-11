/*
 * Author(s):
 * Date: 24/10/2018
 */

public class Collision {

    /**
     * Player Collision Right
     * @param player The player.
     * @return The right edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect isCollidingRight(Player player) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.checkCollision(player).contains(StaticRect.CollisionType.Right)  && staticRect.getCollisionEnabled()) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    /**
     * Player Collision Left
     * @param player The player.
     * @return The left edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect isCollidingLeft(Player player) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.checkCollision(player).contains(StaticRect.CollisionType.Left)  && staticRect.getCollisionEnabled()) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    /**
     * Player Collision Top
     * @param player The player.
     * @return The top edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect isCollidingTop(Player player) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.checkCollision(player).contains(StaticRect.CollisionType.Top)  && staticRect.getCollisionEnabled()) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    /**
     * Player Collision Bottom
     * @param player The player.
     * @return The bottom edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect isCollidingBottom(Player player) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.checkCollision(player).contains(StaticRect.CollisionType.Bottom) && staticRect.getCollisionEnabled()) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    /**
     * Player Collision Enemy
     * @param player The player.
     * @return Any Enemy in enemies the player is colliding with.
     */
    public boolean enemyCollision(Player player) {
        for (Enemy enemy : MainApplication.enemies) {
            if (enemy.checkCollision(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Player Collision Items
     * @param player The player.
     * @return Any StaticRect with the type ITEM in sceneObjects the player is colliding with.
     */
    public StaticRect itemCollision(Player player) {
        for (Object obj : MainApplication.sceneObjects) {
            if (obj instanceof StaticRect) {
                StaticRect s = ((StaticRect) obj);
                if (s.getType() == StaticObject.Type.ITEM) {
                    if (s.checkCollision(player).contains(StaticRect.CollisionType.Right) || s.checkCollision(player).contains(StaticRect.CollisionType.Left) ||
                            s.checkCollision(player).contains(StaticRect.CollisionType.Top) || s.checkCollision(player).contains(StaticRect.CollisionType.Bottom)) {
                        return s;
                    }
                }
            }
        }
        return null;
    }

}
