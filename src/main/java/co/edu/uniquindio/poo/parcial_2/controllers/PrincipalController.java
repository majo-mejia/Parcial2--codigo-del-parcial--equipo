package co.edu.uniquindio.poo.parcial_2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

/** Coordina la actualización de los datos compartidos al cambiar de pestaña. */
public class PrincipalController {
    @FXML private TabPane tabPane;
    @FXML private ServiciosController serviciosViewController;
    @FXML private PaquetesController paquetesViewController;
    @FXML private SolicitudesController solicitudesViewController;
    @FXML private TecnicosController tecnicosViewController;

    @FXML
    public void initialize() {
        tabPane.getSelectionModel().selectedIndexProperty().addListener(
                (observable, anterior, actual) -> actualizarPestana(actual.intValue()));
        actualizarPestana(0);
    }

    private void actualizarPestana(int indice) {
        if (indice == 0 && serviciosViewController != null) {
            serviciosViewController.refrescarDatos();
        } else if (indice == 1 && paquetesViewController != null) {
            paquetesViewController.refrescarDatos();
        } else if (indice == 2 && solicitudesViewController != null) {
            solicitudesViewController.refrescarDatos();
        } else if (indice == 3 && tecnicosViewController != null) {
            tecnicosViewController.refrescarDatos();
        }
    }
}
