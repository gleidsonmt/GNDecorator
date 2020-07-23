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
package io.github.gleidson28.decorator;

import io.github.gleidson28.decorator.background.GNBackground;
import io.github.gleidson28.decorator.buttons.Close;
import io.github.gleidson28.decorator.buttons.GNFullscreen;
import io.github.gleidson28.decorator.buttons.Maximize;
import io.github.gleidson28.decorator.buttons.Minimize;
import io.github.gleidson28.decorator.options.ButtonType;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.stream.Collectors;

/**
 * Create a beautiful decoration for nodes.
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created on 12/04/2018
 */
@SuppressWarnings("unused")
public class GNDecorator {

    private final GNBackground background;
    
    private Stage stage = null;
    private Scene scene = null;

    private final AnchorPane body        = new AnchorPane();
    private final ScrollPane container   = new ScrollPane();
    private final StackPane  content     = new StackPane();
    private final StackPane  areaContent = new StackPane(this.container);

    private final Path top_left     = new Path();
    private final Path top_right    = new Path();
    private final Path bottom_left  = new Path();
    private final Path bottom_right = new Path();

    private final AnchorPane left   = new AnchorPane();
    private final AnchorPane right  = new AnchorPane();
    private final AnchorPane top    = new AnchorPane();
    private final AnchorPane bottom = new AnchorPane();

    private final AnchorPane    bar              = new AnchorPane();
    private final HBox          bar_content      = new HBox();
    private final HBox          controls         = new HBox();
    private final MenuBar       menus            = new MenuBar();
    private final HBox          title_content    = new HBox();
    
    private final Close        btn_close      = new Close();
    private final Maximize     btn_maximize   = new Maximize();
    private final Minimize     btn_minimize   = new Minimize();
    private final GNFullscreen btn_fullScreen = new GNFullscreen();
    private final Label        title          = new Label("Application");
    private final SVGPath      icon           = new SVGPath();
    
    private final ImageView viewMinimize    = new ImageView(new Image("/img/minimize.png"));
    private final ImageView viewMaximize    = new ImageView(new Image("/img/maximize.png"));
    private final ImageView viewClose       = new ImageView(new Image("/img/close.png"));

    private static double initX = -1;
    private static double initY = -1;

    private static double newX;
    private static double newY;
    
    private Rectangle2D bounds       = null;
    private BoundingBox savedBounds  = null;
    private BoundingBox initialBound = null;

    private final BooleanProperty resizableProperty = new SimpleBooleanProperty(GNDecorator.this, "resizableProperty", true);
    private final StringProperty  titleProperty     = new SimpleStringProperty(GNDecorator.this, "textProperty", "title");
    private final BooleanProperty maximizedProperty = new SimpleBooleanProperty(GNDecorator.this, "maximizedProperty", false);
   
    private DoubleProperty barHeight = new SimpleDoubleProperty(GNDecorator.this, "barSize", 30);
    private DoubleProperty buttonHeight = new SimpleDoubleProperty(GNDecorator.this, "buttonHeight", 30);
    private DoubleProperty buttonWidth = new SimpleDoubleProperty(GNDecorator.this, "buttonWidth", 30);
    
    
    private final TranslateTransition open = new TranslateTransition(Duration.millis(100D), this.bar);
    private final TranslateTransition close = new TranslateTransition(Duration.millis(100D), this.bar);

    private EventHandler<MouseEvent> mouseDraggedB;
    private EventHandler<MouseEvent> mousePressedB;

    public enum Style { DEFAULT, DARK }
    public enum Theme { DEFAULT, DARK, DANGER, INFO, PRIMARY, SECONDARY, WARNING, SUCCESS, CUSTOM }
    
    private final ChangeListener<Object> restoreFullScreen = (observable, oldValue, newValue) -> {
        if (newValue != null) {
            configCursor(true);
            AnchorPane.setTopAnchor(GNDecorator.this.areaContent, barHeight.get());
            btn_fullScreen.updateState(true);
            if (!GNDecorator.this.bar.isVisible()) {
                GNDecorator.this.bar.setVisible(true);
                viewBorders(true);
            }
            configCursor(isResizable() && !isMaximized());
        }
    };
    
    public GNDecorator() {
        super();
        background =  new GNBackground();
        configLayout();
        addActions();
        configStage();

        bounds = Screen.getPrimary().getVisualBounds();
        title.textProperty().bind(titleProperty);
        controls.minHeightProperty().bind(barHeight);
        menus.minHeightProperty().bind(barHeight);
        title_content.minHeightProperty().bind(barHeight);
        this.bar.minHeightProperty().bind(barHeight);
        
        controls.getChildren().stream().map((node) -> {
            ((Button) node).minHeightProperty().bind(buttonHeight);
            return node;
        }).map((node) -> {
            ((Button) node).prefHeightProperty().bind(buttonHeight);
            return node;
        }).map((node) -> {
            ((Button) node).minWidthProperty().bind(buttonWidth);
            return node;
        }).forEachOrdered((node) -> {
            ((Button) node).prefWidthProperty().bind(buttonWidth);
        });

        areaContent.setId("area-content");

        btn_fullScreen.minHeightProperty().bind(buttonHeight);
        btn_fullScreen.prefHeightProperty().bind(buttonHeight);
        btn_fullScreen.minWidthProperty().bind(buttonWidth);
        btn_fullScreen.prefWidthProperty().bind(buttonWidth);
    }

    public Stage getStage() {
        return this.stage;
    }
    
    public Scene getScene(){
        return this.scene;
    }

    public final StringProperty titleProperty() {
        return titleProperty;
    }

    public final String getTitle() {
        return titleProperty.get();
    }

    public final void setTitle(String text) {
        titleProperty.set(text);
    }
    
