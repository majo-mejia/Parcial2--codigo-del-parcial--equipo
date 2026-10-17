package co.edu.uniquindio.poo.parcial_2.controllers;

import co.edu.uniquindio.poo.parcial_2.HelloApplication;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;
import co.edu.uniquindio.poo.parcial_2.model.componentes.Paquete;
import co.edu.uniquindio.poo.parcial_2.model.componentes.Servicio;
import co.edu.uniquindio.poo.parcial_2.model.componentes.TechSolutions;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

/** Controlador para crear paquetes y organizar servicios o paquetes dentro de otros. */
public class PaquetesController {
    @FXML private TextField txtNombrePaquete;
    @FXML private TextField txtDescripcionPaquete;
    @FXML private ComboBox<Paquete> cmbPaquete;
    @FXML private ComboBox<ItemContratable> cmbComponente;
    @FXML private ListView<ItemContratable> lstComponentes;
    @FXML private Label lblCostoPaquete;

    private final TechSolutions sistema = HelloApplication.getSistema();

    @FXML
    public void initialize() {
        cmbPaquete.valueProperty().addListener((observable, anterior, actual) -> actualizarComponentes());
        refrescarDatos();
    }

    public void refrescarDatos() {
        Paquete paqueteAnterior = cmbPaquete.getValue();
        cmbPaquete.getItems().setAll(sistema.listarPaquetes());
        if (paqueteAnterior != null && cmbPaquete.getItems().contains(paqueteAnterior)) {
            cmbPaquete.setValue(paqueteAnterior);
        } else if (!cmbPaquete.getItems().isEmpty()) {
            cmbPaquete.getSelectionModel().selectFirst();
        }

        ItemContratable componenteAnterior = cmbComponente.getValue();
        List<ItemContratable> elementos = listarElementosDisponibles();
        cmbComponente.getItems().setAll(elementos);
        if (componenteAnterior != null && cmbComponente.getItems().contains(componenteAnterior)) {
            cmbComponente.setValue(componenteAnterior);
        } else if (!cmbComponente.getItems().isEmpty()) {
            cmbComponente.getSelectionModel().selectFirst();
        }
        actualizarComponentes();
    }

    @FXML
    public void crearPaquete() {
        String nombre = txtNombrePaquete.getText();
        if (nombre == null || nombre.trim().isEmpty()) {
            Mensajes.advertir("Crear paquete", "Escribe un nombre para el paquete.");
            return;
        }

        try {
            String descripcion = txtDescripcionPaquete.getText();
            Paquete nuevo = sistema.crearPaquete(nombre.trim(), descripcion == null ? "" : descripcion.trim());
            txtNombrePaquete.clear();
            txtDescripcionPaquete.clear();
            refrescarDatos();
            cmbPaquete.setValue(nuevo);
            Mensajes.informar("Paquete creado", "Se creó " + nuevo.getCodigo() + ". Ahora puedes agregarle componentes.");
        } catch (RuntimeException excepcion) {
            Mensajes.error("No se pudo crear el paquete", excepcion.getMessage());
        }
    }

    @FXML
    public void agregarComponente() {
        Paquete paquete = cmbPaquete.getValue();
        ItemContratable componente = cmbComponente.getValue();

        if (paquete == null) {
            Mensajes.advertir("Agregar componente", "Crea o selecciona un paquete primero.");
            return;
        }
        if (componente == null) {
            Mensajes.advertir("Agregar componente", "Selecciona un servicio o paquete para agregar.");
            return;
        }

        try {
            sistema.agregarAPaquete(paquete.getCodigo(), componente);
            actualizarComponentes();
            Mensajes.informar("Componente agregado", "El componente se agregó al paquete " + paquete.getNombre() + ".");
        } catch (RuntimeException excepcion) {
            Mensajes.error("No se pudo agregar el componente", excepcion.getMessage());
        }
    }

    private void actualizarComponentes() {
        Paquete paquete = cmbPaquete.getValue();
        if (paquete == null) {
            lstComponentes.getItems().clear();
            lblCostoPaquete.setText("$0");
            return;
        }
        lstComponentes.getItems().setAll(sistema.listarComponentes(paquete.getCodigo()));
        lblCostoPaquete.setText(Mensajes.dinero(paquete.calcularCosto()));
    }

    private List<ItemContratable> listarElementosDisponibles() {
        List<ItemContratable> elementos = new ArrayList<>();
        for (Servicio servicio : sistema.listarServicios()) {
            elementos.add(servicio);
        }
        elementos.addAll(sistema.listarPersonalizados());
        elementos.addAll(sistema.listarPaquetes());
        return elementos;
    }
}
