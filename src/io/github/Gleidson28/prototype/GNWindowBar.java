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
package io.github.Gleidson28.prototype;

import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
public class GNWindowBar extends StackPane {

    private final Stage stage = new Stage();
    private final Scene scene = new Scene(this);

    private final AnchorPane body   = new AnchorPane();
    private final StackPane content = new StackPane();

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
    
    private final Button  btn_close     = new Button();
    private final Button  btn_minimize  = new Button();
    private final Button  btn_maximize  = new Button();
    private final Label   title         = new Label("Application");
    private final SVGPath icon          = new SVGPath();
    
    private final ImageView viewMinimize    = new ImageView(new Image("img/minimize.png"));
    private final ImageView viewMaximize    = new ImageView(new Image("img/maximize.png"));
    private final ImageView viewClose       = new ImageView(new Image("img/close.png"));
    private final ImageView viewRestore     = new ImageView(new Image("img/restore.png"));

    private static double initX = -1;
    private static double initY = -1;

    private static double       newX;
    private static double       newY;
    private final Rectangle2D   bounds;
    
    private BoundingBox savedBounds     = null;
    private boolean     resizeInDrag    = true;

    /**
     * Cria uma decoração | Create a decoration.
     */
    public GNWindowBar() {
        super();
        bounds = Screen.getPrimary().getVisualBounds();
        configStage();
        configLayout();
        addActions();
    }

    /**
     * Decoration Stage.
     * @return Decoration Stage.
     */
    public Stage getStage() {
        return this.stage;
    }
    
    /**
     * Configura o palco | Config the stage.
     */
    private void configStage() {
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.scene.setFill(Color.TRANSPARENT);

        this.stage.setScene(this.scene);
        this.stage.setMinWidth(254.0D);
        this.stage.setMinHeight(57.0D);
    }

    /**
     * Configura o layout | Config the layout.
     */
    private void configLayout() {
        // Color initial
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.body.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        // add body in window
        this.getChildren().add(this.body);
        
        // add bar
        this.body.getChildren().add(bar());
        this.bar.getChildren().add(barContent());
        
        // add components in the bar
        this.bar_content.getChildren().add(menu());
        this.bar_content.getChildren().add(titleContent());
        this.bar_content.getChildren().add(controls());
        this.setStyle("-fx-border-color : #808080; -fx-border-width : 1");

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
        
        viewBars(false);
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
        bar.setId("bar");
        bar.setPrefHeight(36D);
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
        bar_content.setPrefHeight(36D);
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
        controls.setId("buttons");
        btn_minimize.setGraphic(viewMinimize);
        btn_maximize.setGraphic(viewMaximize);
        btn_close.setGraphic(viewClose);
        btn_minimize.setMinSize(40, 40);
        btn_maximize.setMinSize(40, 40);
        btn_close.setMinSize(40, 40);
        controls.getChildren().addAll(btn_minimize, btn_maximize, btn_close);
        
        return controls;
    }
    
