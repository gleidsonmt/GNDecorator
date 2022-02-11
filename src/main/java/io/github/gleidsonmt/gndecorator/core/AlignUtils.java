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

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/07/2020
 */
interface AlignUtils {

    default void alignTopAnchor(Node node){
        AnchorPane.setTopAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
    }

    default void alignRightAnchor(Node node){
        AnchorPane.setTopAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
    }

    default void alignBottomAnchor(Node node){
        AnchorPane.setLeftAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
    }

    default void alignLeftAnchor(Node node){
        AnchorPane.setTopAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
    }

    default void alignTopLeftAnchor(Node node){
        AnchorPane.setTopAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
    }

    default void alignTopRightAnchor(Node node) {
        AnchorPane.setTopAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
    }

    default void alignBottomLeftAnchor(Node node){
        AnchorPane.setLeftAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
    }

    default void alignBottomRightAnchor(Node node){
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
    }

    default void fullBody(Node node){
        AnchorPane.setTopAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
    }

    default void alignContent(Node node, double barHeight){
        AnchorPane.setTopAnchor(node, barHeight);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
    }
}
