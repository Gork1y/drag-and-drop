module com.edencoding {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.jfoenix;

    opens com.edencoding.controllers to javafx.fxml, javafx.base;
    exports com.edencoding;
}