    /**
     * Configura o container de menu direito | Configure the right menu container.
     * @return The container of menu.
     */
    private HBox menu(){
        menu.setId("menu");
        menu.setMinWidth(35);
        menu.setMinHeight(30);
        Button btn_ico = new Button();
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
        title_content.setAlignment(Pos.CENTER);
        HBox.setHgrow(title_content, Priority.ALWAYS);
        title_content.setPadding(new Insets(0,0,0,controls.getWidth()));
        return title_content;
    }

//    
//    private static final class StyleableProperties {
//
//        private static final CssMetaData<JFXRippler, Boolean> RIPPLER_RECENTER
//                = new CssMetaData<JFXRippler, Boolean>("-gn-color",
//                        BooleanConverter.getInstance(), false) {
//            @Override
//            public boolean isSettable(JFXRippler control) {
//                return control.ripplerRecenter == null || !control.ripplerRecenter.isBound();
//            }
//
//            @Override
//            public StyleableProperty<Boolean> getStyleableProperty(JFXRippler control) {
//                return control.ripplerRecenterProperty();
//            }
//        };
//        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
//
//        static {
//            final List<CssMetaData<? extends Styleable, ?>> styleables
//                    = new ArrayList<>(Parent.getClassCssMetaData());
//            Collections.addAll(styleables,
//                    RIPPLER_RECENTER
//            );
//            STYLEABLES = Collections.unmodifiableList(styleables);
//        }
//    }
//    
//    private StyleableObjectProperty<Style> windowType = new SimpleStyleableObjectProperty<>(
//            GNWindow.StyleableProperties.BUTTON_TYPE,
//            JFXButton.this,
//            "buttonType",
//            JFXButton.ButtonType.FLAT);
//
//    public JFXButton.ButtonType getButtonType() {
//        return buttonType == null ? JFXButton.ButtonType.FLAT : buttonType.get();
//    }
//
//    public StyleableObjectProperty<JFXButton.ButtonType> buttonTypeProperty() {
//        return this.buttonType;
//    }
//
//    public void setButtonType(JFXButton.ButtonType type) {
//        this.buttonType.set(type);
//    }
//
//    public void initStyle(Style style) {
//        switch (style) {
//            case DEFAULT:
//                scene.getStylesheets().addAll(
//                        getClass().getResource("/genesis/controls/css/style/light.css").toExternalForm()
//                );
//                break;
//            case DARK:
//                scene.getStylesheets().addAll(
//                        getClass().getResource("/genesis/controls/css/style/dark.css").toExternalForm()
//                );
//                break;
//        }
//    }
    
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
            initX = event.getSceneX();
            initY = event.getSceneY();
            if(isMaximized())
                resizeInDrag = true;
            else 
                resizeInDrag = false;
        });

        bar.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                getStage().setX(event.getScreenX() - initX);
                getStage().setY(event.getScreenY() - initY);
                bar.setCursor(Cursor.MOVE);
                btn_maximize.setGraphic(viewMaximize);
            }  
        });
        
        
        bar.setOnDragDetected(e -> {
            if (resizeInDrag) {
                savedBounds = new BoundingBox(stage.getX() - 20, stage.getY() + 20,
                        stage.getWidth() - 100, stage.getHeight() - 10);
                restoreSavedBounds(stage);
            }
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
     * Verifica se o palco está maximizado | Checks if the stage is maximized.
     * @return true if it is, if not false.
     */
    public boolean isMaximized() {
        return stage.getWidth() == Screen.getPrimary().getVisualBounds().getWidth()
                && stage.getHeight() == Screen.getPrimary().getVisualBounds().getHeight()
                && stage.getX() == Screen.getPrimary().getVisualBounds().getMinX()
                && stage.getY() == Screen.getPrimary().getVisualBounds().getMinY();
    }

    /**
     * Restaura o tanho da janela | Restores size of the window..
     * Se a decoração inicial maximizavel ao restaurar o tamanho é decrecido em 10, no outro caso
     * o tamanho é restaurado para antes de maximizar | f the initial
     * decoration maximizable when restoring size is decreased by 10, in the
     * other case The size is restored to before maximizing..
     */
    public void restore() {
//        if (!init) {

//            init = true;
//        }
        restoreSavedBounds(stage);
//        this.setPadding(new Insets(5));
        btn_maximize.setGraphic(viewMaximize);
        configCursor(true);
    }

    /**
     * Maximiza a decoração | Maximize decoration.
     */
    public void maximize() {
        savedBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());

        this.stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

//        if (init) {
//            content.setPrefSize(bounds.getWidth(), bounds.getHeight());
//        }

        btn_maximize.setGraphic(viewRestore);
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

    /**
     * Inicializa o palco com a decoração | Initialize the stage with
     * decoration.
     */
    public void show() {
        stage.centerOnScreen();
        stage.show();
    }
}
