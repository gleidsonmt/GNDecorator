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
import io.github.gleidson28.test.components.LeftBar;
import io.github.gleidson28.test.components.StageEvent;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/09/2018
 * Version 1.0
 */
public class Test extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        HBox content = new HBox(new Button("Hello World with a button!"), new Button("hello 2 tra afsçdfjk asdfadf"));
        content.setAlignment(Pos.CENTER);
        content.setPrefSize(1000,600);
        content.setStyle("-fx-background-color : green;");


        GNDecoratorT decorator = new GNDecoratorT();
        decorator.setContent(content);


        decorator.show();
//        decorator.testWithScenicView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
