

module io.github.gleidsonmt.gndecorator {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires scenicView;

    opens io.github.gleidsonmt.gndecorator to javafx.fxml;
    exports io.github.gleidsonmt.gndecorator;

}