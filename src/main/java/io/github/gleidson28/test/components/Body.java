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

import io.github.gleidson28.test.components.*;
import javafx.scene.layout.AnchorPane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/07/2020
 */
public class Body extends AnchorPane implements AlignUtils {

    public Body(Container container, Bar bar,
                TopBar topBar, RightBar rightBar,
                BottomBar bottomBar, LeftBar leftBar,
                TopLeftAnchor topLeftAnchor, TopRightAnchor topRightAnchor,
                BottomLeftAnchor bottomLeftAnchor, BottomRightAnchor bottomRightAnchor) {

        this.getStyleClass().add("gn-body");
        this.setId("gn-body");

        this.getChildren().addAll(container, bar, topBar, rightBar,
                bottomBar, leftBar, topLeftAnchor, topRightAnchor,
                bottomLeftAnchor, bottomRightAnchor);

        alignTopAnchor(topBar);
        alignRightAnchor(rightBar);
        alignBottomAnchor(bottomBar);
        alignLeftAnchor(leftBar);

        alignTopLeftAnchor(topLeftAnchor);
        alignTopRightAnchor(topRightAnchor);
        alignBottomLeftAnchor(bottomLeftAnchor);
        alignBottomRightAnchor(bottomRightAnchor);

        alignTopAnchor(bar);

        fullBody(container);

    }
}
