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

import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.scenicview.ScenicView;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/07/2020
 */
public class GNDecoratorT {

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

    private final Background background = new Background(body);

    private final Scene scene = new Scene(background, 800,600);


    private BoundingBox noMaximizedBounds = null;

    public GNDecoratorT() {
        configStage();
    }

    private void configStage() {
//        this.stage = new Stage(StageStyle.UNDECORATED);
//        this.scene = new Scene(background,800,600);
        this.scene.setFill(Color.WHITE);

        this.stage.setScene(this.scene);
        this.stage.setMinWidth(254.0D);
        this.stage.setMinHeight(57.0D);
//
//        this.stage.widthProperty().divide(scene.heightProperty()); // não sei o que faz
//        this.stage.heightProperty().divide(scene.widthProperty());
    }

    public void setContent(Node content){
        this.areaContent.setContent(content);
    }

    public void show(){
        this.stage.show();
        this.noMaximizedBounds = new BoundingBox(this.stage.getX(), this.stage.getY(),
                this.stage.getWidth(), this.stage.getHeight());
    }

    BoundingBox getNoMaximizedBounds(){
        return this.noMaximizedBounds;
    }

    void setBounds(BoundingBox save){
        this.noMaximizedBounds = save;
    }

    Stage getStage(){
        return this.stage;
    }

    Scene getScene(){
        return this.scene;
    }

    public void testWithScenicView(){
        ScenicView.show(this.scene);
    }
}
