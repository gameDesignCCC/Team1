import javafx.scene.image.Image;

public class AnimatedRect extends StaticRect {

    private Image[] frames;
    private double frameRate;
    private long lastTimeMS;
    private int currentFrame = 0;

    /**
     * constructor
     *
     * @param x      x coordinate of rectangle
     * @param y      y coordinate of rectangle
     * @param width  width of rectangle
     * @param height height of rectangle
     * @param frames sprites of rectangle
     * @param type
     */
    public AnimatedRect(double x, double y, double width, double height, Image[] frames, Type type) {
        super(x, y, width, height, frames[0], type);

        this.frames = frames;
        this.frameRate = this.frames.length / 60.0;
        this.lastTimeMS = System.currentTimeMillis();
    }

    public void onUpdate( ) {
        if ( (System.currentTimeMillis() - lastTimeMS) / 100.0 >= 1.0 / frameRate ) {
            System.out.println("Elapsed Time: " + ((System.currentTimeMillis() - lastTimeMS) / 1000.0));
            currentFrame = (currentFrame + 1) % frames.length;
            this.rSprite.setImage(frames[currentFrame]);
            lastTimeMS = System.currentTimeMillis();
        }
    }
}
