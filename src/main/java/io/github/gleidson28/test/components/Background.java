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

package io.github.gleidson28.test.components;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

/**
 * @author   Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Creation  20/04/2018
 */
public class Background extends StackPane {

    private static final String USER_AGENT_STYLESHEET
            = Background.class.getResource("/css/decorator/decorator.css")
            .toExternalForm();

    public Background(Body body) {

        this.getStyleClass().add("gn-background");
        this.setId("gn-background");

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(body);

        this.setStyle("-fx-background-color : transparent;");
    }

    @Override
    public String getUserAgentStylesheet() {
        return USER_AGENT_STYLESHEET;
    }
}
