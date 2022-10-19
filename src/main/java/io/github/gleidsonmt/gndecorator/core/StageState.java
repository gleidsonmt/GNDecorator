package io.github.gleidsonmt.gndecorator.core;

import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class represents the update for new event change the size or position on stage.
 * If one component needs a initial event this class provides the initial postion and sets the new position in the end of event.
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/10/2022
 */
public final class StageState  {

    private double initX = 0;
    private double initY = 0;
    private double newX = 0;
    private double newY = 0;

    private Stage stage;
    private GNDecorator decorator;
    private BoundingBox noMaximizedBounds;

    StageState (GNDecorator _decorator, Stage _stage) {
        this.decorator = _decorator;
        this.stage = _stage;
//        this.noMaximizedBounds = _noMaximizedBounds;
    }

    public double getInitX() {
        return initX;
    }

    public void setInitX(double initX) {
        this.initX = initX;
    }

    public double getInitY() {
        return initY;
    }

    public void setInitY(double initY) {
        this.initY = initY;
    }

    public double getNewX() {
        return newX;
    }

    public void setNewX(double newX) {
        this.newX = newX;
    }

    public double getNewY() {
        return newY;
    }

    public void setNewY(double newY) {
        this.newY = newY;
    }

    @Deprecated
    public boolean setStageWidth(Stage stage, double width) {
        if (width >= stage.getMinWidth()) {
            stage.setWidth(width);
            initX = newX;
            return true;
        }
        return false;
    }

    public boolean setStageWidth(double width) {
        if (width >= stage.getMinWidth()) {
            stage.setWidth(width);
            initX = newX;
            return true;
        }
        return false;
    }

    @Deprecated
    public boolean setStageHeight(Stage stage, double height) {
        if (height >= stage.getMinHeight()) {
            stage.setHeight(height);
            initY = newY;
            return true;
        }
        return false;
    }

    public boolean setStageHeight(double height) {
        if (height >= stage.getMinHeight()) {
            stage.setHeight(height);
            initY = newY;
            return true;
        }
        return false;
    }

    @Deprecated
    public void setStageY(Stage stage, double y) {
        try {
            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            if (screensForRectangle.size() > 0) {
                Screen screen = screensForRectangle.get(0);
                Rectangle2D visualBounds = screen.getVisualBounds();
                if (y < visualBounds.getHeight()) {
                    stage.setY(y);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void setStageY(double y) {
        try {
            ObservableList<Screen> screensForRectangle =
                    Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());

            if (screensForRectangle.size() > 0) {
                Screen screen = screensForRectangle.get(0);
                Rectangle2D visualBounds = screen.getVisualBounds();
                if (y < visualBounds.getHeight()) {
                    stage.setY(y);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    Stage getStage() {
        return this.stage;
    }
    GNDecorator getDecorator() {
        return decorator;
    }

    BoundingBox noMaximizedBounds() {
        return noMaximizedBounds;
    }

    void updateNoMaximizedBounds(BoundingBox box) {
        noMaximizedBounds = box;
    }
}
