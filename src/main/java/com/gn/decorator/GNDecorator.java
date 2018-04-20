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
package com.gn.decorator;

import com.gn.decorator.buttons.Close;
import com.gn.decorator.buttons.FullScreen;
import com.gn.decorator.buttons.Maximize;
import com.gn.decorator.buttons.Minimize;
import com.gn.decorator.options.ButtonType;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Protótipo de decoração com barra de navegação | Prototype of decoration with
 * navigation bar.<br>
 *
 * <p>
 * Cria um node com barras (AnchorPane) fixas nos cantos e nas extremidades
 * eixos (Paths), e um barra(Hbox) no tpo contendo tres paineis(HBox) um esquerdo, um central e
 * um no direito, o da direita é dedicada a apresentação, o do meio ao titulo da janela e o da esquerada
 * aos buttons de controle | Creates a node with fixed (AnchorPane) bars in corners and ends Paths, and a
 * bar (Hbox) in the tpo containing three panels (HBox) one left, one central
 * and one in the right, the one in the right is dedicated to the presentation,
 * the middle to the window title and the left control buttons.
 * </p><br>
 * <p>
 * As barras são vermelhas e os eixos são pretos, as vermelhas representam o
 * redimensionamento da largura e comprimento e os pretos os dois ao mesmo tempo
 * em uma direção específica | The bars are red and the axes are black, the reds
 * represent the resizing of the width and length and the blacks the two at the
 * same time in a specific direction.
 * </p>
 *
 *
 * <p>
 * <b>Example:</b></p>
 *
 * <pre><code>
 * GNDecorationProto decoration = new GNDecorationProto();
 * decoration.viewBar(true); // View resize bars
 * decoration.show();
 *
 * </code></pre>
 *
 * <p>
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created on 12/04/2018
 */
public class GNDecorator extends StackPane {

    private final Stage stage = new Stage(StageStyle.UNDECORATED);
    private final Scene scene = new Scene(this, Color.TRANSPARENT);

    private final AnchorPane body        = new AnchorPane();
    public  final ScrollPane container   = new ScrollPane();
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
    private final HBox          menu             = new HBox();
    private final HBox          title_content    = new HBox();
    
    private final Close      btn_close      = new Close();
    private final Maximize   btn_maximize   = new Maximize();
    private final Minimize   btn_minimize   = new Minimize();
    private final FullScreen btn_fullScreen = new FullScreen();
    private final Label      title          = new Label("Application");
    private final SVGPath    icon           = new SVGPath();
    
    private final ImageView viewMinimize    = new ImageView(new Image("img/minimize.png"));
    private final ImageView viewMaximize    = new ImageView(new Image("img/maximize.png"));
    private final ImageView viewClose       = new ImageView(new Image("img/close.png"));

    private static double initX = -1;
    private static double initY = -1;

    private static double newX;
    private static double newY;
    
    private Rectangle2D bounds       = null;
    private BoundingBox savedBounds  = null;
    private BoundingBox initialBound = null;
    
    private static final String USER_AGENT_STYLESHEET  = GNDecorator.class.getResource("/css/decorator/decorator.css").toExternalForm();
    
    private final BooleanProperty resizableProperty = new SimpleBooleanProperty(GNDecorator.this, "resizableProperty", true);
    private final StringProperty  titleProperty     = new SimpleStringProperty(GNDecorator.this, "textProperty", "title");
    private final BooleanProperty maximizedProperty = new SimpleBooleanProperty(GNDecorator.this, "maximizedProperty", false);
   
    private DoubleProperty barHeight = new SimpleDoubleProperty(GNDecorator.this, "barSize", 30);
    private DoubleProperty buttonHeight = new SimpleDoubleProperty(GNDecorator.this, "buttonHeiht", 30);
    private DoubleProperty buttonWidth = new SimpleDoubleProperty(GNDecorator.this, "buttonWidth", 30);
    
    
    private final TranslateTransition open = new TranslateTransition(Duration.millis(100D), this.bar);
    private final TranslateTransition close = new TranslateTransition(Duration.millis(100D), this.bar);
    
