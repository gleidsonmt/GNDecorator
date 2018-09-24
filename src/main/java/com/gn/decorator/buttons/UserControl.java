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
package com.gn.decorator.buttons;

import com.jfoenix.controls.JFXPopup;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/09/2018
 * Version 1.0
 */
public abstract class UserControl {

    public abstract String confTitle();
    protected abstract String confSub();
    protected abstract Color confColor();
    protected abstract Image  confAvatar();

    private VBox root = new VBox();
    private VBox background = new VBox();
    private ImageView avatar = new ImageView();
    private Label header = new Label();
    private Label subTitle = new Label();
    private HBox content = new HBox();
    private GridPane layoutContent = new GridPane();

    private Button signOut = new Button("Sign Out");
    private Button profile = new Button("Profile");

    protected UserControl() {
        super();
        root.getStyleClass().add("gn-detail");
        this.background.getStyleClass().add("gn-background");
        this.signOut.getStyleClass().add("gn-action");
        this.profile.getStyleClass().add("gn-action");
        this.subTitle.getStyleClass().add("gn-title");
        this.header.getStyleClass().add("gn-header");

        this.profile.setMinHeight(40);
        this.signOut.setMinHeight(40);

        this.profile.setCursor(Cursor.HAND);
        this.signOut.setCursor(Cursor.HAND);

        configLayout();
    }

    private void configLayout(){

        this.header.setText(confTitle());
        this.subTitle.setText(confSub());
        this.avatar.setImage(confAvatar());

        this.background.setPrefHeight(500);

        this.root.setPrefWidth(387);
        this.root.setPrefHeight(300);

        avatar.setFitWidth(139);
        avatar.setFitHeight(136D);

        this.background.setBackground(new Background(new BackgroundFill(confColor(), CornerRadii.EMPTY, Insets.EMPTY)));
//        this.background.setStyle("-fx-background-color :  rgba(0,0,0,.5);");

        signOut.setPrefWidth(100);
        profile.setPrefWidth(100);

        this.layoutContent.add(signOut, 0, 0);
        this.layoutContent.add(profile, 1, 0);

        ColumnConstraints column1 = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        ColumnConstraints column2 = new ColumnConstraints(100, 100, Double.MAX_VALUE);

        RowConstraints row = new RowConstraints(100 ,100, Double.MAX_VALUE);

        column1.setHalignment(HPos.CENTER);
        column2.setHalignment(HPos.CENTER);
//
        column2.setHgrow(Priority.ALWAYS);
        column1.setHgrow(Priority.ALWAYS);
//
        row.setValignment(VPos.CENTER);


        layoutContent.getColumnConstraints().addAll(column1, column2);
        layoutContent.getRowConstraints().addAll(row);

        this.background.setAlignment(Pos.CENTER);
        this.background.getChildren().addAll(avatar, header, subTitle);

        content.setPrefHeight(287);
        content.setAlignment(Pos.CENTER);

        this.content.getChildren().add(layoutContent);

        VBox.setVgrow(this.content, Priority.ALWAYS);
        HBox.setHgrow(this.layoutContent, Priority.ALWAYS);

        this.root.getChildren().addAll(background, content);

        Circle circle = new Circle(60);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(5);
        circle.setCenterX(avatar.getFitWidth() / 2);
        circle.setCenterY(avatar.getFitHeight() / 2);
        avatar.setClip(circle);
    }


    public void setColor(String color){
        background.setStyle("-fx-background-color : " + color);
    }

    public void setProfileAction(EventHandler event){
        this.profile.setOnAction(event);
    }

    public void setSignOutAction(EventHandler event) {
    this.signOut.setOnAction(event);
}

    public void setHeader(String header){
    this.header.textProperty().set(header);
}

    public void show(Node node){
        root.getStylesheets().addAll(getClass().getResource("/css/controls/user.css").toExternalForm());
        JFXPopup popup = new JFXPopup();
        popup.setPopupContent(root);
        popup.setAutoHide(true);
        popup.show(node, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, 0, 25);
    }
}
