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

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  10/07/2020
 */
public class TranslucentStage extends Stage implements StageReposition {

    TranslucentStage() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color : transparent;");
        Parent foreground = new StackPane();
        foreground.setStyle("-fx-background-color : #ffffff15; " +
//        foreground.setStyle("-fx-background-color : red; " +
                "-fx-effect: dropshadow( gaussian , rgba(0,0,0,.9) , 10, 0 , 0 , 0 );" +
                "-fx-border-color : #00000020; -fx-border-width : 1;");
        root.getChildren().add(foreground);
        root.setPadding(new Insets(5D));
//        root.setStyle("-fx-background-color : red;");

        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.TRANSPARENT);
        this.initStyle(StageStyle.TRANSPARENT);
        this.setScene(scene);

//        this.initOwner(decorator.getStage());
//        decorator.getStage().initOwner(this);

    }
}
