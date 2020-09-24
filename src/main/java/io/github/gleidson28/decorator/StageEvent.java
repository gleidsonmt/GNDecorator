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
package io.github.gleidson28.decorator;

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

    private final GNDecorator decorator;

    private final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

    StageEvent(EventType<? extends Event> eventType,GNDecorator decorator) {
        super(eventType);

        this.decorator = decorator;

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

        decorator.noMaximizedBounds =
                new BoundingBox(
                        decorator.stage.getX(),
                        decorator.stage.getY(),
                        decorator.stage.getWidth(),
                        decorator.stage.getHeight()
                );

        decorator.stage.setX(bounds.getMinX());
        decorator.stage.setY(bounds.getMinY());
        decorator.stage.setWidth(bounds.getWidth());
        decorator.stage.setHeight(bounds.getHeight());

        decorator.setMaximized(true);
    }

    private void restoreEvent(){
        decorator.stage.setX(decorator.noMaximizedBounds.getMinX());
        decorator.stage.setY(decorator.noMaximizedBounds.getMinY());
        decorator.stage.setWidth(decorator.noMaximizedBounds.getWidth());
        decorator.stage.setHeight(decorator.noMaximizedBounds.getHeight());

        decorator.setMaximized(false);
    }

    private void minimizeEvent(){
        System.out.println("minimize");
    }

    private void closeEvent(){
        System.out.println("close");
    }
}
