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

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  08/07/2020
 */
public class BottomLeftAnchor extends Path implements StageChanges {

    private final Stage stage;

    protected BottomLeftAnchor(Stage stage) {
        this.getStyleClass().add("gn-bottom-left");
        this.setId("gn-bottom-left");
        this.setCursor(Cursor.SW_RESIZE);
        this.setRotate(270D);
        this.stage = stage;
        configPaths();
        configActions();
    }

    private void configPaths(){
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.TRANSPARENT);
        this.setStrokeType(StrokeType.INSIDE);
        MoveTo moveTo = new MoveTo(100D, -40D);
        LineTo line1 = new LineTo(120D, -40D);
        LineTo line2 = new LineTo(120D, -37D);
        LineTo line3 = new LineTo(103D, -37D);
        LineTo line4 = new LineTo(103D, -20D);
        LineTo line5 = new LineTo(100D, -20D);
        ClosePath closePath = new ClosePath();
        this.getElements().addAll(moveTo, line1, line2, line3, line4, line5, closePath);
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
            if (!event.isPrimaryButtonDown() || (getInitX() == -1 && getInitY() == -1)) {
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
            double deltaY = getNewY() - getInitY();

            if (Cursor.SW_RESIZE.equals(this.getCursor())) {
                if (setStageWidth(this.stage, this.stage.getWidth() - deltaX)) {
                     this.stage.setX(this.stage.getX() + deltaX);
                }
                setStageHeight(this.stage, this.stage.getHeight() + deltaY);
                event.consume();
            }
        });
    }
}
