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
 * along with stage program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.gleidsonmt.gndecorator.core;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  11/07/paddingpadding
 */
interface StageReposition {

    Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    double padding = 20;

    default void repositionOnTop(Stage stage, double padding){
        stage.setX(bounds.getMinX() + padding);
        stage.setY(bounds.getMinY() + padding);
        stage.setWidth(bounds.getWidth() - (padding * 2));
        stage.setHeight(bounds.getHeight() - (padding * 2));
    }

    default void repositionOnRight(Stage stage, double padding){
        stage.setX( (bounds.getMaxX() / 2) + padding );
        stage.setY(bounds.getMinY() + padding);
        stage.setWidth((bounds.getWidth() / 2) - (padding * 2));
        stage.setHeight(bounds.getHeight() - (padding * 2));
    }

    default void repositionOnLeft(Stage stage, double padding){
        stage.setX(bounds.getMinX() + padding);
        stage.setY(bounds.getMinY() + padding);
        stage.setWidth((bounds.getWidth() / 2) - (padding * 2));
        stage.setHeight(bounds.getMaxY() - 40);
    }

    default void repositionOnTopLeft(Stage stage, double padding){
        stage.setX(bounds.getMinX() + padding);
        stage.setY(bounds.getMinY() + padding);
        stage.setWidth(bounds.getWidth() / 2);
        stage.setHeight(bounds.getHeight() / 2);
    }

    default void repositionOnTopRight(Stage stage, double padding){
        stage.setX( (bounds.getMaxX() / 2) + padding );
        stage.setY(bounds.getMinY() + padding);
        stage.setWidth(bounds.getWidth() / 2);
        stage.setHeight(bounds.getHeight() / 2);
    }

    default void repositionOnBottomLeft(Stage stage, double padding){
        stage.setX(bounds.getMinX() + padding);
        stage.setY(bounds.getMaxY() / 2);
        stage.setWidth(bounds.getWidth() / 2);
        stage.setHeight(bounds.getHeight() / 2);
    }

    default void repositionOnBottomRight(Stage stage, double padding){
        stage.setX( (bounds.getMaxX() / 2) + padding );
        stage.setY(bounds.getMaxY() / 2);
        stage.setWidth(bounds.getWidth() / 2);
        stage.setHeight(bounds.getHeight() / 2);
    }
}
