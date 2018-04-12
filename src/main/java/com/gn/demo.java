/*
 * Copyright (C) 2018 Gleidson Neves da Silveira
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

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.scenicview.ScenicView;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 */
public class demo extends Application {

    
    
    @Override
    public void start(Stage stage) throws Exception {
        GNWindowBar window = new GNWindowBar();
        window.viewBars(true);
        window.show();
//        ScenicView.show(window.getScene());
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
