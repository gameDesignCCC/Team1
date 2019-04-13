public class EnemyCollision {

    public static StaticRect colliding(Enemy enemy, StaticObject.CollisionDirection direction){

        StaticRect s = null;

        for (Object object : MainApplication.sceneObjects) {
            if (object instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) object;

                if (staticRect.getCollisionEnabled() && staticRect.checkCollision(enemy).contains(direction)) {
                    s = staticRect;
                    break;
                }
            }
        }
        return s;
    }

}
