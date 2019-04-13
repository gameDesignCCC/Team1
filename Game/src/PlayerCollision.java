public class PlayerCollision {

    /**
     * Player Collision Right
     *
     * @param player The player.
     * @return The right edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public static StaticRect collidingRight(Player player) {

        StaticRect s = null;

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(player).contains(StaticObject.CollisionDirection.Right)) {
                    s = staticRect;
                    break;
                }
            }
        }
        return s;
    }

    /**
     * Player Collision Left
     *
     * @param player The player.
     * @return The left edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public static StaticRect collidingLeft(Player player) {

        StaticRect s = null;

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(player).contains(StaticObject.CollisionDirection.Left)) {
                    s = staticRect;
                    break;
                }
            }
        }
        return s;
    }

    /**
     * Player Collision Top
     *
     * @param player The player.
     * @return The top edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public static StaticRect collidingTop(Player player) {

        StaticRect s = null;

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(player).contains(StaticObject.CollisionDirection.Top)) {
                    s = staticRect;
                    break;
                }
            }
        }
        return s;
    }

    /**
     * Player Collision Bottom
     *
     * @param player The player.
     * @return The bottom edge of the player is colliding with any static map object (StaticRect) in sceneObjects.
     */
    public static StaticRect collidingBottom(Player player) {

        StaticRect s = null;

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(player).contains(StaticObject.CollisionDirection.Bottom)) {
                    s = staticRect;
                    break;
                }
            }
        }
        return s;
    }

    /**
     * Player Collision StaticRect
     *
     * @param staticRect Any StaticRect.
     * @param player     The player.
     * @return Player is colliding with staticRect.
     */
    public static boolean isColliding(StaticRect staticRect, Player player) {
        return (staticRect.checkCollision(player).contains(StaticObject.CollisionDirection.Bottom) ||
                staticRect.checkCollision(player).contains(StaticObject.CollisionDirection.Right) ||
                staticRect.checkCollision(player).contains(StaticObject.CollisionDirection.Left) ||
                staticRect.checkCollision(player).contains(StaticObject.CollisionDirection.Top));
    }

    /**
     * Player Collision Enemy
     *
     * @param player The player.
     * @return Any Enemy in enemies the player is colliding with.
     */
    public static boolean enemyCollision(Player player) {

        boolean result = false;

        for (Enemy enemy : MainApplication.enemies) {
            if (enemy.checkPlayerCollision(player)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Player Collision Items
     *
     * @param player The player.
     * @return Any StaticRect with the type ITEM in sceneObjects the player is colliding with.
     */
    public static StaticRect itemCollision(Player player) {

        StaticRect s = null;

        for (Object obj : MainApplication.sceneObjects) {
            if (obj instanceof StaticRect) {
                StaticRect staticRect = ((StaticRect) obj);
                if (staticRect.getType() == StaticObject.Type.ITEM) {
                    if (isColliding(staticRect, player)) {
                        s = staticRect;
                        break;
                    }
                }
            }
        }
        return s;
    }

    /**
     * Player Collision Exit
     *
     * @param player The player.
     * @return Any StaticRect with the type EXIT in sceneObjects the player is colliding with.
     */
    public static StaticRect exitCollision(Player player) {

        StaticRect s = null;

        for (Object obj : MainApplication.sceneObjects) {
            if (obj instanceof StaticRect) {
                StaticRect staticRect = ((StaticRect) obj);
                if (staticRect.getType() == StaticObject.Type.EXIT) {
                    if (isColliding(staticRect, player)) {
                        s = staticRect;
                        break;
                    }
                }
            }
        }
        return s;
    }

}
