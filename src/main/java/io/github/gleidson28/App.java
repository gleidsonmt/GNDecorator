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

import io.github.gleidson28.decorator.GNDecorator;
import io.github.gleidson28.decorator.Theme;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/09/2018
 * Version 1.0
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent content = new HBox();

        GNDecorator decorator = new GNDecorator();
        decorator.setContent(content);


        decorator.setTitle("GNDecorator 0.3");

        decorator.setMinHeight(300D);
        decorator.fullBody();
        decorator.switchTheme(Theme.MAC_YOSEMITE);

        decorator.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
