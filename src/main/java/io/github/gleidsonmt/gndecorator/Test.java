package io.github.gleidsonmt.gndecorator;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/10/2022
 */
public class Test extends Application {

    @Override
    public void start(Stage stage) {

        CustomRightBar rightBar = new CustomRightBar();
        CustomLefBar lefBar = new CustomLefBar();


        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(rightBar, lefBar);

        AnchorPane.setTopAnchor(rightBar, 0D);
        AnchorPane.setRightAnchor(rightBar, 0D);
        AnchorPane.setBottomAnchor(rightBar, 0D);

        AnchorPane.setTopAnchor(lefBar, 0D);
        AnchorPane.setLeftAnchor(lefBar, 0D);
        AnchorPane.setBottomAnchor(lefBar, 0D);

        stage.setScene(new Scene(root, 800, 600));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Subject sub = new Subject();
        sub.attach(rightBar);
        sub.attach(lefBar);

        sub.setState(22);

        rightBar.setOnMouseClicked(event -> {

            System.out.println("sub.getState() = " + sub.getState());
        });
        
        lefBar.setOnMouseClicked(event -> {
            sub.setState(sub.getState()+1);
            System.out.println("sub.getState() = " + sub.getState());
            
        });


    }

    public static void main(String[] args) {
        launch(args);
    }
}


class Subject {

    private List<Observer> observers = new ArrayList<>();
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

interface Observer extends Observable {

    void update();

}
class CustomRightBar extends VBox implements Observer {

    public CustomRightBar() {

        setStyle("-fx-background-color : red;");

        setMaxWidth(3);
        setMinWidth(3);

        setCursor(Cursor.H_RESIZE);

    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    @Override
    public void update() {
        System.out.println("updating..");
    }
}

class CustomLefBar extends VBox implements Observer {

    public CustomLefBar() {

        setStyle("-fx-background-color : red;");

        setMaxWidth(3);
        setMinWidth(3);

        setCursor(Cursor.H_RESIZE);

    }


    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    @Override
    public void update() {
        System.out.println("Updating in two..");
    }
}
