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
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/07/2020
 */
public class Bar extends HBox implements StageChanges {

    private final Stage stage;
    private final GNDecoratorT decorator;

    private ObservableList<Button> defaultControls;

    private BoundingBox savedBounds  = null;
    private BoundingBox initialBound = null;

    private Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

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
//            System.out.println(stage.getX());

            double x = decorator.getStage().getX();

            if(decorator.getStage().isMaximized()){


                double width = decorator.getNoMaximizedBounds().getWidth();

                System.out.println(decorator.getNoMaximizedBounds().getWidth());
                System.out.println(event.getSceneX() - stage.getWidth());
                System.out.println(stage.getWidth() - event.getSceneX());
//                System.out.println(decorator.getBounds().get());

//                System.out.println(decorator.getStage().getX());

                if(bounds.getMaxX() < (event.getScreenX() + decorator.getNoMaximizedBounds().getWidth())) {
                    stage.setX(bounds.getMaxX() - (decorator.getNoMaximizedBounds().getWidth()));
                    System.out.println("first");
//                } else if( stage.getX() <= 0 && stage.getHeight() == bounds.getMaxY()){
//                } else if( bounds.getMinX() < (event.getScreenX() +  decorator.getBounds().getWidth() )) {
                } else if(bounds.getMinX() < (decorator.getNoMaximizedBounds().getWidth() - event.getScreenX())) {
                    stage.setX(0);
                    System.out.println("second");
                } else {
                    stage.setX(event.getScreenX() - (decorator.getNoMaximizedBounds().getWidth() / 2));
                    System.out.println("third");
                }


                if(decorator.getNoMaximizedBounds().getHeight() > bounds.getMaxY()){
                    stage.setHeight(bounds.getMaxY() - 100);
                } else {
                    stage.setHeight(decorator.getNoMaximizedBounds().getHeight());
                }

                if(decorator.getNoMaximizedBounds().getWidth() > bounds.getMaxX()){
                    stage.setWidth(bounds.getWidth() - 200);
                } else {
                    stage.setWidth(decorator.getNoMaximizedBounds().getWidth() - olderBounds.getWidth());
                }

//                stage.setX(event.getScreenX() - (decorator.getBounds().getWidth() / 2));
//                stage.setX(bounds.getMaxX() - (decorator.getBounds().getWidth()));
                stage.setY(0);
//                stage.setHeight(decorator.getBounds().getHeight());
//
                stage.setMaximized(false);

//                if(stage.getX() < decorator.getBounds().getMinX()){
//                } else if((stage.getX() + olderBounds.getWidth() )  > olderBounds.getMaxX()){
//                    stage.setX(decorator.getBounds().getMaxX() - stage.getWidth());
//                }
            } else {

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
        });
    }
}
