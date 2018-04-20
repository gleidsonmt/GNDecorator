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

package com.gn.decorator.background;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.layout.StackPane;

/**
 * @author   Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Creation  20/04/2018
 */
public class GNBackground extends StackPane {

    public GNBackground() {
        super();
        getStyleClass().add("gn-decorator");
        setAlignment(Pos.CENTER);
//        setCache(true);
        setCacheHint(CacheHint.SCALE);
    }

    
}