    public void setMaximized(boolean maximized) {
        maximizedProperty.set(maximized);
        Platform.runLater(() -> {
            if (maximized) {
                maximize(); // configura os icones dos buttons
            }
        });
    }
    
    public BooleanProperty maximizedProperty() {
        return maximizedProperty;
    }

    public GNBackground getBackground() {
        return this.background;
    }

    public boolean isMaximized() {
        return stage.getWidth() == Screen.getPrimary().getVisualBounds().getWidth()
                && stage.getHeight() == Screen.getPrimary().getVisualBounds().getHeight()
                && stage.getX() == Screen.getPrimary().getVisualBounds().getMinX()
                && stage.getY() == Screen.getPrimary().getVisualBounds().getMinY();
    }

    public void setResizable(boolean resizable){
        this.resizableProperty.set(resizable);

        Platform.runLater(() -> {
            configCursor(resizable);

            if(!resizable) {
                btn_maximize.setDisable(true);
                bar.setOnMouseClicked(null);

                bar.setOnMouseDragged(null);
                bar.setOnMousePressed(null);
            } else { // Solving bug
                btn_maximize.setDisable(false);
                bar.setOnMouseDragged(mouseDraggedB);
                bar.setOnMousePressed(mousePressedB);
            }
//            if (isMaximized()) {
//                btn_maximize.setId("restore");
//            } else {
//                btn_maximize.setId("maximize");
//            }
        });
    }

    public boolean isResizable(){
        return this.resizableProperty.get();
    }

    public BooleanProperty resizableProperty(){
        return this.resizableProperty;
    }
    
    public void setContent(Node body) {
        if(!this.content.getChildren().isEmpty())
            this.content.getChildren().clear();

        if(body instanceof Pane){
            ((Pane) body).setMinSize(0,0);
        }

        this.content.getChildren().add(body);
    }
    
    public StackPane getContent(){
        return this.content;
    }
    
