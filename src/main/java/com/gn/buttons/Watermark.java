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

package com.gn.buttons;

import com.sun.javafx.css.converters.PaintConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * @author   Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Creation  16/04/2018
 */
public class Watermark extends StackPane {

    public Watermark() {
        this.setBackground(new Background(new BackgroundFill(getdefaultFill(), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public Paint getdefaultFill() {
        return defaultFill.get();
    }

    public StyleableProperty<Paint> defaultFillProperty() {
        return this.defaultFill;
    }

    public void setdefaultFill(Paint color) {
        this.defaultFill.set(color);
    }
    
    private final StyleableObjectProperty<Paint> defaultFill 
            = new SimpleStyleableObjectProperty(StyleableProperties.DEFAULT_FILL, Watermark.this, "defaultFill", Color.GREEN);
    
    private static class StyleableProperties {
        private static final CssMetaData<Watermark, Paint> DEFAULT_FILL
            = new CssMetaData<Watermark, Paint>("-gn-fill",
                    PaintConverter.getInstance(), Color.RED) {
        @Override
        public boolean isSettable(Watermark control) {
            return control.defaultFill == null || !control.defaultFill.isBound();
        }

        @Override
        public StyleableProperty<Paint> getStyleableProperty(Watermark control) {
            return control.defaultFillProperty();
        }
    };

    
        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables
                    = new ArrayList<>(StackPane.getClassCssMetaData());
            Collections.addAll(styleables,
                    DEFAULT_FILL);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }   
}
