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

import io.github.gleidson28.test.components.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/09/2018
 * Version 1.0
 */
public class Mac extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent content = new HBox(new Button("Hello GNDecorator!"));
//        content.setAlignment(Pos.CENTER);

//        Parent f = new ComboBox<>();
//        f.getpre


        GNDecoratorT decorator = new GNDecoratorT();
//        decorator.setContent(content,1200,800);
        decorator.setContent(content);

        decorator.setTitle("GNDecorator 0.3");

        Menu options = new Menu("Options");
        MenuItem macTheme = new MenuItem("Mac Yosemite theme");
        MenuItem defaultTheme = new MenuItem("Default Theme");
        MenuItem switchLight = new MenuItem("Switch Light");
        MenuItem fullscreen = new MenuItem("Fullscreen");
        options.getItems().addAll(macTheme, defaultTheme, switchLight, fullscreen);
        decorator.addMenu(options);
        decorator.addMenu(new Menu("Edit"));

        Menu menuAction = new Menu("Menu Action");

        menuAction.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            System.out.println("menu action event");
        });

        decorator.addMenu(menuAction);

        macTheme.setOnAction( e -> decorator.switchTheme(Theme.MAC_YOSEMITE));

        defaultTheme.setOnAction( e -> decorator.switchTheme(Theme.DEFAULT));

        switchLight.setOnAction(e -> decorator.setDark(!decorator.isDark()));

        fullscreen.setOnAction(e -> {
            decorator.setFullScreen(true);
        });

        decorator.setFullBody(true);
//
//        decorator.setMaximized(true);
        decorator.setMinHeight(300D);
//        decorator.setFullScreen(true);
        decorator.addControl(new Button("Custom Control"));
        decorator.addControl(new ComboBox<>());
        
//        decorator.setWidth(500);
//        decorator.setHeight(500);
        
//        decorator.setMaximized(true);
//        decorator.setResizable(true);
//        decorator.getIcons().add(new Image("/img/close.png"));

//        decorator.setDark(true);
        decorator.addStylesheets(getClass().getResource("/theme/master.css").toExternalForm());

//        decorator.initTheme(Theme.MAC_YOSEMITE);

        decorator.show();

//        decorator.testWithScenicView();


//        stage.setScene(new Scene(content,800,600));
//        stage.setMaximized(true);
//        stage.setResizable(false);
//        stage.set(true);
//        stage.setFullScreenExitHint();
//        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
