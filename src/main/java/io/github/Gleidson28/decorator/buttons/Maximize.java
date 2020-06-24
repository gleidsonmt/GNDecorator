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

package io.github.Gleidson28.decorator.buttons;

import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.css.*;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author   Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Creation  15/04/2018
 */
public class Maximize extends Button {
    
    
    private final ImageView viewMaximize = new ImageView(new Image("/img/maximize.png"));
    private final ImageView viewRestore = new ImageView(new Image("/img/restore.png"));
    
    public Maximize(){
        getStyleClass().add("gn-maximize");
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setGraphic(viewMaximize);
    }
    
    public void updateState(boolean maximize){
        if(maximize)this.setGraphic(viewMaximize);
        else this.setGraphic(viewRestore);
    }
    
    @Override
    protected Skin<?> createDefaultSkin() {
        return new ButtonSkin(Maximize.this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("/css/controls/buttons.css").toExternalForm();
    }

    private final StyleableObjectProperty<Paint> defaultFill = new SimpleStyleableObjectProperty<>(StyleableProperties.DEFAULT_FILL,
            Maximize.this,
            "defaultFill",
            Color.WHITE);

    public Paint getdefaultFill() {
        return defaultFill.get();
    }

    public StyleableObjectProperty<Paint> defaultFillProperty() {
        return this.defaultFill;
    }

    public void setdefaultFill(Paint color) {
        this.defaultFill.set(color);
    }

    private static class StyleableProperties {

        private static final CssMetaData<Maximize, Paint> DEFAULT_FILL
                = new CssMetaData<Maximize, Paint>("-gn-fill",
                        PaintConverter.getInstance(), Color.RED) {
            @Override
            public boolean isSettable(Maximize control) {
                return control.defaultFill == null || !control.defaultFill.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(Maximize control) {
                return control.defaultFillProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables
                    = new ArrayList<>(Button.getClassCssMetaData());
            Collections.addAll(styleables,
                    DEFAULT_FILL);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    // inherit the styleable properties from parent
    private List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        if (STYLEABLES == null) {
            final List<CssMetaData<? extends Styleable, ?>> styleables
                    = new ArrayList<>(Button.getClassCssMetaData());
            styleables.addAll(getClassCssMetaData());
            styleables.addAll(Maximize.getClassCssMetaData());
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
        return STYLEABLES;
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.CHILD_STYLEABLES;
    }

}
