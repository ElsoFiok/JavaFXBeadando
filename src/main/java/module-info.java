module com.beadando.javafxbeadando {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;  valamiert nem tetszik neki ha benne van ?????????????
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;
    opens com.beadando.javafxbeadando to javafx.fxml;
    exports com.beadando.javafxbeadando;
}