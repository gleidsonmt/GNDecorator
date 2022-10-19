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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/07/2020
 */
class Maximize extends Button {

    private static final PseudoClass RESTORE_PSEUDO_CLASS
            = PseudoClass.getPseudoClass("restore");
    private final BooleanProperty restore = new BooleanPropertyBase(false) {
        public void invalidated() {
            pseudoClassStateChanged(RESTORE_PSEUDO_CLASS, get());
        }

        @Override public Object getBean() {
            return Maximize.this;
        }

        @Override public String getName() {
            return "restore";
        }
    };

    void setRestore(boolean restore) {
        this.restore.set(restore);
    }

    private boolean isRestore() {
        return this.restore.get();
    }

    Maximize(StageState state) {

        this.setText("[ ]");
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        if(state.getDecorator().isMaximized()) this.setId("gn-restore");
        else this.setId("gn-maximize");


        this.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

            if(!state.getDecorator().isMaximized()) {
                this.fireEvent(new StageEvent(StageEvent.MAXIMIZE, state));
                setRestore(true);
            } else {
                setRestore(false);
                this.fireEvent(new StageEvent(StageEvent.RESTORE, state));
            }
        });

        state.getDecorator().maximizedProperty().addListener((observable, oldValue, newValue) -> {
            setRestore(newValue);
        });

    }
}
