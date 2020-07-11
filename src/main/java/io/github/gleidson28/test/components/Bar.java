/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.gleidson28.test.components;

import io.github.gleidson28.decorator.GNDecorator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/07/2020
 */
public class Bar extends HBox implements StageChanges, StageReposition {

    private final Stage stage;
    private final GNDecoratorT decorator;

    private ObservableList<Button> defaultControls;

    private BoundingBox savedBounds  = null;
    private BoundingBox initialBound = null;

    private final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

    public Bar(GNDecoratorT decorator) {

        defaultControls = FXCollections.observableArrayList(
            new Maximize(decorator)
        );

        this.setPrefSize(200,40);
        this.setStyle("-fx-background-color : blue");
        this.decorator = decorator;
        this.stage = decorator.getStage();

        this.setAlignment(Pos.CENTER);
        configActions();


        this.getChildren().setAll(defaultControls);
    }

    private void configActions(){
        this.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                setInitX(event.getScreenX());
                setInitY(event.getScreenY());
                event.consume();
            }
        });

        this.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                decorator.getStage().setMaximized(!decorator.getStage().isMaximized());
                decorator.getStage().centerOnScreen();
            }
        });

        this.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() || getInitX() == -1 ) {
                return;
            }

            if (event.isStillSincePress()) {
                return;
            }

            if (savedBounds == null) {
                savedBounds = decorator.getNoMaximizedBounds();
            }

            BoundingBox olderBounds = decorator.getNoMaximizedBounds();

            if(decorator.getStage().isMaximized()){

                if(bounds.getMaxX() < (event.getScreenX() + decorator.getNoMaximizedBounds().getWidth())) {
                    stage.setX(bounds.getMaxX() - (decorator.getNoMaximizedBounds().getWidth()));
                } else if(bounds.getMinX() < (decorator.getNoMaximizedBounds().getWidth() - event.getScreenX())) {
                    stage.setX(0);
                } else {
                    stage.setX(event.getScreenX() - (decorator.getNoMaximizedBounds().getWidth() / 2));
                }

                if(decorator.getNoMaximizedBounds().getHeight() > bounds.getMaxY()){
                    stage.setHeight(bounds.getMaxY() - 100);
                } else {
                    stage.setHeight(decorator.getNoMaximizedBounds().getHeight());
                }

                if(decorator.getNoMaximizedBounds().getWidth() > bounds.getMaxX()){
                    stage.setWidth(bounds.getWidth() - 200);
                } else {
                    stage.setWidth(decorator.getNoMaximizedBounds().getWidth());
                }
                stage.setY(0);
                stage.setMaximized(false);
            } else {

                Stage stage = decorator.getTranslucentStage();
                decorator.getStage().setAlwaysOnTop(true);

                if(isOnTopLeft(event)) {
                    repositionOnTopLeft(stage,0);
                    stage.show();
                } else if(isOnBottomLeft(event)) {
                    repositionOnBottomLeft(stage,20);
                    stage.show();
                } else if(isOnTopRight(event)) {
                    repositionOnTopRight(stage,20);
                    stage.show();
                } else if(isOnBottomRight(event)) {
                    repositionOnBottomRight(stage,20);
                    stage.show();
                } else if(isOnRight(event)) {
                    repositionOnRight(stage,20);
                    stage.show();
                } else if(isOnLeft(event)) {
                    repositionOnLeft(stage,20);
                    stage.show();
                } else if(isOnTop(event)){
                    repositionOnTop(stage,20);
                    stage.show();
                } else {
                    decorator.getTranslucentStage().close();
                }

                setNewX(event.getScreenX());
                setNewY(event.getScreenY());

                double deltaX = getNewX() - getInitX();
                double deltaY = getNewY() - getInitY();

                setInitX(getNewX());
                setInitY(getNewY());

                this.stage.setX(this.stage.getX() + deltaX);
                setStageY(this.stage, this.stage.getY() + deltaY);
            }

            this.setCursor(Cursor.MOVE);

        });

        this.setOnMouseReleased(event -> {
            this.setCursor(Cursor.HAND);
            Stage stage = decorator.getStage();
            this.decorator.getStage().setAlwaysOnTop(false);

            if(isOnTopLeft(event)) {
                repositionOnTopLeft(stage,0);
            } else if(isOnBottomLeft(event)) {
                repositionOnBottomLeft(stage,0);
            } else if(isOnTopRight(event)) {
                repositionOnTopRight(stage,0);
            } else if(isOnBottomRight(event)) {
                repositionOnBottomRight(stage,0);
            } else if(isOnRight(event)) {
                repositionOnRight(stage,0);
            } else if(isOnLeft(event)) {
                repositionOnLeft(stage,0);
            } else if(isOnTop(event)){
                repositionOnTop(stage,0);
            } else {
                decorator.getTranslucentStage().close();
            }

            decorator.getTranslucentStage().close();

        });
    }

    private boolean isOnTopLeft(MouseEvent event){
        return event.getScreenY() <= 0 && event.getScreenX() <= 0;
    }

    private boolean isOnBottomLeft(MouseEvent event){
        return event.getScreenX() <= 0 && event.getScreenY() >= bounds.getMaxY();
    }

    private boolean isOnTopRight(MouseEvent event){
        return event.getScreenX() >= (bounds.getMaxX() -2) && event.getScreenY() <= bounds.getMinY();
    }

    private boolean isOnBottomRight(MouseEvent event){
        return event.getScreenX() >= (bounds.getMaxX() - 2) && event.getScreenY() >= bounds.getMaxY();
    }

    private boolean isOnRight(MouseEvent event){
        return event.getScreenX() >= ( bounds.getMaxX() -2 );
    }

    private boolean isOnLeft(MouseEvent event){
        return event.getScreenX() <= 0;
    }

    private boolean isOnTop(MouseEvent event){
        return event.getScreenY() <= 0;
    }
}
