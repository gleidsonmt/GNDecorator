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
package io.github.gleidsonmt.gndecorator.core;

import javafx.animation.TranslateTransition;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/07/2020
 */
@SuppressWarnings("unused")
public class GNDecorator {

    private final DoubleProperty barHeight
            = new SimpleDoubleProperty(GNDecorator.class,
            "BarHeightProperty",30);

    private final BooleanProperty maximized
            = new SimpleBooleanProperty(GNDecorator.class,
            "maximizedProperty", false);

    private final BooleanProperty resizable
            = new SimpleBooleanProperty(GNDecorator.class,
            "resizableProperty", true);

    private final StringProperty title
            = new SimpleStringProperty(GNDecorator.this,
            "titleProperty", "GNDecorator");

    private final BooleanProperty fullBody
            = new SimpleBooleanProperty(GNDecorator.this,
            "fullBodyProperty", false);

    private final TranslateTransition open
            = new TranslateTransition(Duration.millis(100D), this.bar);

    private final TranslateTransition close
            = new TranslateTransition(Duration.millis(100D), this.bar);

    private final Rectangle2D bounds
            = Screen.getPrimary().getVisualBounds();

    private final Stage stage = new Stage(StageStyle.TRANSPARENT);

    /// Fixing
    private final StageState stageState = new StageState(this, stage);
    private final LeftBar   leftBar     = new LeftBar(stageState);
    private final RightBar  rightBar    = new RightBar(stageState);
    private final TopBar    topBar      = new TopBar(stageState);
    private final BottomBar bottomBar   = new BottomBar(stageState);
    private final TopLeftAnchor     topLeftAnchor       = new TopLeftAnchor(stageState);
    private final TopRightAnchor    topRightAnchor      = new TopRightAnchor(stageState);
    private final BottomLeftAnchor bottomLeftAnchor    = new BottomLeftAnchor(stageState);
    private final BottomRightAnchor bottomRightAnchor   = new BottomRightAnchor(stageState);

    private final Bar bar       = new Bar(this, stageState);

    private final AreaContent   areaContent   = new AreaContent();
    private final Container container     = new Container(areaContent);

    private final Body body
            = new Body(stage, this, container, bar, topBar,
            rightBar, bottomBar, leftBar, topLeftAnchor, topRightAnchor,
            bottomLeftAnchor, bottomRightAnchor);

    private final Background background
            = new Background(body);

    private final Scene scene
            = new Scene(background);

    private double  initialWidth    = 800;
    private double  initialHeight   = 600;
    private boolean dark            = false;

//    private BoundingBox noMaximizedBounds;

    public GNDecorator() {
        configStage();
//        viewBar(true);
    }

