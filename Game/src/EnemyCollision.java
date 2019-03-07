/*
 * Name: Team1
 */

public class EnemyCollision {

    public static StaticRect collidingRight(Enemy enemy) {

        StaticRect s = null;

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Right)) {
                    s = staticRect;
                    break;
                }
            }
        }
        return s;
    }

    public static StaticRect collidingLeft(Enemy enemy) {

        StaticRect s = null;

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Left)) {
                    s = staticRect;
                    break;
                }
            }
        }
        return s;
    }

    public static StaticRect collidingTop(Enemy enemy) {

        StaticRect s = null;

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {

                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Top)) {
                    s = staticRect;
                    break;
                }
            }
        }
        return s;
    }

    public static StaticRect collidingBottom(Enemy enemy) {

        StaticRect s = null;

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Bottom)) {
                    s = staticRect;
                    break;
                }
            }
        }
        return s;
    }

}
