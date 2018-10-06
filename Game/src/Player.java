import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {

    double vX = 10.0;
    double vY = 10.0;
    double g = 4;

    Player(double x, double y, double width, double height){
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);

    }

    public boolean isCollidingLeft(){

        if(this.getX() <= 0){
            return true;
        }

        return false;
    }

    public boolean isCollidingRight(){

        if(this.getX() + this.getWidth() >= MainApplication.winX){
            return true;
        }

        return false;
    }

    public boolean isCollidingBottom(){

        if(this.getY() + this.getHeight() >= MainApplication.winY){
            return true;
        }

        return false;
    }

    public void moveRight(){
        this.setX( this.getX() + vX);
    }

    public void moveLeft(){
        this.setX( this.getX() - vX);
    }

    public void jump(){
        this.setTranslateY(vY);
        vY -= g;
        if(this.getY() > MainApplication.winY){
            vY = 0;
            this.setY(MainApplication.winY - this.getHeight());
        }
    }

}
