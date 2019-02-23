/*
 * Name: Team1
 */

public class EnemyCollision {

    public StaticRect isCollidingRight(Enemy enemy) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                if(((StaticRect) object).getX() < enemy.getX() + enemy.getSpeed()) {
                    StaticRect staticRect = (StaticRect) object;

                    if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Right)) {
                        return staticRect;
                    }
                }
            }
        }
        return null;
    }

    public StaticRect isCollidingLeft(Enemy enemy) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                if (!(object instanceof Enemy)) {
                    StaticRect staticRect = (StaticRect) object;

                    if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Left)) {
                        return staticRect;
                    }
                }
            }
        }
        return null;
    }

    public StaticRect isCollidingTop(Enemy enemy) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                if (!(object instanceof Enemy)) {
                    StaticRect staticRect = (StaticRect) object;

                    if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Top)) {
                        return staticRect;
                    }
                }
            }
        }
        return null;
    }

    public StaticRect isCollidingBottom(Enemy enemy) {

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                if (!(object instanceof Enemy)) {
                    StaticRect staticRect = (StaticRect) object;

                    if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(StaticRect.CollisionType.Bottom)) {
                        return staticRect;
                    }
                }
            }
        }
        return null;
    }

}