    private void configStage() {
        this.scene.setFill(Color.TRANSPARENT);
        this.stage.setScene(this.scene);
        this.stage.setMinWidth(254.0D);
        this.stage.setMinHeight(57.0D);

        this.stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));

        stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> viewBar(newValue));

        fullBody.addListener((observable, oldValue, newValue) -> {
            if(newValue) body.alignTopAnchor(container);
            else body.alignContent(container, bar.getHeight());
        });
    }

    public void setContent(Parent content){
       Region _content = (Region) content;
       content.requestFocus();
       setContent(_content);
    }

    public void setContent(Region _content){
        
        _content.setMinSize(0,0);
        
        if(_content.getPrefWidth() != -1 && _content.getPrefHeight() != -1){
            setContent(_content, _content.getPrefWidth(), _content.getPrefHeight());
            initialWidth = _content.getPrefWidth();
            initialHeight = _content.getPrefHeight();
        } else if(_content.getPrefWidth() != -1){
            setContent(_content, _content.getPrefWidth(), initialHeight);
            initialHeight = _content.getPrefHeight();
        } else if(_content.getPrefHeight() != -1){
            setContent(_content, initialWidth, _content.getPrefHeight());
            initialWidth = _content.getPrefHeight();
        } else {
            setContent(_content, initialWidth, initialHeight);
        }
    }

    public void setContent(Pane content){

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

    public void setContent(Region content, double width, double height){
        this.areaContent.setContent(content);

        initialWidth = width;
        initialHeight = height;

        double x = width < bounds.getWidth() ?
                (bounds.getWidth() / 2 ) - (width / 2) : width;
        double y = height < bounds.getHeight() ?
                (bounds.getHeight() / 2) - (height / 2) : height;
        double _width = width < bounds.getWidth() ? width : initialWidth;
        double _height = height < bounds.getHeight() ? height : initialHeight;

        stageState.updateNoMaximizedBounds(new BoundingBox(x, y, _width, _height));
    }

    public Node getContent(){
        return areaContent.getContent();
    }

    public void show() {

        if(maximized.get()){
            this.stage.setWidth(bounds.getWidth());
            this.stage.setHeight(bounds.getHeight());
        } else if(Double.isNaN(this.stage.getWidth())
                && Double.isNaN(this.stage.getHeight())){
            this.stage.setWidth(initialWidth);
            this.stage.setHeight(initialHeight);
        } else if(Double.isNaN(this.stage.getWidth())){
            this.stage.setWidth(initialWidth);
        } else if(Double.isNaN(this.stage.getHeight())){
            this.stage.setHeight(initialHeight);
        }

        this.stage.centerOnScreen();
        this.stage.show();

//        background.setStyle("-fx-background-radius : 10px;");

//        background.setClip(new Circle(stage.getWidth() /2));
    }

    /**
     * If stage is maximized.
     * @return The one state of stage.
     */
    public boolean isMaximized() {
        return maximized.get();
    }

    public boolean isResizable(){
        return resizable.get();
    }

    public BooleanProperty maximizedProperty() {
        return maximized;
    }

    public BooleanProperty resizableProperty(){
        return resizable;
    }

    public void setMaximized(boolean maximized) {
        this.maximized.set(maximized);
    }

    public void setResizable(boolean resizable){
        this.resizable.set(resizable);
    }

    public void setIconified(boolean value) {
        stage.setIconified(value);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public boolean isFullBody() {
        return fullBody.get();
    }

    public BooleanProperty fullBodyProperty() {
        return fullBody;
    }

    public void setFullBody(boolean fullBody) {
        this.fullBody.set(fullBody);
    }

    public void fullBody(){
        this.fullBody.set(true);
    }

    public ReadOnlyDoubleProperty widthProperty() {
        return this.stage.widthProperty();
    }

    public void setMinSize(double width, double height) {
        this.stage.setMinWidth(width);
        this.stage.setMinHeight(height);
    }

    public void setHeight(double height) {
        this.stage.setHeight(height);
    }

    public void setMinHeight(double height) {
        this.stage.setMinHeight(height);
    }

    public void setWidth(double width) {
        this.stage.setWidth(width);
    }

    public void setMinWidth(double width) {
        this.stage.setMinWidth(width);
    }

    public void setSize(double width, double height) {
        this.stage.setWidth(width);
        this.stage.setHeight(height);
    }
    
    public double getWidth() {
        return this.stage.getWidth();
    }

    public ReadOnlyDoubleProperty heightProperty() {
        return this.stage.heightProperty();
    }

    public double getHeight() {
        return this.stage.getHeight();
    }

    public double getX() {
        return this.stage.getX();
    }

    public double getY() {
        return this.stage.getY();
    }

    public Window getWindow(){
        return this.stage.getScene().getWindow();
    }

    /**
     * Add a menu for menu bar.
     * @param menu for bar.
     */
    public void addMenu(Menu menu){
        this.bar.getMenuBar().getMenus().add(menu);
    }

    public void addMenu(int index, Menu menu){
        this.bar.getMenuBar().getMenus().add(index, menu);
    }
    
    public ObservableList<Menu> getMenus(){
        return this.bar.getMenuBar().getMenus();
    }

    public void addControl(Control control){
        this.bar.getCustomControls().add(control);
    }

    public void addControl(int index, Control control){
        this.bar.getCustomControls().add(index, control);
    }

    public void addControls(Control... control){
        this.bar.getCustomControls().addAll(control);
    }

    public void addControls(int index, Control... controls){
        this.bar.getCustomControls().addAll(index, Arrays.asList(controls));
    }
    
    public ObservableList<Node> getCustomControls(){
        return this.bar.getCustomControls();
    }
    
    public void removeControl(Control control){
        this.bar.getCustomControls().remove(control);
    }

    public void removeControl(int index){
        this.bar.getCustomControls().remove(index);
    }

    public void removeControls(Control... controls){
        this.bar.getCustomControls().removeAll(Arrays.asList(controls));
    }

    public void lockControls(){
        //this.bar.getCustomBar().unblock();
        this.bar.setMouseTransparent(true);

        this.body.getChildren().stream().filter( p -> p instanceof StageChanges).forEach(c -> c.setMouseTransparent(true));
    }

    public void unLockControls(){
        //this.bar.getCustomBar().unblock();
        this.bar.setMouseTransparent(true);

        this.body.getChildren().stream().filter( p -> p instanceof StageChanges).forEach(c -> c.setMouseTransparent(false));
    }
    
    public boolean isLocked(){
        return  this.bar.isMouseTransparent();
    }
    
    /**
     * Get icons from stage.
     * @return The icons from this decorator.
     */
    public final ObservableList<Image> getIcons() {
        return stage.getIcons();
    }

    public void switchTheme(DecoratorTheme decoratorTheme){;
        switch (decoratorTheme) {
            case MAC_YOSEMITE -> {
                background.getStylesheets().remove(background.getStylesheets().size() - 1);
                background.getStylesheets().add(
                        Objects.requireNonNull(GNDecorator.class.
                                getResource("/theme/yosemite.css")).toExternalForm()
                );
                bar.addAutoHover();
                bar.invertControls(true);
            }
            case DEFAULT -> {
                background.getStylesheets().remove(background.getStylesheets().size() - 1);

                background.getStylesheets().add(
                        Objects.requireNonNull(GNDecorator.class.
                                getResource("/theme/default.css")).toExternalForm()
                );

                bar.invertControls(false);
                bar.removeAutoHover();
            }
        }
    }


    public void setFullScreen(boolean value){
        stage.setFullScreen(true);
//        setMaximized(true);

//        this.background.setOnMouseMoved(handler);
//
        bar.setVisible(!value);

        body.fullBody(container);

    }

    @Deprecated
    private void viewBar(boolean view){

        open.setFromY(-(barHeight.get()));
        open.setByY(barHeight.get());

        close.setFromY(barHeight.get() * -1);

        close.setByY( (barHeight.get() ));

        if(!view) {
            open.play();
            if(isFullBody()){
                AnchorPane.setTopAnchor(this.container, 0D);
            } else {
                AnchorPane.setTopAnchor(this.container, barHeight.get());
            }
            bar.setVisible(true);
        } else {
            AnchorPane.setTopAnchor(this.container, 0D);
            close.play();
            bar.setVisible(false);
        }

    }

    @Deprecated
    public void setDark(boolean value){
        dark = value;
        if(value) {
            background.getStylesheets().remove(
                    Objects.requireNonNull(GNDecorator.class.
                            getResource("/theme/light.css")).toExternalForm()
            );
            background.getStylesheets().add(0, Objects.requireNonNull(GNDecorator.class.
                    getResource("/theme/dark.css")).toExternalForm());

        } else {

                background.getStylesheets().remove(
                        Objects.requireNonNull(GNDecorator.class.
                                getResource("/theme/dark.css")).toExternalForm()
                );
                background.getStylesheets().add(0, Objects.requireNonNull(GNDecorator.class.
                        getResource("/theme/light.css")).toExternalForm());
        }
    }

    @Deprecated
    public boolean isDark() {
        return dark;
    }

    public void addStylesheets(String... stylesheet) {
        stage.getScene().getStylesheets().addAll(stylesheet);
    }

    public ObservableList<String> getStylesheets(){
        return this.scene.getStylesheets();
    }

    public Node lookup(String value){
        return this.scene.lookup(value);
    }


    public double getBarHeight() {
        return barHeight.get();
    }

    public DoubleProperty barHeightProperty() {
        return barHeight;
    }

    public void setBarHeight(double barHeight) {
        this.barHeight.set(barHeight);
    }
}
