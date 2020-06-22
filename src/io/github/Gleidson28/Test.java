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
package io.github.Gleidson28;

import io.github.Gleidson28.decorator.GNDecorator;
import io.github.Gleidson28.decorator.buttons.ButtonTest;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/09/2018
 * Version 1.0
 */
public class Test extends Application {

    @Override
    public void start(Stage stage) throws Exception {

//        Parent root = FXMLLoader.load(getClass().getResource("/com/gn/resources/sample.fxml"));

        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setPrefSize(400,400);
        GNDecorator window = new GNDecorator();

//        window.hideCustoms();
        Button btn = new Button("Action");
        btn.setOnMouseClicked(event ->{
//            window.removeControls();
            window.floatActions();
//            window.hideControls();

//            System.out.println("controls " + window.getControls());
//            window.showCustoms();
//            window.block();
//            window.setResizable(true);
        });

//        window.addButton(ButtonType.FULL_EFFECT);
        content.getChildren().add(btn);
        window.setContent(content);


        window.setTitle("Application");
//        window.addButton(ButtonType.FULL_EFFECT);
        window.setTitle(null);

        ButtonTest a1 = new ButtonTest("Button 1");
        ButtonTest a2 = new ButtonTest("Button 2");
        ButtonTest a3 = new ButtonTest("Button 3");


        window.addControl(2, a1);
        window.addControl(4,a2);
        window.addControl(a3);


        Menu menu = new Menu("File");
        menu.getItems().add(new MenuItem("Open"));
        menu.getItems().add(new MenuItem("Close"));

        Menu menu1 = new Menu("Save");
        menu1.getItems().add(new MenuItem("Save"));
        menu1.getItems().add(new MenuItem("Save as"));

        Menu menu2 = new Menu("About");
        menu2.getItems().add(new MenuItem("About us"));
        menu2.getItems().add(new MenuItem("Exit"));

        Menu btn_ico = new Menu();
        btn_ico.setStyle("-fx-background-color : transparent;");
        btn_ico.show();
        btn_ico.getItems().clear();

//        System.out.println(btn_ico.fire());

        SVGPath icon = new SVGPath();
        icon.setId("icon");
        icon.setContent("M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z");
        btn_ico.setGraphic(icon);
        icon.setFill(Color.web("#999"));
        icon.setOnMouseClicked(event -> System.out.println("teste"));

        window.addMenu(btn_ico);
        window.addMenu(menu);
        window.addMenu(2,menu1);
        window.addMenu(menu2);


//        window.setMaximized(true);
        window.fullBody();
//        window.addCustom(new FullScreen());
//        window.centralizeTitle();
        window.show();

//        window.getScene().getStylesheets().addAll(getClass().getResource("/com/gn/resources/css/custom.css").toExternalForm());

//        ScenicView.show(window.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
