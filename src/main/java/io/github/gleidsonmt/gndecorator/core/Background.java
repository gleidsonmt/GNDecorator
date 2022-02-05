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

package io.github.gleidsonmt.gndecorator.core;

//import io.github.gleidson28.decorator.background.GNBackground;
import io.github.gleidsonmt.gndecorator.GNDecorator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

/**
 * @author   Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Creation  20/04/2018
 */
public class Background extends StackPane {

    private static final PseudoClass MAXIMIZE_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("maximized");

//    private static final String USER_AGENT_STYLESHEET =
//            GNBackground.class.getResource("/theme/agent.css").toExternalForm();

    private final BooleanProperty maximized = new BooleanPropertyBase(false) {
        public void invalidated() {
            pseudoClassStateChanged(MAXIMIZE_PSEUDO_CLASS, get());
        }

        @Override public Object getBean() {
            return Background.this;
        }

        @Override public String getName() {
            return "maximized";
        }
    };

    private static final String DEFAULT_STYLESHEET
            = Background.class.getResource("/io.github.gleidson.gndecorator/theme/default.css")
            .toExternalForm();

    private static final String LIGHT_STYLESHEET
            = Background.class.getResource("/io.github.gleidson.gndecorator/theme/light.css")
            .toExternalForm();

    public Background(Body body, GNDecorator decorator) {

        this.getStyleClass().add("gn-background");
        this.setId("gn-background");

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(body);



        this.getStylesheets().add(LIGHT_STYLESHEET);
        this.getStylesheets().add(DEFAULT_STYLESHEET);

        decorator.maximizedProperty().
                addListener((observable, oldValue, newValue) -> setMaximize(newValue));
    }

    public boolean isMaximized() {
        return maximized.get();
    }

    public BooleanProperty maximizeProperty() {
        return maximized;
    }

    public void setMaximize(boolean maximize) {
        this.maximized.set(maximize);
    }

//    @Override
//    public String getUserAgentStylesheet() {
//        return USER_AGENT_STYLESHEET;
//    }
}
