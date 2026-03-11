module dev.alan20111.monitorpresionarterial {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens dev.alan20111.monitorpresionarterial to javafx.fxml;

    opens dev.alan20111.monitorpresionarterial.controllers to javafx.fxml;

    opens dev.alan20111.monitorpresionarterial.models to javafx.base;

    exports dev.alan20111.monitorpresionarterial;
    exports dev.alan20111.monitorpresionarterial.controllers;
}