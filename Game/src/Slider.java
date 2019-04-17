import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Slider {

    private static Rectangle sliderBar;
    private static Rectangle sliderButton;
    private static Label sliderLabel;

    private static EventHandler<MouseEvent> dragEventHandler = (e -> {});
    private static EventHandler<MouseEvent> releaseEventHandler = (e -> {});

    public Slider(double x, double y, double width, double height, String text){

        sliderBar = new Rectangle(x, y, width, height);
        sliderBar.setFill(Color.WHITE);
        sliderBar.setOpacity(0.2);

        // noinspection SuspiciousNameCombination
        sliderButton = new Rectangle(x, y, height, height);
        sliderButton.setFill(Color.WHITE);
        sliderButton.setOnMouseDragged(this::mouseDraggedActionEvent);
        sliderButton.setOnMouseReleased(this::mouseReleasedActionEvent);

        sliderLabel = new Label(text);
        sliderLabel.setLayoutX(x + height / 5);
        sliderLabel.setLayoutY(y - height / 5);
        sliderLabel.setTextFill(Color.WHITE);
        sliderLabel.setOpacity(0.5);
        sliderLabel.setStyle("-fx-font-size: " + height + "px; -fx-font-family: \"Russo One\";");
    }

    public double getValue(){
        return (sliderButton.getX() - sliderBar.getX()) / (sliderBar.getWidth() - sliderButton.getWidth());
    }

    private void mouseDraggedActionEvent(MouseEvent e){
        if(e.getX() - sliderButton.getWidth() / 2 >= sliderBar.getX() && e.getX() <= sliderBar.getX() + sliderBar.getWidth() - sliderButton.getWidth() / 2){
            sliderButton.setX(e.getX() - sliderButton.getWidth() / 2);
        } else if (e.getX() - sliderButton.getWidth() / 2 < sliderBar.getX()){
            sliderButton.setX(sliderBar.getX());
        } else if(e.getX() > sliderBar.getX() + sliderBar.getWidth() - sliderButton.getWidth() / 2){
            sliderButton.setX(sliderBar.getX() + sliderBar.getWidth() - sliderButton.getWidth());
        }
        sliderButton.setFill(Color.valueOf("#EEE"));
        dragEventHandler.handle(e);
    }

    public void mouseReleasedActionEvent(MouseEvent e){
        releaseEventHandler.handle(e);
        sliderButton.setFill(Color.valueOf("#FFF"));
    }

    public void setDragEventHandler(EventHandler<MouseEvent> e){
        dragEventHandler = e;
    }

    public void setReleaseEventHandler(EventHandler<MouseEvent> e){
        releaseEventHandler = e;
    }

    public Node[] getElements(){
        return new Node[]{sliderBar, sliderLabel, sliderButton};
    }

}
