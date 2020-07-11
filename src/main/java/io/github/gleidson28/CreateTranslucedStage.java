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

package io.github.gleidson28;

import io.github.gleidson28.test.components.GNDecoratorT;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Stack;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/09/2018
 * Version 1.0
 */
public class CreateTranslucedStage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
// #ffffff10

        StackPane root = new StackPane();
        Parent foreground = new StackPane();
        foreground.setStyle("-fx-background-color : #ffffff10; " +
                "-fx-effect: dropshadow( gaussian , rgba(0,0,0,.8) , 10, 0 , 0 , 0 );" +
                "-fx-border-color : #00000010; -fx-border-width : 1.5;");
        root.getChildren().add(foreground);
        root.setPadding(new Insets(5D));
//        root.setStyle("-fx-background-color : red;");

        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        stage.show();

//        decorator.testWithScenicView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