    private final ChangeListener<Object> restaureFullScreen = new ChangeListener<Object>() {
        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
            if (newValue != null) {
                configCursor(true);
                AnchorPane.setTopAnchor(GNDecorator.this.areaContent, barHeight.get());
                btn_fullScreen.updateState(true);
                if(!GNDecorator.this.bar.isVisible()){
                    GNDecorator.this.bar.setVisible(true);
                    viewBorders(true);
                }
                if(isResizable() && !isMaximized()) configCursor(true); else configCursor(false);
            }
        }
    };
    
    /********************************************************************************************
     * 
     * 
     *                      Construtors
     *      
     * 
     ********************************************************************************************/
    
    /**
     * Cria uma decoração | Create a decoration.
     */
    public GNDecorator() {
        super();
        configStage();
        configLayout();
        addActions();
        bounds = Screen.getPrimary().getVisualBounds();
        title.textProperty().bind(titleProperty);
        controls.minHeightProperty().bind(barHeight);
        menu.minHeightProperty().bind(barHeight);
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
        
        btn_fullScreen.minHeightProperty().bind(buttonHeight);
        btn_fullScreen.prefHeightProperty().bind(buttonHeight);
        btn_fullScreen.minWidthProperty().bind(buttonWidth);
        btn_fullScreen.prefWidthProperty().bind(buttonWidth);
    }
    
    /********************************************************************************************
     *
     *
     *                              Public methods
     *
     *
     ********************************************************************************************/
    
    /**
     * Palco desta decoração | Stage this decoration.
     *
     * @return
     */
    public Stage getStage() {
        return this.stage;
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

    /**
     * Verifica se o palco está maximizado | Checks if the stage is maximized.
     *
     * @return true if it is, if not false.
     */
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
//            bar.setOnMouseDragged(null);
//            bar.setOnMousePressed(null);
            
            if(!resizable) {
                btn_maximize.setDisable(true);
                bar.setOnMouseClicked(null);
            }
//            if (isMaximized()) {
//                btn_maximize.setId("restore");
//            } else {
//                btn_maximize.setId("maximize");
//            }
        });
    }

    @Override
    public boolean isResizable(){
        return this.resizableProperty.get();
    }
    
    public BooleanProperty resizableProperty(){
        return this.resizableProperty;
    }
    
    /**
     * @param body o corpo para configurar.
     */
    public void setContent(Node body) {
        if(!this.content.getChildren().isEmpty())
            this.content.getChildren().clear();
        
        this.content.getChildren().add(body);
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
            stage.fullScreenProperty().addListener(restaureFullScreen);
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
    

    
    /**
     * Configura o palco | Config the stage.
     */
    private void configStage() {

        this.stage.setScene(this.scene);
        this.stage.setMinWidth(254.0D);
        this.stage.setMinHeight(57.0D);
    }

    /**
     * Configura o layout | Config the layout.
     */
    private void configLayout() {
        this.getStyleClass().add("gn-decorator");
        this.body.getStyleClass().add("gn-body");
        this.title.getStyleClass().add("gn-title");
        this.container.getStyleClass().add("gn-container");
        
//        this.container.getViewportBounds().false);
        
        // add body in window
        this.getChildren().add(this.body);
        
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
        
//        containersetStyle("-fx-background-color : blue");
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
    

    
    /**
     * Criar a região com os limites de bordas.
     *
     * @return Região configurada.
     */
    private Region createRegion() {
        
        AnchorPane.setTopAnchor(areaContent, barHeight.get());
        AnchorPane.setRightAnchor(areaContent, 0D);
        AnchorPane.setBottomAnchor(areaContent, 0D);
        AnchorPane.setLeftAnchor(areaContent, 0D);

        return areaContent;
    }

    /**
     * Cria um eixo no topo esquerdo | Creates an axis on top left.
     *
     * @return the axis.
     */
    private Path axisTopLeft() {
        top_left.setFill(Color.BLACK);
        top_left.setCursor(Cursor.NW_RESIZE);
        top_left.setId("top_left");
        pathLines(top_left);
        AnchorPane.setTopAnchor(top_left, 0D);
        AnchorPane.setLeftAnchor(top_left, 0D);
        return top_left;
    }

    /**
     * Cria um eixo no topo direito | Creates an axis at the top right.
     *
     * @return the axis.
     */
    private Path axisTopRight() {
        top_right.setCursor(Cursor.NE_RESIZE);
        top_right.setId("top_right");
        pathLines(top_right);
        top_right.setRotate(90D);
        AnchorPane.setTopAnchor(top_right, 0D);
        AnchorPane.setRightAnchor(top_right, 0D);
        return top_right;
    }

    /**
     * Cria um eixo no fundo esquerdo | Creates an axis on the left bottom.
     *
     * @return the axis.
     */
    private Path axisBottomLeft() {
        bottom_left.setCursor(Cursor.SW_RESIZE);
        bottom_left.setId("bottom_left");
        pathLines(bottom_left);
        bottom_left.setRotate(270D);
        AnchorPane.setLeftAnchor(bottom_left, 0D);
        AnchorPane.setBottomAnchor(bottom_left, 0D);
        return bottom_left;
    }

    /**
     * Cria um eixo no fundo esquerdo | Creates an axis on the left bottom.
     *
     * @return the axis.
     */
    private Path axisBottomRight() {
        bottom_right.setCursor(Cursor.SE_RESIZE);
        bottom_right.setId("bottom_right");
        pathLines(bottom_right);
        bottom_right.setRotate(180D);
        AnchorPane.setRightAnchor(bottom_right, 0D);
        AnchorPane.setBottomAnchor(bottom_right, 0D);
        return bottom_right;
    }

    /**
     * Desenha os eixos | Draw the axes.
     *
     * @param path the axis.
     */
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
    
    /**
     * Cria uma barra na esquerda | Create bar in left.
     *
     * @return One bar of left.
     */
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

    /**
     * Cria uma barra na direita | Create bar in right.
     *
     * @return One bar of right.
     */
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

    /**
     * Cria uma barra no topo | Create bar in top.
     *
     * @return One bar of top.
     */
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

    /**
     * Cria uma barra no fundo | Create bar in bottom.
     * @return One bar of bottom.
     */
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
    
    /**
     * Configura as barass | Config the bars.
     * @param bar The bar for configuration.
     */
    private void bars(AnchorPane bar) {
        bar.setStyle("-fx-background-color : red");
    }
    
    /**
     * Configura a barra da decoração | Config the bar of decor.
     * @return The bar content.
     */
    private AnchorPane bar(){
        bar.getStyleClass().add("gn-bar");
        bar.setMinHeight(barHeight.get());
        AnchorPane.setTopAnchor(bar, 0D);
        AnchorPane.setRightAnchor(bar, 0D);
        AnchorPane.setLeftAnchor(bar, 0D);
        return bar;
    }
    

    /**
     * Configura o content da barra | Config the content of bar.
     * @return The bar content. 
     */
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
    
    /**
     * Configura os controles básicos da decoração |  
     * Config os controles básicos da decoração Configure basic decor controls.
     * @return The controls.
     */
    private HBox controls(){
        controls.getStyleClass().add("gn-buttons");
        controls.setAlignment(Pos.CENTER);
        
        double prefWidth = buttonWidth.get(), prefHeiht = buttonHeight.get();
        
        btn_minimize.setGraphic(viewMinimize);
        btn_maximize.setGraphic(viewMaximize);
        btn_close.setGraphic(viewClose);
        
        btn_minimize.setMinSize(prefWidth, prefHeiht);
        btn_maximize.setMinSize(prefWidth, prefHeiht);
        btn_close.setMinSize(prefWidth, prefHeiht);
        
        btn_minimize.setPrefSize(prefWidth, prefHeiht);
        btn_maximize.setPrefSize(prefWidth, prefHeiht);
        btn_close.setPrefSize(prefWidth, prefHeiht);
        
        controls.getChildren().addAll(btn_minimize, btn_maximize, btn_close);
        controls.setMinWidth(buttonWidth.get() * controls.getChildren().size());
        return controls;
    }
    
    /**
     * Configura o container de menu direito | Configure the right menu container.
     * @return The container of menu.
     */
    private HBox menu(){
        menu.getStyleClass().add("gn-menu");
        menu.setMinWidth(barHeight.get());
        menu.setMinHeight(barHeight.get());
        Button btn_ico = new Button();
        icon.setId("icon");
        icon.setContent("M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z");
        btn_ico.setGraphic(icon);
        icon.setFill(Color.web("#999"));
        menu.getChildren().add(icon);
        menu.setAlignment(Pos.CENTER);
        return menu;
    }
    
    /**
     * Configura o container de titulo | Configure the title container.
     * @return The container of menu.
     */
    private HBox titleContent() {
        title_content.setId("menu");
        title_content.getChildren().add(title);
        title_content.setAlignment(Pos.CENTER_LEFT);
        title.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(title_content, Priority.ALWAYS);
        return title_content;
    }
    
    public void centralizeTitle(){
        title_content.setAlignment(Pos.CENTER);
        title.setAlignment(Pos.CENTER);
        double esquerda = menu.getMinWidth();
        double direita = getButtonWidth() * controls.getChildren().size();
        double x = direita - esquerda;
        title.setTranslateX(x / 2);
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
     * Sets the body of the application as background. This method makes the
     * content occupy every area of the decoration, however when a component not
     * resized by width or height is scaled a scroolpane launches a bar to
     * define the border limits, when this happens a vertical bar appears next
     * to the close button if the limit resize is reached, then a minimum height
     * limit must be set. perfect for responsive applications.
     *
     * @param minWidth  Minimum width limit.
     * @param minHeight Minimum height limit.
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

        btn_close.setOnMouseClicked(event -> stage.close());
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

        bar.setOnMousePressed(event -> {
            initX = event.getScreenX();
            initY = event.getScreenY();
        });

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
                    stage.setX(bounds.getMinX());
                } else if((stage.getX() + savedBounds.getWidth() )  > bounds.getMaxX()){
                    stage.setX(bounds.getMaxX() - savedBounds.getWidth());
                }

            }

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
        System.out.println(savedBounds);
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
    public void maximize() {
       //set Stage boundaries to visible bounds of the main screen
       
       this.savedBounds = new BoundingBox(this.stage.getX(), this.stage.getY(), this.stage.getWidth(), this.stage.getHeight());
       
        this.stage.setX(bounds.getMinX());
        this.stage.setY(bounds.getMinY());
        this.stage.setWidth(bounds.getWidth());
        this.stage.setHeight(bounds.getHeight());
        this.setWidth(bounds.getWidth());
        this.setHeight(bounds.getHeight());
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
            } else {
                maximize();
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
    public void viewBars(boolean view){
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
        if(view && !isMaximized() && !stage.isFullScreen()) this.setStyle("-fx-border-width : 1");
        else this.setStyle("-fx-border-width : 0");
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

    private FullScreen fullScreen(){
        btn_fullScreen.updateState(true );
        controls.getChildren().add(btn_fullScreen);

        btn_fullScreen.toBack();
        return this.btn_fullScreen;
    }
    
    private void configFullEffect(){
        btn_fullScreen.setOnMouseClicked(e -> {
            if (!stage.isFullScreen()) {
                stage.setFullScreen(true);
                configCursor(false);
                viewBar(false);
                viewBorders(false);
                btn_fullScreen.updateState(false);
                this.controls.getChildren().removeAll(btn_minimize, btn_maximize);
            } else {
                this.controls.getChildren().addAll(btn_minimize, btn_maximize);
                btn_maximize.toFront();
                btn_close.toFront();
                viewBorders(true);
                stage.setFullScreen(false);
                configCursor(true);
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
                this.setOnMouseMoved(null);
            }
        };

        this.setOnMouseMoved(handler);

        this.bar.setOnMouseExited(e -> {
            if (stage.isFullScreen() && e.getY() > 0) {
                viewBar(false);
                this.setOnMouseMoved(handler);
            }
        });

        this.bar.setOnMouseMoved(e -> {
            if (stage.isFullScreen()) {
                this.setOnMouseMoved(null);
            }
        });

        this.bar.setOnMouseEntered(e -> {
            if (stage.isFullScreen()) {
                this.setOnMouseMoved(null);
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

        stage.fullScreenProperty().addListener(restaureFullScreen);
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
                this.getStylesheets().clear();
                this.getStylesheets().add(getClass().getResource("/css/theme/default.css").toExternalForm());
                break;
            case DARKULA :
                this.getStylesheets().clear();
                this.getStylesheets().add(getClass().getResource("/css/theme/darkula.css").toExternalForm());
                break;
            case DANGER:
                this.getStylesheets().clear();
                // add css
                this.getScene().getStylesheets().add(getClass().getResource("/css/theme/roboto.css").toExternalForm());
                // add theme
                this.getStylesheets().add(getClass().getResource("/css/theme/danger.css").toExternalForm());
                break;
            case INFO :
                this.getStylesheets().clear();
                // add css
                this.getScene().getStylesheets().add(getClass().getResource("/css/theme/roboto.css").toExternalForm());
                // add theme
                this.getStylesheets().add(getClass().getResource("/css/theme/info.css").toExternalForm());
            break;
            case PRIMARY:
                this.getStylesheets().clear();
                // add css
                this.getScene().getStylesheets().add(getClass().getResource("/css/theme/roboto.css").toExternalForm());
                // add theme
                this.getStylesheets().add(getClass().getResource("/css/theme/primary.css").toExternalForm());
            break;
            case SECONDARY:
                this.getStylesheets().clear();
                // add css
                this.getScene().getStylesheets().add(getClass().getResource("/css/theme/roboto.css").toExternalForm());
                // add theme
                this.getStylesheets().add(getClass().getResource("/css/theme/secondary.css").toExternalForm());
            break;
            case SUCCESS:
                this.getStylesheets().clear();
                // add css
                this.getScene().getStylesheets().add(getClass().getResource("/css/theme/roboto.css").toExternalForm());
                // add theme
                this.getStylesheets().add(getClass().getResource("/css/theme/success.css").toExternalForm());
                break;
            case WARNING:
                this.getStylesheets().clear();
                // add css
                this.getScene().getStylesheets().add(getClass().getResource("/css/theme/roboto.css").toExternalForm());
                // add theme
                this.getStylesheets().add(getClass().getResource("/css/theme/warning.css").toExternalForm());
                break;
        }
    }

    /**
     * Inicializa o palco com a decoração | Initialize the stage with
     * decoration.
     */
    public void show() {
        stage.show();
        initRestaure();
    }
    
    public enum Style {
        DEFAULT, DARK
    };

    public enum Theme {
        DEFAULT, DARKULA, DANGER, INFO, PRIMARY, SECONDARY, WARNING, SUCCESS
    };

    
    /**
     * Pega os parametros do stage para definir a posição e o tamanho.
     * @return Um box com as dimensões.
     */
    private BoundingBox initRestaure(){
        double x = stage.getX();
        double y = stage.getY();
        double width = stage.getWidth();
        double height = stage.getHeight();
        
        if(stage.isFullScreen()){
            width = Screen.getPrimary().getVisualBounds().getWidth() - 5;
            height = Screen.getPrimary().getVisualBounds().getHeight() - 5;
        }
        this.initialBound = new BoundingBox(x, y, width, height);
        return this.initialBound;
    }

    @Override
    public String getUserAgentStylesheet() {
        return USER_AGENT_STYLESHEET;
    }
}