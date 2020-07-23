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

import javafx.geometry.BoundingBox;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/07/2020
 */
class LeftBar extends Pane implements StageChanges, StageBar {

    private final Stage stage;
    private final GNDecoratorT decorator;

    LeftBar(GNDecoratorT decorator) {
        this.getStyleClass().add("gn-left-bar");
        this.setId("gn-left-bar");
        this.setCursor(Cursor.W_RESIZE);
        this.setMinWidth(3D);
        this.stage = decorator.getStage();
        this.decorator = decorator;
        configActions();
    }

    private void configActions(){
        this.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {

                setInitX(event.getScreenX());
                setInitY(event.getScreenY());
                event.consume();

//                if(!decorator.isMaximized()){
//                    new BoundingBox(this.stage.getX(), this.stage.getY(),
//                            this.stage.getWidth(), this.stage.getHeight());
//                }
            }
        });

        this.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() ||
                    (getInitX() == -1 && getInitY() == -1)) {
                return;
            }

            if (this.stage.isFullScreen()) {
                return;
            }

            if (event.isStillSincePress()) {
                return;
            }

            setNewX(event.getScreenX());
            setNewY(event.getScreenY());

            double deltaX = getNewX() - getInitX();

            if (Cursor.W_RESIZE.equals(this.getCursor())) {
                if(setStageWidth(this.stage, this.stage.getWidth() - deltaX)){
                    this.stage.setX(this.stage.getX() + deltaX);
                }
                event.consume();
            }
        });
    }

    @Override
    public void changeCursor() {
        this.setCursor(Cursor.W_RESIZE);
    }
}
