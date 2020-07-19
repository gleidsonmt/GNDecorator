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
package io.github.gleidson28.test.components;

import javafx.beans.NamedArg;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.scenicview.ScenicView;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/07/2020
 */
public class GNDecoratorT {

    private final DoubleProperty barHeight
            = new SimpleDoubleProperty(GNDecoratorT.class,
            "BarHeightProperty",30);

    private final BooleanProperty maximized
            = new SimpleBooleanProperty(GNDecoratorT.class,
            "maximizedProperty", false);

    private final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

    private double initialWidth  = 800;
    private double initialHeight = 600;


    private final Stage stage = new Stage(StageStyle.UNDECORATED);

    private final LeftBar   leftBar     = new LeftBar(this);
    private final RightBar  rightBar    = new RightBar(stage);
    private final TopBar    topBar      = new TopBar(stage);
    private final BottomBar bottomBar   = new BottomBar(stage);

    private final TopLeftAnchor     topLeftAnchor    = new TopLeftAnchor(stage);
    private final TopRightAnchor    topRightAnchor   = new TopRightAnchor(stage);
    private final BottomLeftAnchor  bottomLeftAnchor = new BottomLeftAnchor(stage);
    private final BottomRightAnchor bottomRightAnchor = new BottomRightAnchor(stage);

    private final Bar bar       = new Bar(this);

    private final AreaContent   areaContent   = new AreaContent();
    private final Container     container     = new Container(areaContent);

    private final Body body = new Body(container, bar, topBar,
            rightBar, bottomBar, leftBar, topLeftAnchor, topRightAnchor,
            bottomLeftAnchor, bottomRightAnchor);

    private final Background background = new Background(body, this);

    private final Scene scene = new Scene(background);
    private final TranslucentStage translucentStage = new TranslucentStage(this);

    private BoundingBox noMaximizedBounds = null;

    public GNDecoratorT() {
        configStage();
    }

    private void configStage() {
        this.scene.setFill(Color.WHITE);
        this.stage.setScene(this.scene);
        this.stage.setMinWidth(254.0D);
        this.stage.setMinHeight(57.0D);
    }
 
    public void setContent(Pane content){

        System.out.println("width = " + initialWidth);

        if(content.getPrefWidth() != -1 && content.getPrefHeight() != -1){
            setContent(content, content.getPrefWidth(), content.getPrefHeight());
            initialWidth = content.getPrefWidth();
            initialHeight = content.getPrefHeight();
        } else if(content.getPrefWidth() != -1){
            setContent(content, content.getPrefWidth(), initialHeight);
            initialHeight = content.getPrefHeight();
        } else if(content.getPrefHeight() != -1){
            setContent(content, initialWidth, content.getPrefHeight());
            initialWidth = content.getPrefHeight();
        } else {
            setContent(content, initialWidth, initialHeight);
        }
    }

    public void setContent(Pane content, double width, double height){
        this.areaContent.setContent(content);

        initialWidth = width;
        initialHeight = height;

        double x = width < bounds.getWidth() ?
                (bounds.getWidth() / 2 ) - (width / 2) : width;
        double y = height < bounds.getHeight() ?
                (bounds.getHeight() / 2) - (height / 2) : height;
        double _width = width < bounds.getWidth() ? width : initialWidth;
        double _height = height < bounds.getHeight() ? height : initialHeight;

        noMaximizedBounds = new BoundingBox( x, y, _width, _height);
    }


    public void show(){
        if (maximized.get()) {
            this.stage.setWidth(bounds.getWidth());
            this.stage.setHeight(bounds.getHeight());
        } else {
            this.stage.setWidth(initialWidth);
            this.stage.setHeight(initialHeight);
        }
        this.stage.show();
    }

    public boolean isMaximized() {
        return maximized.get();
    }

    public BooleanProperty maximizedProperty() {
        return maximized;
    }

    public void setMaximized(boolean maximized) {
        this.maximized.set(maximized);
    }
    
    public void addMenu(Menu menu){
        this.bar.getMenuBar().getMenus().add(menu);
    }

    /**
     *
     * Tests
     *
     * */
    BoundingBox getNoMaximizedBounds(){
        return this.noMaximizedBounds;
    }

    void setBounds(BoundingBox save){
        this.noMaximizedBounds = save;
    }

    public Stage getStage(){
        return this.stage;
    }

    public Scene getScene(){
        return this.scene;
    }

    TranslucentStage getTranslucentStage(){
        return translucentStage;
    }

    DoubleProperty barHeightProperty(){
        return this.barHeight;
    }

    public void testWithScenicView(){
        ScenicView.show(this.scene);
    }

    public void initTheme(Theme theme){
        switch (theme) {
        }
    }
}