    public void setColor(Color color){
        this.body.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public boolean isFullScreen(){
        return stage.isFullScreen();
    }
    
    public void setFullScreen(boolean full){
        if(full){
            stage.setFullScreen(true);
            AnchorPane.setTopAnchor(this.areaContent, 0D);
            stage.fullScreenProperty().addListener(restoreFullScreen);
            viewBorders(false);
            configCursor(false);
            if(bar.isVisible()) bar.setVisible(false);
        }
    }
    
    public DoubleProperty barHeightProperty(){
        return this.barHeight;
    }
    
    public void setBarHeight(double height){
        this.barHeight.set(height);
        AnchorPane.setTopAnchor(this.areaContent, height);
    }
    
    public double getBarHeight(){
        return this.barHeight.get();
    }
    
    public DoubleProperty buttonHeightProperty() {
        return this.buttonHeight;
    }

    public void setButtonHeight(double height) {
        this.buttonHeight.set(height);
    }

    public double getButtonHeight() {
        return this.buttonHeight.get();
    }
    
    public DoubleProperty buttonWidthProperty() {
        return this.buttonWidth;
    }

    public void setButtonWidth(double width) {
        this.buttonWidth.set(width);
    }

    public double getButtonWidth() {
        return this.buttonWidth.get();
    }
    
    public void setButtonSize(double width, double height){
        this.buttonWidth.set(width);
        this.buttonHeight.set(height);
    }

    private void configStage() {
        this.stage = new Stage(StageStyle.UNDECORATED);
        this.scene = new Scene(background);
        this.scene.setFill(Color.RED);
        
        
        this.stage.setScene(this.scene);
        this.stage.setMinWidth(254.0D);
        this.stage.setMinHeight(57.0D);
        stage.widthProperty().divide(scene.heightProperty());
        stage.heightProperty().divide(scene.widthProperty());
    }

    private void configLayout() {
        this.background.getStyleClass().add("gn-decorator");
        this.body.getStyleClass().add("gn-body");
        this.title.getStyleClass().add("gn-title");
        this.container.getStyleClass().add("gn-container");

        // add body in window
        this.background.getChildren().add(this.body);
        
        // add bar
        this.body.getChildren().add(bar());
        this.bar.getChildren().add(barContent());
        
        // add components in the bar
        this.bar_content.getChildren().add(menu());
        this.bar_content.getChildren().add(controls());
        this.bar_content.getChildren().add(titleContent());
        controls.toFront();
//        this.setStyle("-fx-border-color : #808080; -fx-border-width : 1");
        
        container.setFitToHeight(true);
        container.setFitToWidth(true);
        
        container.setStyle("-fx-background-color : transparent");
        this.container.setContent(content);
        this.body.getChildren().add(createRegion());
        
        // Config Axis in body
        this.body.getChildren().add(axisTopLeft());
        this.body.getChildren().add(axisTopRight());
        this.body.getChildren().add(axisBottomLeft());
        this.body.getChildren().add(axisBottomRight());

        // Config bars in body
        this.body.getChildren().add(left());
        this.body.getChildren().add(right());
        this.body.getChildren().add(top());
        this.body.getChildren().add(bottom());

        initTheme(Theme.DEFAULT);
        viewBars(false);
    }

    private Region createRegion() {
        
        AnchorPane.setTopAnchor(areaContent, barHeight.get());
        AnchorPane.setRightAnchor(areaContent, 0D);
        AnchorPane.setBottomAnchor(areaContent, 0D);
        AnchorPane.setLeftAnchor(areaContent, 0D);

        return areaContent;
    }


    private Path axisTopLeft() {
        top_left.setFill(Color.BLACK);
        top_left.setCursor(Cursor.NW_RESIZE);
        top_left.setId("top_left");
        pathLines(top_left);
        AnchorPane.setTopAnchor(top_left, 0D);
        AnchorPane.setLeftAnchor(top_left, 0D);
        return top_left;
    }

    private Path axisTopRight() {
        top_right.setCursor(Cursor.NE_RESIZE);
        top_right.setId("top_right");
        pathLines(top_right);
        top_right.setRotate(90D);
        AnchorPane.setTopAnchor(top_right, 0D);
        AnchorPane.setRightAnchor(top_right, 0D);
        return top_right;
    }

    private Path axisBottomLeft() {
        bottom_left.setCursor(Cursor.SW_RESIZE);
        bottom_left.setId("bottom_left");
        pathLines(bottom_left);
        bottom_left.setRotate(270D);
        AnchorPane.setLeftAnchor(bottom_left, 0D);
        AnchorPane.setBottomAnchor(bottom_left, 0D);
        return bottom_left;
    }

    private Path axisBottomRight() {
        bottom_right.setCursor(Cursor.SE_RESIZE);
        bottom_right.setId("bottom_right");
        pathLines(bottom_right);
        bottom_right.setRotate(180D);
        AnchorPane.setRightAnchor(bottom_right, 0D);
        AnchorPane.setBottomAnchor(bottom_right, 0D);
        return bottom_right;
    }

    private void pathLines(Path path) {
        path.setFill(Color.BLACK);
        path.setStroke(Color.BLACK);
        path.setStrokeType(StrokeType.INSIDE);
        MoveTo moveTo = new MoveTo(100D, -40D);
        LineTo line1 = new LineTo(120D, -40D);
        LineTo line2 = new LineTo(120D, -37D);
        LineTo line3 = new LineTo(103D, -37D);
        LineTo line4 = new LineTo(103D, -20D);
        LineTo line5 = new LineTo(100D, -20D);
        ClosePath closePath = new ClosePath();
        path.getElements().addAll(moveTo, line1, line2, line3, line4, line5, closePath);
    }

    private AnchorPane left() {
        left.setId("left");
        left.setCursor(Cursor.W_RESIZE);
        left.setMinWidth(3D);
        AnchorPane.setTopAnchor(left, 22D);
        AnchorPane.setLeftAnchor(left, 0D);
        AnchorPane.setBottomAnchor(left, 22D);
        bars(left);
        return left;
    }

    private AnchorPane right() {
        right.setId("right");
        right.setCursor(Cursor.E_RESIZE);
        right.setMinWidth(3D);
        AnchorPane.setTopAnchor(right, 22D);
        AnchorPane.setRightAnchor(right, 0D);
        AnchorPane.setBottomAnchor(right, 22D);
        bars(right);
        return right;
    }

    private AnchorPane top() {
        top.setId("top");
        top.setCursor(Cursor.N_RESIZE);
        top.setMinHeight(3D);
        AnchorPane.setTopAnchor(top, 0D);
        AnchorPane.setRightAnchor(top, 22D);
        AnchorPane.setLeftAnchor(top, 22D);
        bars(top);
        return top;
    }

    private AnchorPane bottom() {
        bottom.setId("bottom");
        bottom.setCursor(Cursor.S_RESIZE);
        bottom.setMinHeight(3D);
        AnchorPane.setBottomAnchor(bottom, 0D);
        AnchorPane.setRightAnchor(bottom, 22D);
        AnchorPane.setLeftAnchor(bottom, 22D);
        bars(bottom);
        return bottom;
    }
    
    private void bars(AnchorPane bar) {
        bar.setStyle("-fx-background-color : red");
    }
    
    private AnchorPane bar(){
        bar.getStyleClass().add("gn-bar");
        bar.setMinHeight(barHeight.get());
        AnchorPane.setTopAnchor(bar, 0D);
        AnchorPane.setRightAnchor(bar, 0D);
        AnchorPane.setLeftAnchor(bar, 0D);
        return bar;
    }
    
    private HBox barContent() {
        bar_content.setId("barContent");
        bar_content.setPrefHeight(barHeight.get());
        bar_content.setMinHeight(barHeight.get());
        bar_content.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(bar_content, 0D);
        AnchorPane.setRightAnchor(bar_content, 0D);
        AnchorPane.setLeftAnchor(bar_content, 0D);
        return bar_content;
    }

    private HBox controls(){
        controls.getStyleClass().add("gn-buttons");
        controls.setAlignment(Pos.CENTER);
        double prefWidth = buttonWidth.get(), prefHeight = buttonHeight.get();

        btn_minimize.setGraphic(viewMinimize);
        btn_maximize.setGraphic(viewMaximize);
        btn_close.setGraphic(viewClose);
        
        btn_minimize.setMinSize(prefWidth, prefHeight);
        btn_maximize.setMinSize(prefWidth, prefHeight);
        btn_close.setMinSize(prefWidth, prefHeight);

        btn_minimize.setPrefSize(prefWidth, prefHeight);
        btn_maximize.setPrefSize(prefWidth, prefHeight);
        btn_close.setPrefSize(prefWidth, prefHeight);
        
        controls.getChildren().addAll(btn_minimize, btn_maximize, btn_close);
        controls.setMinWidth(buttonWidth.get() * controls.getChildren().size());
        return controls;
    }

    private void updateMinWidth(){
        controls.setMinWidth(buttonWidth.get() * controls.getChildren().size());
    }
    
    private MenuBar menu(){
        menus.getStyleClass().add("gn-menu-bar");
//        menu.setMinWidth(barHeight.get());
        menus.setMinHeight(barHeight.get());

//        btn_ico.setStyle("-fx-background-color : transparent;");
//
//        icon.setId("icon");
//        icon.setContent("M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z");
//        btn_ico.setGraphic(icon);
//        icon.setFill(Color.web("#999"));
//        menu.getChildren().add(btn_ico);
//        menu.setAlignment(Pos.CENTER);
        return menus;
    }

    /**
     * Set icon for this decoration.
     * @param node icon.
     */
    @Deprecated
    public void setIcon(Node node){
//        menus.getChildren().add(node);
    }

    private HBox titleContent() {
        title_content.setId("menu");
        title_content.getChildren().add(title);
        title_content.setAlignment(Pos.CENTER_LEFT);
        title.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(title_content, Priority.ALWAYS);
        HBox.setMargin(title, new Insets(0,0,0,10));
        return title_content;
    }

    /**
     * Centralize title.
     * Center the title using the half-width of the
     * stage and subtract the width of the right and left controls
     */
    public void centralizeTitle(){
        Platform.runLater(()->{
            title_content.setAlignment(Pos.CENTER);
            title.setAlignment(Pos.CENTER);


            double countR = 0;

            for(Node node : controls.getChildren()){
                if(node instanceof Button){
                    countR += ((Button) node).getWidth();
                } else if(node instanceof HBox){
                    countR += ((HBox) node).getWidth();
                }
            }

            title.setTranslateX(countR / 2);
        });
    }

    /**
     * Sets the body of the application as background.
     * This method makes the content occupy every area of the decoration,
     * however when a component not resized by width or height is scaled a
     * scroolpane launches a bar to define the border limits, when this happens
     * a vertical bar appears next to the close button if the limit resize is
     * reached, then a minimum height limit must be set.
     * perfect for sliding applications.
     * @param minHeight Minimum height limit.
     * @deprecated
     */
    public void fullBody(@NamedArg("minHeight") double minHeight){
        AnchorPane.setTopAnchor(this.areaContent, 0D);
        this.bar.toFront();
        top.toFront();
        top_left.toFront();
        top_right.toFront();
        
        this.stage.setHeight(minHeight);
        this.stage.setMinHeight(minHeight);
    }

    /**
     * Sets the body of the application as background. This method makes the content occupy every area of the
     * decoration, however when a component not resized by width or height is scaled a scroolpane launches a
     * bar to define the border limits.
     */
    public void fullBody() {
        AnchorPane.setTopAnchor(areaContent, 0D);

        bar.toFront();
        top.toFront();

        top_left.toFront();
        top_right.toFront();


    }

    @Deprecated
    public void fullBody(Insets insets) {
        AnchorPane.setTopAnchor(areaContent, 0D);
        AnchorPane.setRightAnchor(areaContent, 0D);

        bar.toFront();
        top.toFront();
        right.toFront();
        left.toFront();
    }

    /**
     * This method position actions maximize, minimize and close.
     * The bar is set to a minimum size to contain only your children and is position in left.
     */
    public void floatActions(){
        AnchorPane.setTopAnchor(areaContent, 0D);
        bar.toFront();
        bar_content.getChildren().removeAll(title_content, menus);
        AnchorPane.clearConstraints(bar);
        AnchorPane.setTopAnchor(bar, 0D);
        AnchorPane.setRightAnchor(bar, 0D);
    }

    /**
     * This method position actions maximize, minimize and close.
     * The bar is set to a minimum size to contain only your children and is position in left.
     */
//    public void floatActions(Region bar2){
//        AnchorPane.setTopAnchor(areaContent, 0D);
////        bar.toFront();
//        bar_content.getChildren().removeAll(title_content, menus);
//        AnchorPane.clearConstraints(bar);
//        AnchorPane.setTopAnchor(bar, 0D);
//        AnchorPane.setRightAnchor(bar, 0D);
//
//        addBarActions(bar2);
//    }

    /**
     * Sets the body of the application as background. This method makes the
     * content occupy every area of the decoration, however when a component not
     * resized by width or height is scaled a scroolpane launches a bar to
     * define the border limits, when this happens a vertical bar appears next
     * to the close button if the limit resize is reached, then a minimum height
     * limit must be set. perfect for responsive applications.
     *
     * @param minWidth  Minimum width limit.
     * @param minHeight Minimum height limit.
     * @deprecated
     */
    public void fullBody(@NamedArg("minWidth") double minWidth, @NamedArg("minHeight") double minHeight) {
        AnchorPane.setTopAnchor(this.areaContent, 0D);
        this.bar.toFront();
        top.toFront();
        top_left.toFront();
        top_right.toFront();

        this.stage.setHeight(minHeight);
        this.stage.setMinHeight(minHeight);
    }
    
    /**
     * Adiciona ações aos eixos e barras que redimensiona o conteudo decoração |
     * Add actions to the axes and bars that resize the decor content.
     */
    private void addActions() {

        btn_close.setOnMouseClicked(event -> getStage().close());
        btn_maximize.setOnMouseClicked(event -> maximizeOrRestore());
        btn_minimize.setOnMouseClicked(event -> stage.setIconified(true));
        
        right.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                initX = event.getScreenX();
                initY = event.getScreenY();
                event.consume();
            }
        });

        right.setOnMouseDragged(event -> {
            
            if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
                return;
            }
            if (getStage().isFullScreen()) {
                return;
            }
            if (event.isStillSincePress()) {
                return;
            }

            newX = event.getScreenX();
            newY = event.getScreenY();
            double deltax = newX - initX;
            double deltay = newY - initY;

            Cursor cursor = right.getCursor();

            if (Cursor.E_RESIZE.equals(cursor)) {
                setStageWidth(getStage(), getStage().getWidth() + deltax);
                event.consume();
            }
        });

        left.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                initX = event.getScreenX();
                initY = event.getScreenY();
                event.consume();
            }
        });

        left.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
                return;
            }
            if (getStage().isFullScreen()) {
                return;
            }
            if (event.isStillSincePress()) {
                return;
            }

            newX = event.getScreenX();
            newY = event.getScreenY();
            double deltax = newX - initX;
            double deltay = newY - initY;

            Cursor cursor = left.getCursor();
            if (Cursor.W_RESIZE.equals(cursor)) {
                if (setStageWidth(getStage(), getStage().getWidth() - deltax)) {
                    getStage().setX(getStage().getX() + deltax);
                }
                event.consume();
            }
        });

        top.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                initX = event.getScreenX();
                initY = event.getScreenY();
                event.consume();
            }
        });

        top.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
                return;
            }

            if (getStage().isFullScreen()) {
                return;
            }
            if (event.isStillSincePress()) {
                return;
            }

            newX = event.getScreenX();
            newY = event.getScreenY();
            double deltax = newX - initX;
            double deltay = newY - initY;
            Cursor cursor = top.getCursor();
            if (Cursor.N_RESIZE.equals(cursor)) {
                if (setStageHeight(getStage(), getStage().getHeight() - deltay)) {
                    setStageY(getStage(), getStage().getY() + deltay);
                }
                event.consume();
            }
        });

        bottom.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                initX = event.getScreenX();
                initY = event.getScreenY();
                event.consume();
            }
        });

        bottom.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
                return;
            }
            if (getStage().isFullScreen()) {
                return;
            }
            if (event.isStillSincePress()) {
                return;
            }

            newX = event.getScreenX();
            newY = event.getScreenY();
            double deltax = newX - initX;
            double deltay = newY - initY;

            Cursor cursor = bottom.getCursor();
            if (Cursor.S_RESIZE.equals(cursor)) {
                setStageHeight(getStage(), getStage().getHeight() + deltay);
                event.consume();
            }
        });

        top_right.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                initX = event.getScreenX();
                initY = event.getScreenY();
                event.consume();

            }
        });

        top_right.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
                return;
            }

            if (getStage().isFullScreen()) {
                return;
            }

            if (event.isStillSincePress()) {
                return;
            }

            newX = event.getScreenX();
            newY = event.getScreenY();
            double deltax = newX - initX;
            double deltay = newY - initY;

            Cursor cursor = top_right.getCursor();

            if (Cursor.NE_RESIZE.equals(cursor)) {
                if (setStageHeight(getStage(), getStage().getHeight() - deltay)) {
                    setStageY(getStage(), getStage().getY() + deltay);
                }
                setStageWidth(getStage(), getStage().getWidth() + deltax);
                event.consume();
            }
        });

        top_left.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                initX = event.getScreenX();
                initY = event.getScreenY();
                event.consume();
            }
        });

        top_left.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
                return;
            }
            if (getStage().isFullScreen()) {
                return;
            }
            if (event.isStillSincePress()) {
                return;
            }

            newX = event.getScreenX();
            newY = event.getScreenY();
            double deltax = newX - initX;
            double deltay = newY - initY;

            Cursor cursor = top_left.getCursor();

            if (Cursor.NW_RESIZE.equals(cursor)) {
                if (setStageWidth(getStage(), getStage().getWidth() - deltax)) {
                    getStage().setX(getStage().getX() + deltax);
                }
                if (setStageHeight(getStage(), getStage().getHeight() - deltay)) {
                    setStageY(getStage(), getStage().getY() + deltay);
                }
                event.consume();
            }
        });

        bottom_right.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                initX = event.getScreenX();
                initY = event.getScreenY();
                event.consume();
            }
        });

        bottom_right.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
                return;
            }

            if (getStage().isFullScreen()) {
                return;
            }
            if (event.isStillSincePress()) {
                return;
            }

            newX = event.getScreenX();
            newY = event.getScreenY();
            double deltax = newX - initX;
            double deltay = newY - initY;

            Cursor cursor = bottom_right.getCursor();

            if (Cursor.SE_RESIZE.equals(cursor)) {
                setStageWidth(getStage(), getStage().getWidth() + deltax);
                setStageHeight(getStage(), getStage().getHeight() + deltay);
                event.consume();
            }
        });

        bottom_left.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                initX = event.getScreenX();
                initY = event.getScreenY();
                event.consume();
            }
        });

        bottom_left.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
                return;
            }
            if (getStage().isFullScreen()) {
                return;
            }
            if (event.isStillSincePress()) {
                return;
            }

            newX = event.getScreenX();
            newY = event.getScreenY();
            double deltax = newX - initX;
            double deltay = newY - initY;

            Cursor cursor = bottom_left.getCursor();

            if (Cursor.SW_RESIZE.equals(cursor)) {
                if (setStageWidth(getStage(), getStage().getWidth() - deltax)) {
                    getStage().setX(getStage().getX() + deltax);
                }
                setStageHeight(getStage(), getStage().getHeight() + deltay);
                event.consume();
            }
        });


        addBarActions(bar);

    }

    public void addBarActions(Region bar){
        bar.setOnMousePressed(event -> {
            initX = event.getScreenX();
            initY = event.getScreenY();
        });

        mousePressedB = (EventHandler<MouseEvent>) bar.getOnMousePressed();

        bar.setOnMouseDragged(e -> {

            if (!e.isPrimaryButtonDown() || initX == -1) {
                return;
            }

            if (e.isStillSincePress()) {
                return;
            }


            if (savedBounds == null) {
                savedBounds = initialBound;
            }

            if(isMaximized() && isResizable()){

                stage.setX(e.getScreenX() - savedBounds.getWidth() / 2);
                stage.setY(0);
                stage.setWidth(savedBounds.getWidth());
                stage.setHeight(savedBounds.getHeight());

                // verifica se a posicao não atinji o limite da borda
                if(stage.getX() < bounds.getMinX()){
                } else if((stage.getX() + savedBounds.getWidth() )  > bounds.getMaxX()){
                    stage.setX(bounds.getMaxX() - savedBounds.getWidth());
                }

                setMaximized(false);
                btn_maximize.updateState(true);

            }

            viewBorders(true);

            newX = e.getScreenX();
            newY = e.getScreenY();
            double deltax = newX - initX;
            double deltay = newY - initY;
            initX = newX;
            initY = newY;
            stage.setX(stage.getX() + deltax);
            setStageY(stage, stage.getY() + deltay);
            if(isResizable()) configCursor(true);
            else configCursor(false);
            btn_maximize.setId("maximize");
            bar.setCursor(Cursor.MOVE);
        });

        mouseDraggedB = (EventHandler<MouseEvent>) bar.getOnMouseDragged();

        bar.setOnMouseReleased(event -> {
            if (stage.isResizable()) {
                initX = -1;
                initY = -1;
                bar.setCursor(Cursor.DEFAULT);
            }

        });

        bar.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                maximizeOrRestore();
            }
        });

    }


    /**
     * Restaura o tanho da janela | Restores size of the window..
     * Se a decoração inicial maximizavel ao restaurar o tamanho é decrecido em 10, no outro caso
     * o tamanho é restaurado para antes de maximizar | f the initial
     * decoration maximizable when restoring size is decreased by 10, in the
     * other case The size is restored to before maximizing..
     */
    public void restore() {
        if (savedBounds == null) {
            savedBounds = initialBound;
        }
        
        restoreSavedBounds(stage);
        btn_maximize.updateState(true);
        viewBorders(true);
        configCursor(true);
    }

    /**
     * Maximiza a decoração | Maximize decoration.
     */
    private void maximize() {
       //set Stage boundaries to visible bounds of the main screen
       
       this.savedBounds = new BoundingBox(this.stage.getX(), this.stage.getY(), this.stage.getWidth(), this.stage.getHeight());
       
        this.stage.setX(bounds.getMinX());
        this.stage.setY(bounds.getMinY());
        this.stage.setWidth(bounds.getWidth());
        this.stage.setHeight(bounds.getHeight());
        this.background.setMinWidth(bounds.getWidth());
        this.background.setMinHeight(bounds.getHeight());
//
        this.stage.setFullScreen(false); // important
        btn_fullScreen.updateState(true);
        btn_maximize.updateState(false);
        viewBorders(false);
        stage.centerOnScreen();
        configCursor(false);
    }

    /**
     * Maximiza ou restaura o tamanho da decoração | Maximizes or restores the
     * size of the decor.
     */
    private void maximizeOrRestore() {
        if (isResizable()) {
            if (isMaximized()) {
                restore();
                maximizedProperty.set(false);
            } else {
                maximize();
                maximizedProperty.set(true);
            }
        }
    }

    /**
     * Restaura os bounds da janela | Restores the bounds of the decor.
     * @param stage Stage for restore.
     */
    private void restoreSavedBounds(Stage stage) {
        stage.setX(savedBounds.getMinX());
        stage.setY(savedBounds.getMinY());
        stage.setWidth(savedBounds.getWidth());
        stage.setHeight(savedBounds.getHeight());
        savedBounds = null;
    }

    /**
     * Configura nova posição do eixo y durante o evento | Sets new y-axis
     * position during the event.
     *
     * @param stage Stage for change.
     * @param y Axis y of stage.
     */
    private void setStageY(Stage stage, double y) {
        try {
            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            if (screensForRectangle.size() > 0) {
                Screen screen = screensForRectangle.get(0);
                Rectangle2D visualBounds = screen.getVisualBounds();
                if (y < visualBounds.getHeight()) {
                    stage.setY(y);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Redimensiona a largura do palco retornando verdadeiro se a ação for
     * efetivada e falso se não for | Resize the Stage Width by Returning to an
     * Action made and false if it is not..
     *
     * @param stage Stage to change the width.
     * @param width New width.
     * @return Verdadeiro se a largura for maior ou igual a largura minima |
     * True if the width is greater than or equal to the minimum width.
     * permitida.
     */
    private boolean setStageWidth(Stage stage, double width) {
        if (width >= stage.getMinWidth()) { // se a largura for maior ou igual a largura minima permitida.
            stage.setWidth(width); // altera a largura do stage.
            initX = newX; // o valor inicial do eixo x agora é o valor do novo eixo x gerado no evento.
            return true;
        }
        return false;
    }

    /**
     * Redimensiona o comprimento do palco retornando verdadeiro se a ação for
     * efetivada e falso se não for | Resizes the stage length by returning true
     * if the action is   made and false if it is not..
     *
     * @param stage Stage to change length.
     * @param height New height.
     * @return verdadeiro se o comprimento for maior ou igual o comprimento
     * minimo permitido | true if the length is greater than or equal to the
     * length   minimum allowed..
     */
    private boolean setStageHeight(Stage stage, double height) {
        if (height >= stage.getMinHeight()) { // se o comprimento for maior ou igual o comprimento minimo permitido.
            stage.setHeight(height); // altera o comprimento do stage.
            initY = newY; // o valor inicial do eixo y agora é o valor do novo eixo y gerado no evento.
            return true;
        }
        return false;
    }

    private void configCursor(boolean resizable) {
        if (!resizable) {
            top.cursorProperty().set(Cursor.DEFAULT);
            right.cursorProperty().set(Cursor.DEFAULT);
            left.cursorProperty().set(Cursor.DEFAULT);
            bottom.cursorProperty().set(Cursor.DEFAULT);

            top_right.cursorProperty().set(Cursor.DEFAULT);
            top_left.cursorProperty().set(Cursor.DEFAULT);
            bottom_right.cursorProperty().set(Cursor.DEFAULT);
            bottom_left.cursorProperty().set(Cursor.DEFAULT);

        } else {
            top.cursorProperty().set(Cursor.N_RESIZE);
            left.cursorProperty().set(Cursor.W_RESIZE);
            right.cursorProperty().set(Cursor.E_RESIZE);
            bottom.cursorProperty().set(Cursor.S_RESIZE);

            top_right.cursorProperty().set(Cursor.NE_RESIZE);
            top_left.cursorProperty().set(Cursor.NW_RESIZE);
            bottom_right.cursorProperty().set(Cursor.SE_RESIZE);
            bottom_left.cursorProperty().set(Cursor.SW_RESIZE);
        }
    }

    /**
     * Visualizar as barras de redimensionamento | View the resize bars
     * @param view The bars of decor.
     */
    private void viewBars(boolean view){
        if(view){
            top_left.setOpacity(1);
            top_right.setOpacity(1);
            bottom_left.setOpacity(1);
            bottom_right.setOpacity(1);
            
            left.setOpacity(1);
            right.setOpacity(1);
            top.setOpacity(1);
            bottom.setOpacity(1);
            
        } else {
            top_left.setOpacity(0);
            top_right.setOpacity(0);
            bottom_left.setOpacity(0);
            bottom_right.setOpacity(0);

            left.setOpacity(0);
            right.setOpacity(0);
            top.setOpacity(0);
            bottom.setOpacity(0);
        }
    }
    
    private void viewBorders(boolean view){
        if(view && !isMaximized() && !stage.isFullScreen()) this.background.setStyle("-fx-border-width : 1");
        else this.background.setStyle("-fx-border-width : 0");
    }
    
    public void addButton(ButtonType button){
        switch(button){
            case FULL_EFFECT :
                fullScreen();
                configFullEffect();
                break;
            case FULL_SCREEN :
                fullScreen();
                configFullScreen();
            break;
        }
    }

    public void addControl(Control control){
        updateControls(control);
    }

    public void addControl(int index, Control control){
        updateControls(index, control);
    }

    public void removeControl(Control control){
        this.controls.getChildren().remove(control);
    }

    public void removeControl(int index){
        this.controls.getChildren().remove(index);
    }

    private void updateControls(Node node){

        if(node instanceof Region){
            ((Region) node).setMaxHeight(getBarHeight());
            ((Region) node).setMinHeight(getBarHeight());
            ((Region) node).setPrefHeight(getBarHeight());
        }
        controls.getChildren().add(node);
//        node.toBack();


    }

    private void updateControls(Control control){

        if(control instanceof Control){
            ((Region) control).setMaxHeight(getBarHeight());
            ((Region) control).setMinHeight(getBarHeight());
            ((Region) control).setPrefHeight(getBarHeight());
        }

        control.getProperties().put("custom-control", "gn-custom-control");
        controls.getChildren().add(control);
        control.toBack();
    }

    private void updateControls(int index, Node control){

        if(control instanceof Region){
            ((Region) control).setMaxHeight(getBarHeight());
            ((Region) control).setMinHeight(getBarHeight());
            ((Region) control).setPrefHeight(getBarHeight());
        }
        control.getProperties().put("custom-control", "gn-custom-control");

        if(controls.getChildren().size() - index < 0){
            System.err.println("The index does not exists");
        }

        controls.getChildren().add(controls.getChildren().size() - index, control);
//        node.toBack();
    }

    public void hideControls(){
        controls.getChildren().stream()
                .filter(e -> e instanceof Control && e.getProperties().containsValue("gn-custom-control"))
                .forEach(e -> e.setVisible(false));
    }

    public void removeControls(){
        controls.getChildren().stream()
                .filter(e -> e instanceof Control && e.getProperties().containsValue("gn-custom-control"))
                .collect(Collectors.toSet()).forEach(e -> controls.getChildren().removeAll(e));
    }

    public void showControls(){
        controls.getChildren().stream()
                .filter(e -> e instanceof Control && e.getProperties().containsValue("gn-custom-control"))
                .forEach(e -> e.setVisible(true));
    }

    public ObservableList<Control> getControls(){
       return FXCollections.observableArrayList(controls.getChildren().stream()
                .filter(e -> e instanceof Control && e.getProperties().containsValue("gn-custom-control"))
                .map( e -> (Control) e)
                .collect(Collectors.toSet()));
    }

    public void addMenu(Menu menu){
        menu.getStyleClass().add("gn-menu-item");
        menus.getMenus().add(menu);
    }

    public void addMenu(int index, Menu menu){
        menu.getStyleClass().add("gn-menu-item");
        menus.getMenus().add(index, menu);
    }

    public ObservableList<Menu> getMenus(){
        return menus.getMenus();
    }

    private GNFullscreen fullScreen(){
        btn_fullScreen.updateState(true );
        controls.getChildren().add(btn_fullScreen);
        btn_fullScreen.toBack();
        return this.btn_fullScreen;
    }

    public void block(){
        controls.getChildren().forEach(
                e -> e.setMouseTransparent(true)
        );

        menus.setMouseTransparent(true);

        bar.setMouseTransparent(true);

        top_left.setMouseTransparent(true);
        top_right.setMouseTransparent(true);
        bottom_left.setMouseTransparent(true);
        bottom_right.setMouseTransparent(true);

        left.setMouseTransparent(true);
        right.setMouseTransparent(true);
        top.setMouseTransparent(true);
        bottom.setMouseTransparent(true);
    }

    public void unblock(){
        controls.getChildren().forEach(
                e -> e.setMouseTransparent(false)
        );

        menus.setMouseTransparent(false);

        bar.setMouseTransparent(false);

        top_left.setMouseTransparent(false);
        top_right.setMouseTransparent(false);
        bottom_left.setMouseTransparent(false);
        bottom_right.setMouseTransparent(false);

        left.setMouseTransparent(false);
        right.setMouseTransparent(false);
        top.setMouseTransparent(false);
        bottom.setMouseTransparent(false);
    }

    private void configFullEffect(){
        btn_fullScreen.setOnMouseClicked(e -> {
            if (!stage.isFullScreen()) {
                stage.setFullScreen(true);
                configCursor(false);
                viewBar(true);
                viewBorders(false);
                btn_fullScreen.updateState(false);
                this.controls.getChildren().removeAll(btn_maximize, btn_minimize);
                updateMinWidth();
            } else {
                this.controls.getChildren().addAll(btn_minimize, btn_maximize);
                btn_maximize.toFront();
                btn_close.toFront();
                stage.setFullScreen(false);
                configCursor(true);
                viewBorders(true);
                btn_fullScreen.updateState(true);
            }
        });

        stage.fullScreenProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                configCursor(true);
                viewBar(true);
                if(GNDecorator.this.controls.getChildren().size() < 4){
                    GNDecorator.this.controls.getChildren().addAll(btn_minimize, btn_maximize);
                    btn_maximize.toFront();
                    btn_close.toFront();
                    
                }
                btn_fullScreen.updateState(true);
                
            }
        });

       
        EventHandler handler = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            if (event.getY() == 0 && stage.isFullScreen()) {
                viewBar(true);
                this.background.setOnMouseMoved(null);
            }
        };

        this.background.setOnMouseMoved(handler);

        this.bar.setOnMouseExited(e -> {
            if (stage.isFullScreen() && e.getY() > 0) {
                viewBar(false);
                this.background.setOnMouseMoved(handler);
            }
        });

        this.bar.setOnMouseMoved(e -> {
            if (stage.isFullScreen()) {
                this.background.setOnMouseMoved(null);
            }
        });

        this.bar.setOnMouseEntered(e -> {
            if (stage.isFullScreen()) {
                this.background.setOnMouseMoved(null);
            }

        });
    }
    
    private void configFullScreen(){
        btn_fullScreen.setOnMouseClicked(e -> {
            if (!stage.isFullScreen()) {
                stage.setFullScreen(true);
                viewBorders(false);
                this.bar.setVisible(false);
                configCursor(false);
                AnchorPane.setTopAnchor(this.areaContent, 0D);
            } 
            // provavelmente nunca chamado
            else {
                stage.setFullScreen(false);
                this.bar.setVisible(true);
                AnchorPane.setTopAnchor(this.areaContent, bar.getHeight());
            }
        });

        stage.fullScreenProperty().addListener(restoreFullScreen);
    }
    
    private void viewBar(boolean view){
        
        open.setFromY(-(barHeight.get()));
        open.setByY(bar.getMinHeight());
        
        close.setFromY(barHeight.get() * -1);
        close.setByY( - (barHeight.get() ));
        
        if(view) {
            open.play();
            AnchorPane.setTopAnchor(this.areaContent, barHeight.get());
        } else {
            AnchorPane.setTopAnchor(this.areaContent, 0D);
            close.play();
        }
    }
    
    public void initTheme(Theme theme){
        switch(theme){
            case DEFAULT :
                this.background.getStylesheets().clear();
                this.background.getStylesheets().add(getClass().getResource("/css/theme/default.css").toExternalForm());
                break;
            case DARK :
                this.background.getStylesheets().clear();
                this.background.getStylesheets().add(getClass().getResource("/css/theme/darkula.css").toExternalForm());
                break;
            case DANGER:
                this.background.getStylesheets().clear();
                // add theme
                this.background.getStylesheets().add(getClass().getResource("/css/theme/danger.css").toExternalForm());
                break;
            case INFO :
                this.background.getStylesheets().clear();
                // add theme
                this.background.getStylesheets().add(getClass().getResource("/css/theme/info.css").toExternalForm());
            break;
            case PRIMARY:
                this.background.getStylesheets().clear();
                // add css
                this.background.getStylesheets().add(getClass().getResource("/css/theme/primary.css").toExternalForm());
            break;
            case SECONDARY:
                this.background.getStylesheets().clear();
                // add css
                this.background.getStylesheets().add(getClass().getResource("/css/theme/secondary.css").toExternalForm());
            break;
            case SUCCESS:
                this.background.getStylesheets().clear();
                // add css
                this.background.getStylesheets().add(getClass().getResource("/css/theme/success.css").toExternalForm());
                break;
            case WARNING:
                this.background.getStylesheets().clear();
                // add css
                this.background.getStylesheets().add(getClass().getResource("/css/theme/warning.css").toExternalForm());
                break;
            case CUSTOM:
                this.background.getStylesheets().clear();
                // add css
                this.background.getStylesheets().add(getClass().getResource("/css/theme/custom.css").toExternalForm());
                break;
        }
    }

    public void show() {
        stage.show();
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            background.setPrefWidth(newValue.doubleValue());
            body.setPrefWidth(newValue.doubleValue());
            AnchorPane.setRightAnchor(content, newValue.doubleValue());
        });
        initRestore();
    }
    

    private void initRestore(){
        double x = stage.getX();
        double y = stage.getY();
        double width = stage.getWidth();
        double height = stage.getHeight();
        
        if(stage.isFullScreen()){
            width = Screen.getPrimary().getVisualBounds().getWidth() - 5;
            height = Screen.getPrimary().getVisualBounds().getHeight() - 5;
        }
        this.initialBound = new BoundingBox(x, y, width, height);
    }
}

