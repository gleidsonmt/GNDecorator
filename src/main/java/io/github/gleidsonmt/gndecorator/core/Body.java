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
package io.github.gleidsonmt.gndecorator.core;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** Enviroment class for the container
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/07/2020
 */
public class Body extends AnchorPane implements AlignUtils {


    public Body(Stage stage, GNDecorator decorator, Container container, Bar bar,
                TopBar topBar, RightBar rightBar,
                BottomBar bottomBar, LeftBar leftBar,
                TopLeftAnchor topLeftAnchor, TopRightAnchor topRightAnchor,
                BottomLeftAnchor bottomLeftAnchor, BottomRightAnchor bottomRightAnchor) {

        this.getStyleClass().add("gn-body");
        this.setId("gn-body");

        this.getChildren().addAll(container, bar, topBar, rightBar,
                bottomBar, leftBar, topLeftAnchor, topRightAnchor,
                bottomLeftAnchor, bottomRightAnchor);

        decorator.resizableProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue) getChildren().stream().
                    filter(e -> e instanceof StageBar)
                    .map(e -> (StageBar) e)
                    .forEach(StageBar::changeCursor);
            else getChildren().stream().
                    filter(e -> e instanceof StageBar)
                    .map(e -> (StageBar) e)
                    .forEach(e -> ((Node) e).setCursor(Cursor.DEFAULT));
            bar.disableActions(!newValue);
        });

        decorator.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) getChildren().stream().
                    filter(e -> e instanceof StageBar)
                    .map(e -> (StageBar) e)
                    .forEach(e -> ((Node) e).setCursor(Cursor.DEFAULT));
            else getChildren().stream().
                    filter(e -> e instanceof StageBar)
                    .map(e -> (StageBar) e)
                    .forEach(StageBar::changeCursor);
        });

        stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) getChildren().stream()
                    .filter(e -> e instanceof StageBar)
                    .map(e -> (StageBar) e)
                    .forEach(e -> ((Node) e).setCursor(Cursor.DEFAULT));
            else getChildren().stream()
                    .filter(e -> e instanceof StageBar)
                    .map(e -> (StageBar) e)
                    .forEach(StageBar::changeCursor);
        });

        alignTopAnchor(topBar);
        alignRightAnchor(rightBar);
        alignBottomAnchor(bottomBar);
        alignLeftAnchor(leftBar);

        alignTopLeftAnchor(topLeftAnchor);
        alignTopRightAnchor(topRightAnchor);
        alignBottomLeftAnchor(bottomLeftAnchor);
        alignBottomRightAnchor(bottomRightAnchor);

        alignTopAnchor(bar);

        alignContent(container,bar.getPrefHeight());

    }

    void fullBody(Container container) {
        alignContent(container, 0);
    }
}
