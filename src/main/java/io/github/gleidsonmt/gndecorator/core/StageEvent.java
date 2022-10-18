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

import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/07/2020
 */
class StageEvent extends Event {

    public static final EventType<StageEvent> ANY =
            new EventType<>(Event.ANY, "ALL_STAGE_EVENT");

    static final EventType<StageEvent> MAXIMIZE = new EventType<>(ANY, "MAXIMIZE");
    static final EventType<StageEvent> RESTORE  = new EventType<>(ANY, "RESTORE");
    static final EventType<StageEvent> MINIMIZE = new EventType<>(ANY, "MINIMIZE");
    static final EventType<StageEvent> CLOSE    = new EventType<>(ANY, "CLOSE");

    private final StageState state;
    private final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

    StageEvent(EventType<? extends Event> eventType, StageState _state) {
        super(eventType);

        this.state = _state;

        if (MAXIMIZE.equals(eventType)) {
            maximizeEvent();
        } else if(RESTORE.equals(eventType)){
            restoreEvent();
        } else if(MINIMIZE.equals(eventType)){
            minimizeEvent();
        } else if(CLOSE.equals(eventType)){
            closeEvent();
        }
    }

    private void maximizeEvent(){

        state.updateNoMaximizedBounds(
                new BoundingBox(
                        state.getStage().getX(),
                        state.getStage().getY(),
                        state.getStage().getWidth(),
                        state.getStage().getHeight()
                ));

        state.getStage().setX(bounds.getMinX());
        state.getStage().setY(bounds.getMinY());
        state.getStage().setWidth(bounds.getWidth());
        state.getStage().setHeight(bounds.getHeight());

        state.getDecorator().setMaximized(true);
    }

    private void restoreEvent(){
        state.getStage().setX(state.noMaximizedBounds().getMinX());
        state.getStage().setY(state.noMaximizedBounds().getMinY());
        state.getStage().setWidth(state.noMaximizedBounds().getWidth());
        state.getStage().setHeight(state.noMaximizedBounds().getHeight());

        state.getDecorator().setMaximized(false);
    }

    private void minimizeEvent(){
        System.out.println("minimize");
    }

    private void closeEvent(){
        System.out.println("close");
    }
}
