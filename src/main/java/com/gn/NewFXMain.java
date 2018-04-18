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

import com.gn.buttons.Close;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 */
public class NewFXMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        
        Close control = new Close();
        
        
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        VBox root = new VBox();
        root.getStyleClass().add("tedo");
//        root.setAlignment(Pos.BASELINE_LEFT);
        root.getChildren().addAll(control, btn);

        Scene scene = new Scene(root, 300, 250);
        
        scene.getStylesheets().add(getClass().getResource("/css/controls/teste.css").toExternalForm());

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
