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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Protótipo de decoração | Prototype decoration.<br>
 *
 * <p>
 * Cria um node com barras (AnchorPane) fixas nos cantos e nas extremidades
 * eixos (Paths) | Creates a node with fixed bars (AnchorPane) in the corners
 * and at the end axes (Paths).
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
 * decoration.show();
 *
 * </code></pre>
 *
 * <p>
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com <br>
 * Create 11/04/2018
 * @version 1.0
 */
public class GNWindowProto extends StackPane {

    private final Stage stage = new Stage();
    private final Scene scene = new Scene(this);

    private final AnchorPane body   = new AnchorPane();

    private final Path top_left     = new Path();
    private final Path top_right    = new Path();
    private final Path bottom_left  = new Path();
    private final Path bottom_right = new Path();

    private final AnchorPane left   = new AnchorPane();
    private final AnchorPane right  = new AnchorPane();
    private final AnchorPane top    = new AnchorPane();
    private final AnchorPane bottom = new AnchorPane();

    private static double initX = -1;
    private static double initY = -1;

    private static double newX;
    private static double newY;
    
    VBox node = new VBox();
    
    public void setBody(Node body){
        this.node.getChildren().add(body);
    }

    /**
     * Cria uma decoração | Create a decoration.
     */
    public GNWindowProto() {
        super();
        configStage();
        configLayout();
        addActions();
    }

    /**
     * Palco desta decoração | Stage this decoration.
     *
     * @return
     */
    private Stage getStage() {
        return this.stage;
    }

    /**
     * Configura o palco | Config the stage.
     */
    private void configStage() {
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.scene.setFill(Color.TRANSPARENT);

        this.stage.setScene(this.scene);
    }

    /**
     * Configura o layout | Config the layout.
     */
    private void configLayout() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Teste.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(GNWindowProto.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Color initial
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.body.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        // add body in window
        this.getChildren().add(this.body);
        
        AnchorPane.setTopAnchor(node, 1D);
        AnchorPane.setRightAnchor(node, 1D);
        AnchorPane.setBottomAnchor(node, 1D);
        AnchorPane.setLeftAnchor(node, 1D);
        this.body.getChildren().add(root);
        

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
        
        
   
    }

    /**
     * Cria um eixo no topo esquerdo | Creates an axis on top left.
     *
     * @return the axis.
     */
    private Path axisTopLeft() {
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
     * 
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
    private void bars(AnchorPane bar){
        bar.setStyle("-fx-background-color : red");
    }

    /**
     * Adiciona ações aos eixos e barras que redimensiona o conteudo decoração | 
     * Add actions to the axes and bars that resize the decor content.
     */
    private void addActions() {

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
            // Long press generates drag event!
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

            // Long press generates drag event!
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
            
           // Long press generates drag event!
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
            /*
         * Long press generates drag event!
             */
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

    /**
     * Inicializa o palco com a decoração | Initialize the stage with decoration.
     */
    public void show() {
        stage.centerOnScreen();
        stage.show();
    }
}
