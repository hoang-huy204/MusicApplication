module com {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires org.controlsfx.controls;
    requires java.mail;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires javafx.web;
    requires com.google.gson;
    requires jlayer;
    requires java.net.http;

    opens com.controller to javafx.fxml;
    opens com.model to com.google.gson;

    exports com;
}
