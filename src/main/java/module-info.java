module com.beadando.oanda {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.beadando.oanda to javafx.fxml;
    exports com.beadando.oanda;
}