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

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/09/2018
 * Version 1.0
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        HBox content = new HBox();

        GNDecorator decorator = new GNDecorator();
        decorator.setContent(content);


        decorator.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/logo/logo_32.png")).toExternalForm()));


        decorator.setTitle(null);

        Button block = new Button("Block or unblock controls");
        
        block.setOnMouseClicked(event -> {
            if(!decorator.isLocked())
            decorator.lockControls();
            else decorator.unLockControls();
        });

        content.getChildren().add(block);

        decorator.setMinHeight(300D);

//        decorator.fullBody();
        decorator.switchTheme(Theme.MAC_YOSEMITE);
//        decorator.testWithScenicView();
        decorator.show();

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
//
        decorator.addControl(new Button("wlecome"));
        decorator.addMenu(menuFile);


//        ScenicView.show(decorator.getWindow().getScene());


    }

    public static void main(String[] args) {
        launch(args);
    }
}
//