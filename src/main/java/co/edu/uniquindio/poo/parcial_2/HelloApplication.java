package co.edu.uniquindio.poo.parcial_2;

import co.edu.uniquindio.poo.parcial_2.model.componentes.LegacySupport;
import co.edu.uniquindio.poo.parcial_2.model.adapter.LegacySupportAdapter;
import co.edu.uniquindio.poo.parcial_2.model.componentes.TechSolutions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static TechSolutions sistema;

    @Override
    public void start(Stage escenario) throws IOException {
        if (sistema == null) {
            sistema = new TechSolutions(
                    "TechSolutions",
                    "900123456-7",
                    "Armenia, Quindío",
                    "6060000000",
                    new LegacySupportAdapter(new LegacySupport())
            );
        }

        FXMLLoader cargador = new FXMLLoader(
                HelloApplication.class.getResource("/co/edu/uniquindio/poo/parcial_2/hello-view.fxml")
        );
        Parent raiz = cargador.load();

        Scene escena = new Scene(raiz, 1180, 800);
        escenario.setTitle("TechSolutions - Gestión de servicios tecnológicos");
        escenario.setMinWidth(1000);
        escenario.setMinHeight(700);
        escenario.setScene(escena);
        escenario.show();
    }

    public static TechSolutions getSistema() {
        return sistema;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
