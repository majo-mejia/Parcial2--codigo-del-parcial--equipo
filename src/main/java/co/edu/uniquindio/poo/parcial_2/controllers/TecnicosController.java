package co.edu.uniquindio.poo.parcial_2.controllers;

import co.edu.uniquindio.poo.parcial_2.model.componentes.InformacionTecnico;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;
import co.edu.uniquindio.poo.parcial_2.model.componentes.SolicitudServicio;
import co.edu.uniquindio.poo.parcial_2.model.componentes.TechSolutions;
import co.edu.uniquindio.poo.parcial_2.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.List;

/** Controlador para consultar técnicos, calcular costos y confirmar solicitudes. */
public class TecnicosController {
    @FXML private ComboBox<String> cmbEspecialidad;
    @FXML private ListView<InformacionTecnico> lstConsultasTecnicos;
    @FXML private ComboBox<SolicitudServicio> cmbSolicitud;
    @FXML private Label lblCostoSolicitud;
    @FXML private TextArea txtDetalleCosto;

    private final TechSolutions sistema = HelloApplication.getSistema();

    @FXML
    public void initialize() {
        cmbEspecialidad.getItems().setAll("HARDWARE", "REDES", "DATOS");
        cmbEspecialidad.getSelectionModel().selectFirst();
        txtDetalleCosto.setEditable(false);
        cmbSolicitud.valueProperty().addListener(
                (observable, anterior, actual) -> mostrarResumenSolicitud(actual));
        refrescarDatos();
    }

    public void refrescarDatos() {
        SolicitudServicio solicitudAnterior = cmbSolicitud.getValue();
        cmbSolicitud.getItems().setAll(sistema.listarSolicitudes());
        if (solicitudAnterior != null && cmbSolicitud.getItems().contains(solicitudAnterior)) {
            cmbSolicitud.setValue(solicitudAnterior);
        } else if (!cmbSolicitud.getItems().isEmpty()) {
            cmbSolicitud.getSelectionModel().selectFirst();
        } else {
            lblCostoSolicitud.setText("Sin solicitud seleccionada");
            txtDetalleCosto.clear();
        }
    }

    @FXML
    public void consultarTecnico() {
        String especialidad = cmbEspecialidad.getValue();
        if (especialidad == null) {
            Mensajes.advertir("Consultar técnico", "Selecciona una especialidad.");
            return;
        }
        try {
            InformacionTecnico tecnico = sistema.consultarTecnico(especialidad);
            lstConsultasTecnicos.getItems().removeIf(
                    existente -> existente.getEspecialidad().equals(especialidad));
            lstConsultasTecnicos.getItems().add(tecnico);
            lstConsultasTecnicos.getSelectionModel().select(tecnico);
        } catch (RuntimeException excepcion) {
            Mensajes.error("Consulta de técnico", excepcion.getMessage());
        }
    }

    @FXML
    public void calcularCostoSolicitud() {
        SolicitudServicio solicitud = cmbSolicitud.getValue();
        if (solicitud == null) {
            Mensajes.advertir("Calcular costo", "Primero registra o selecciona una solicitud.");
            return;
        }
        try {
            double total = sistema.calcularCosto(solicitud.getCodigo());
            lblCostoSolicitud.setText("Costo total: " + Mensajes.dinero(total));
            txtDetalleCosto.setText(construirDetalle(solicitud, total, true));
        } catch (RuntimeException excepcion) {
            lblCostoSolicitud.setText("No disponible para confirmar");
            txtDetalleCosto.setText(construirDetalle(solicitud, 0, false));
            Mensajes.advertir("No se pudo calcular el costo", excepcion.getMessage()
                    + "\nLa solicitud permanece pendiente y no se ha confirmado.");
        }
    }

