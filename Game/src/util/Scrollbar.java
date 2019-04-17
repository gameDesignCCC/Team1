package util;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Scrollbar {

    private static Rectangle scrollBarFrame;
    private static Rectangle scrollBarSelector;

    private static EventHandler<MouseEvent> dragEventHandler = (e -> {});
    private static EventHandler<MouseEvent> releaseEventHandler = (e -> {});

    public Scrollbar(double x, double y, double width, double height){

        scrollBarFrame = new Rectangle(x, y, width, height);
        scrollBarFrame.setFill(Color.WHITE);
        scrollBarFrame.setOpacity(0.2);

        scrollBarSelector = new Rectangle(x, y, width, width * 10);
        scrollBarSelector.setFill(Color.WHITE);
        scrollBarSelector.setOnMouseDragged(this::mouseDraggedActionEvent);
        scrollBarSelector.setOnMouseReleased(this::mouseReleasedActionEvent);
    }

    public double getValue(){
        return (scrollBarSelector.getY() - scrollBarFrame.getY()) / (scrollBarFrame.getHeight() - scrollBarSelector.getHeight());
    }

    private void mouseDraggedActionEvent(MouseEvent e){
        if(e.getY() - scrollBarSelector.getHeight() / 2 >= scrollBarFrame.getY() && e.getY() <= scrollBarFrame.getY() + scrollBarFrame.getHeight() - scrollBarSelector.getHeight() / 2){
            scrollBarSelector.setY(e.getY() - scrollBarSelector.getHeight() / 2);
        } else if (e.getY() - scrollBarSelector.getHeight() / 2 < scrollBarFrame.getY()){
            scrollBarSelector.setY(scrollBarFrame.getY());
        } else if(e.getY() > scrollBarFrame.getY() + scrollBarFrame.getHeight() - scrollBarSelector.getHeight() / 2){
            scrollBarSelector.setY(scrollBarFrame.getY() + scrollBarFrame.getHeight() - scrollBarSelector.getHeight());
        }
        scrollBarSelector.setFill(Color.valueOf("#EEE"));
        dragEventHandler.handle(e);
    }

    public void mouseReleasedActionEvent(MouseEvent e){
        releaseEventHandler.handle(e);
        scrollBarSelector.setFill(Color.valueOf("#FFF"));
    }

    public void setDragEventHandler(EventHandler<MouseEvent> e){
        dragEventHandler = e;
    }

    public void setReleaseEventHandler(EventHandler<MouseEvent> e){
        releaseEventHandler = e;
    }

    public Node[] getElements(){
        return new Node[]{scrollBarFrame, scrollBarSelector};
    }

}
