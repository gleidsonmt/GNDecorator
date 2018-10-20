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
package com.gn.decorator.component;

import com.gn.decorator.buttons.GNFullscreen;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  20/10/2018
 * Version 1.0
 */
public class FullScreen extends GNControl {
    @Override
    Node icon() {
        return null;
    }

    @Override
    Node status() {
        return null;
    }

    @Override
    Node action() {
        GNFullscreen fullscreen = new GNFullscreen();
        fullscreen.setPrefSize(30, 30);
        return fullscreen;
    }
}
