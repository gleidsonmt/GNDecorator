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

package com.gn.decorator.buttons;

import com.gn.decorator.background.UserDetail;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * @author   Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Creation  28/04/2018
 */
public class User extends HBox {

    private final StringProperty name = new SimpleStringProperty();
    private final Label view = new Label();
    private UserDetail userDetail;
    
    public User() {
        view.textProperty().bind(name);
    }
    
    public User(String name){
        super();
        this.view.setText(name);
        this.name.set(name);
        this.view.textProperty().bind(this.name);
        configLayout();
        userDetail = new UserDetail(this);
    }
    
    private void configLayout(){
        getStyleClass().add("gn-user");
        this.getChildren().add(view);
        this.setAlignment(Pos.CENTER);
        this.setCursor(Cursor.HAND);
        this.setOnMouseClicked(e -> {
            userDetail.show();
        });
    }

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
        getUserDetail().setHeader(value);
    }

    public StringProperty nameProperty() {
        return name;
    }
    
    public UserDetail getUserDetail(){
        return this.userDetail;
    }
    
    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("/css/controls/user.css").toExternalForm();
    }
}
