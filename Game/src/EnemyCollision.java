/*
 * Name: Team1
 */

public class EnemyCollision {

    public StaticRect collidingRight(Enemy enemy) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.getX() < enemy.getX() + enemy.getWidth() + enemy.getSpeed() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Right)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    public StaticRect collidingLeft(Enemy enemy) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Left)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    public StaticRect collidingTop(Enemy enemy) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {

                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Top)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

    public StaticRect collidingBottom(Enemy enemy) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Bottom)) {
                    return staticRect;
                }
            }
        }
        return null;
    }

}
