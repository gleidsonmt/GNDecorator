

module io.github.gleidsonmt.gndecorator {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.jetbrains.annotations;
    requires scenicView;

    opens io.github.gleidsonmt.gndecorator to javafx.fxml;
    exports io.github.gleidsonmt.gndecorator;
    exports io.github.gleidsonmt.gndecorator.core;
    opens io.github.gleidsonmt.gndecorator.core to javafx.fxml;

}