package dev.alan20111.monitorpresionarterial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la vista desde la carpeta resources/dev.alan20111.monitorpresionarterial/views/
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("c_mainView.fxml"));

        // Validación para evitar que el programa choque en silencio si no encuentra el archivo
        if (fxmlLoader.getLocation() == null) {
            System.err.println("¡ERROR! No se encontró c_mainView.fxml en la carpeta views.");
            System.exit(1);
        }

        Scene scene = new Scene(fxmlLoader.load(), 900, 700); // Ajusta el tamaño según tu diseño

        // Carga la hoja de estilos de BootstrapFX
        scene.getStylesheets().add(org.kordamp.bootstrapfx.BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("Monitor de Presión Arterial - Examen");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}