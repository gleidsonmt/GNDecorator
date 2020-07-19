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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

        HBox content = new HBox(new Label("Hello GNDecorator!"));
        content.setAlignment(Pos.CENTER);
//        content.setPrefSize(1200,800);
//        content.setStyle("-fx-background-color : green; -fx-border-color : blue;");


        GNDecoratorT decorator = new GNDecoratorT();
        decorator.setContent(content,1200,800);

        decorator.addMenu(new Menu("Archive"));
//        decorator.setBarHeight(80D);
//        decorator.setButtonsWidth(100);
//        decorator.setContent(content);

//        decorator.setMaximized(true);

        decorator.show();
//        decorator.testWithScenicView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
