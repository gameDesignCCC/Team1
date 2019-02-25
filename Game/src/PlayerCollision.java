/*
 * Author(s):
 * Date: 24/10/2018
 */

public class PlayerCollision {

    /**
     * Player Collision Right
     *
     * @param player The player.
     * @return The right edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect collidingRight(Player player) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(player).contains(StaticRect.CollisionType.Right)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    /**
     * Player Collision Left
     *
     * @param player The player.
     * @return The left edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect collidingLeft(Player player) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(player).contains(StaticRect.CollisionType.Left)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    /**
     * Player Collision Top
     *
     * @param player The player.
     * @return The top edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect collidingTop(Player player) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(player).contains(StaticRect.CollisionType.Top)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    /**
     * Player Collision Bottom
     *
     * @param player The player.
     * @return The bottom edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public StaticRect collidingBottom(Player player) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(player).contains(StaticRect.CollisionType.Bottom)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    /**
     * Player Collision StaticRect
     *
     * @param staticRect Any StaticRect.
     * @param player     The player.
     * @return Player is colliding with staticRect.
     */
    public boolean isColliding(StaticRect staticRect, Player player) {
        return (staticRect.checkCollision(player).contains(StaticRect.CollisionType.Bottom) ||
                staticRect.checkCollision(player).contains(StaticRect.CollisionType.Right) ||
                staticRect.checkCollision(player).contains(StaticRect.CollisionType.Left) ||
                staticRect.checkCollision(player).contains(StaticRect.CollisionType.Top));
    }

    /**
     * Player Collision Enemy
     *
     * @param player The player.
     * @return Any Enemy in enemies the player is colliding with.
     */
    public boolean enemyCollision(Player player) {
        for (Enemy enemy : MainApplication.enemies) {
            if (enemy.checkPlayerCollision(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Player Collision Items
     *
     * @param player The player.
     * @return Any StaticRect with the type ITEM in sceneObjects the player is colliding with.
     */
    public StaticRect itemCollision(Player player) {
        for (Object obj : MainApplication.sceneObjects) {
            if (obj instanceof StaticRect) {
                StaticRect s = ((StaticRect) obj);
                if (s.getType() == StaticObject.Type.ITEM) {
                    if (isColliding(s, player)) {
                        return s;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Player Collision Exit
     *
     * @param player The player.
     * @return Any StaticRect with the type EXIT in sceneObjects the player is colliding with.
     */
    public StaticRect exitCollision(Player player) {
        for (Object obj : MainApplication.sceneObjects) {
            if (obj instanceof StaticRect) {
                StaticRect s = ((StaticRect) obj);
                if (s.getType() == StaticObject.Type.EXIT) {
                    if (isColliding(s, player)) {
                        return s;
                    }
                }
            }
        }
        return null;
    }

}
