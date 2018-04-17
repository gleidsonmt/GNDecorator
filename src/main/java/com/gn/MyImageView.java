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

import com.jfoenix.controls.JFXDatePicker;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * @author   Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Creation  15/04/2018
 */
public class MyImageView extends Button {
    
    public MyImageView(){
        super.setText("Button");
        getStyleClass().add("control");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MyControlSkin(MyImageView.this);
    }
    
    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("/css/archive.css").toExternalForm();
    }
    /**
     * the default color used in the data picker content
     */
    private final StyleableObjectProperty<Paint> defaultColor = new SimpleStyleableObjectProperty<>(StyleableProperties.DEFAULT_COLOR,
            MyImageView.this,
            "defaultColor",
            Color.valueOf(
                    "#009688"));

    public Paint getDefaultColor() {
        return  Color.valueOf(
                "#009688");
    }

    public StyleableObjectProperty<Paint> defaultColorProperty() {
        return this.defaultColor;
    }

    public void setDefaultColor(Paint color) {
        this.defaultColor.set(color);
    }
    
    

    private static class StyleableProperties {

        private static final CssMetaData<MyImageView, Paint> DEFAULT_COLOR
                = new CssMetaData<MyImageView, Paint>("-jfx-default-color",
                        PaintConverter.getInstance(), Color.valueOf("#009688")) {
            @Override
            public boolean isSettable(MyImageView control) {
                return control.defaultColor == null || !control.defaultColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(MyImageView control) {
                return control.defaultColorProperty();
            }
        };


        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables
                    = new ArrayList<>(Button.getClassCssMetaData());
            Collections.addAll(styleables,
                    DEFAULT_COLOR);
            System.out.println(DEFAULT_COLOR);
            for(CssMetaData<? extends Styleable, ?> style : styleables){
                System.out.println(style);
            }
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
            styleables.addAll(MyImageView.getClassCssMetaData());
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
        return STYLEABLES;
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.CHILD_STYLEABLES;
    }
    
   
}

