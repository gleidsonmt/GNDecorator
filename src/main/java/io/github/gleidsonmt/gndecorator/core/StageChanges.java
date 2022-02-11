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

import javafx.stage.Stage;

/**
 * Interface for access the stage changes.
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  07/07/2020
 */
public interface StageChanges {

    XYStage xy = XYStage.getInstance();

    default double getInitX(){
        return xy.getInitX();
    }

    default double getInitY(){
        return xy.getInitY();
    }

    default double getNewX(){
        return xy.getNewX();
    }

    default double getNewY(){
        return xy.getNewY();
    }

    default void setInitX(double x){
        xy.setInitX(x);
    }

    default void setInitY(double y){
        xy.setInitY(y);
    }

    default void setNewX(double x){
        xy.setNewX(x);
    }

    default void setNewY(double y){
        xy.setNewY(y);
    }

    default boolean setStageWidth(Stage stage, double width) {
        return xy.setStageWidth(stage, width);
    }

    default boolean setStageHeight(Stage stage, double height) {
        return xy.setStageHeight(stage, height);
    }

    default void setStageY(Stage stage, double y) {
        xy.setStageY(stage,y);
    }
}
