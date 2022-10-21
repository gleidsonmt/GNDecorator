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

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/07/2020
 */
public class BottomBar extends Region implements StageBar {

    private final StageState state;

    BottomBar(StageState _state) {
        this.state = _state;
        this.getStyleClass().add("gn-bottom-bar");
        this.setId("gn-bottom-bar");
        this.setCursor(Cursor.S_RESIZE);
        this.setMinHeight(3D);
        configActions();
    }

    private void configActions(){
        this.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                state.setInitX(event.getScreenX());
                state.setInitY(event.getScreenY());
                event.consume();
            }
        });

        this.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() || (state.getInitX() == -1 && state.getInitY() == -1)) {
                return;
            }

            if (state.getStage().isFullScreen()) {
                return;
            }

            if (event.isStillSincePress()) {
                return;
            }

            state.setNewX(event.getScreenX());
            state.setNewY(event.getScreenY());

            double deltaY = state.getNewY() - state.getInitY();

            if (Cursor.S_RESIZE.equals(this.getCursor())) {
                state.setStageHeight(state.getStage().getHeight() + deltaY);
                event.consume();
            }
        });
    }

    @Override
    public void changeCursor() {
        this.setCursor(Cursor.S_RESIZE);
    }
}
