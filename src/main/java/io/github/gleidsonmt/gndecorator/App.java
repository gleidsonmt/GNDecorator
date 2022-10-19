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

package io.github.gleidsonmt.gndecorator;

import io.github.gleidsonmt.gndecorator.core.DecoratorTheme;
import io.github.gleidsonmt.gndecorator.core.GNDecorator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/09/2018
 * Version 1.0
 */
public class App extends Application {

    private GNDecorator decorator = new GNDecorator();


    @Override
    public void start(Stage stage) throws Exception {
        VBox body = new VBox();
        body.setAlignment(Pos.CENTER);

        decorator.setContent(body);
        decorator.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/logo/logo_32.png")).toExternalForm()));
        decorator.setTitle(null);

        decorator.setMinHeight(300D);

//        decorator.fullBody();
        decorator.switchTheme(DecoratorTheme.MAC_YOSEMITE);
        decorator.show();

        Button addMenus = new Button("Adiciona menus");
        addMenus.setOnMouseClicked(this::handle);
        decorator.addControl(addMenus);

        Button addCustom = new Button("Add Custom");
        addCustom.setOnMouseClicked(event -> decorator.addControl(new Button("Custom Control")));

        Label lblWelcome = new Label("Welcome, Click on the buttons to see the behavior");
        lblWelcome.setStyle("-fx-font-size : 18pt;");

        Button block = new Button("Block or unblock controls");

        block.setOnMouseClicked(event -> {
            if(!decorator.isLocked())
                decorator.lockControls();
            else decorator.unLockControls();
        });

        VBox.setMargin(lblWelcome, new Insets(50));

        body.getChildren().addAll(lblWelcome, addMenus, block, addCustom);

        ScenicView.show(decorator.getWindow().getScene());
        decorator.switchTheme(DecoratorTheme.MAC_YOSEMITE);

        System.out.println("decorator.getBarHeight() = " + decorator.getBarHeight());

    }

    private void handle(MouseEvent mouseEvent) {
        Menu menuFile = new Menu("File");
        MenuItem menuNew = new MenuItem("New");
        MenuItem menuOpen = new MenuItem("Open");
        Menu menuOpenRecent = new Menu("Open Recent");
        MenuItem menuR1 = new MenuItem("File 1");
        MenuItem menuR2 = new MenuItem("File 2");
        menuOpenRecent.getItems().addAll(menuR1, menuR2);
        MenuItem close = new MenuItem("Close");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem revert = new MenuItem("Revert");
        MenuItem preferences = new MenuItem("Preferences");
        MenuItem quit = new MenuItem("Quit");

        menuFile.getItems().addAll(menuNew, menuOpen, menuOpenRecent,
                new SeparatorMenuItem(), close, save, saveAs, revert, new SeparatorMenuItem(),
                preferences, new SeparatorMenuItem(), quit);

        decorator.addMenu(menuFile);


    }

    public static void main(String[] args) {
        launch(args);
    }
}
//