    @FXML
    public void confirmarSolicitud() {
        SolicitudServicio solicitud = cmbSolicitud.getValue();
        if (solicitud == null) {
            Mensajes.advertir("Confirmar solicitud", "Primero registra o selecciona una solicitud.");
            return;
        }
        try {
            sistema.confirmarSolicitud(solicitud.getCodigo());
            refrescarDatos();
            cmbSolicitud.setValue(solicitud);
            lblCostoSolicitud.setText("Costo total: " + Mensajes.dinero(solicitud.getCostoTotal()));
            txtDetalleCosto.setText(construirDetalle(solicitud, solicitud.getCostoTotal(), true));
            Mensajes.informar("Solicitud confirmada",
                    solicitud.getCodigo() + " quedó confirmada.\nCosto total: "
                            + Mensajes.dinero(solicitud.getCostoTotal()));
        } catch (RuntimeException excepcion) {
            lblCostoSolicitud.setText("No se puede confirmar");
            txtDetalleCosto.setText(construirDetalle(solicitud, 0, false));
            Mensajes.error("No se pudo confirmar la solicitud", excepcion.getMessage()
                    + "\nLa solicitud permanece pendiente.");
        }
    }

    private void mostrarResumenSolicitud(SolicitudServicio solicitud) {
        if (solicitud == null) {
            lblCostoSolicitud.setText("Sin solicitud seleccionada");
            txtDetalleCosto.clear();
            return;
        }
        if (solicitud.getEstado().toString().equals("CONFIRMADA")) {
            lblCostoSolicitud.setText("Costo total: " + Mensajes.dinero(solicitud.getCostoTotal()));
            txtDetalleCosto.setText(construirDetalle(solicitud, solicitud.getCostoTotal(), true));
        } else {
            lblCostoSolicitud.setText("Costo total: pendiente de cálculo");
            txtDetalleCosto.setText(construirDetalle(solicitud, 0, false));
        }
    }

    private String construirDetalle(SolicitudServicio solicitud, double total, boolean calculado) {
        StringBuilder detalle = new StringBuilder();
        detalle.append("Solicitud: ").append(solicitud.getCodigo()).append('\n');
        detalle.append("Cliente: ").append(solicitud.getCliente().getNombre()).append('\n');
        detalle.append("Estado: ").append(solicitud.getEstado()).append("\n\n");
        detalle.append("Servicios y paquetes:\n");
        for (ItemContratable item : solicitud.getItems()) {
            detalle.append("- ").append(item.getNombre()).append(": ")
                    .append(Mensajes.dinero(item.calcularCosto())).append('\n');
        }
        detalle.append("Subtotal de elementos: ")
                .append(Mensajes.dinero(solicitud.calcularCostoItems())).append("\n\n");
        detalle.append("Técnicos para atención presencial:\n");
        List<String> especialidades = solicitud.obtenerEspecialidadesPresenciales();
        if (especialidades.isEmpty()) {
            detalle.append("No se necesita desplazamiento presencial.\n");
        } else {
            double desplazamientos = 0;
            for (String especialidad : especialidades) {
                InformacionTecnico tecnico = sistema.consultarTecnico(especialidad);
                detalle.append("- ").append(especialidad).append(": ");
                if (tecnico.isDisponible()) {
                    detalle.append("técnico ").append(tecnico.getId())
                            .append(", desplazamiento ")
                            .append(Mensajes.dinero(tecnico.getCostoDesplazamiento())).append('\n');
                    desplazamientos += tecnico.getCostoDesplazamiento();
                } else {
                    detalle.append("sin técnico disponible; costo de desplazamiento $0\n");
                }
            }
            detalle.append("Total de desplazamientos disponibles: ")
                    .append(Mensajes.dinero(desplazamientos)).append("\n");
        }
        if (calculado) {
            detalle.append("\nCosto total de la solicitud: ").append(Mensajes.dinero(total)).append('\n');
        } else {
            detalle.append("\nEl costo final solo se calcula cuando hay técnicos disponibles para todas las especialidades presenciales.\n");
        }
        return detalle.toString();
    }
}
