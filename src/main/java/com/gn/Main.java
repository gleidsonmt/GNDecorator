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
package com.gn;

import com.gn.decorator.GNDecorator;
import com.gn.decorator.background.GNBackground;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/09/2018
 * Version 1.0
 */
public class Main implements Initializable {


    @FXML
    private ToggleGroup color;

    @FXML
    private JFXColorPicker barColor;


    @FXML
    private JFXToggleButton highlight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void create(){
        GNDecorator decorator = new GNDecorator();
        decorator.setContent(new VBox());
        decorator.initTheme(GNDecorator.Theme.CUSTOM);

        decorator.getBackground().setBackground(new Background(new BackgroundFill(barColor.getValue(), CornerRadii.EMPTY, Insets.EMPTY)));


//        decorator.getScene().getStylesheets().addAll(getClass().getResource("/css/theme/custom.css").toExternalForm());

        decorator.show();

    }
}
