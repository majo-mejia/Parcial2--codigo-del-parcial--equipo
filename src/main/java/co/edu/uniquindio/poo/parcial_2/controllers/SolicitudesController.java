package co.edu.uniquindio.poo.parcial_2.controllers;

import co.edu.uniquindio.poo.parcial_2.HelloApplication;
import co.edu.uniquindio.poo.parcial_2.model.componentes.*;
import co.edu.uniquindio.poo.parcial_2.model.enums.NivelPrioridad;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Controlador para registrar solicitudes con datos obligatorios y opcionales. */
public class SolicitudesController {
    @FXML private ComboBox<Cliente> cmbCliente;
    @FXML private ComboBox<ItemContratable> cmbElementoDisponible;
    @FXML private ListView<ItemContratable> lstItemsBorrador;
    @FXML private DatePicker dpFechaProgramada;
    @FXML private TextArea txtObservaciones;
    @FXML private ComboBox<NivelPrioridad> cmbPrioridad;
    @FXML private ListView<SolicitudServicio> lstSolicitudes;
    @FXML private TextArea txtDetalleSolicitud;

    private final TechSolutions sistema = HelloApplication.getSistema();
    private final List<ItemContratable> itemsBorrador = new ArrayList<>();

    @FXML
    public void initialize() {
        cmbPrioridad.getItems().setAll(NivelPrioridad.values());
        txtDetalleSolicitud.setEditable(false);
        lstSolicitudes.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, actual) -> mostrarDetalleSolicitud(actual));
        refrescarDatos();
    }

    /** Mantiene el borrador actual mientras actualiza los elementos disponibles. */
    public void refrescarDatos() {
        Cliente clienteAnterior = cmbCliente.getValue();
        cmbCliente.getItems().setAll(sistema.listarClientes());
        if (clienteAnterior != null && cmbCliente.getItems().contains(clienteAnterior)) {
            cmbCliente.setValue(clienteAnterior);
        } else if (!cmbCliente.getItems().isEmpty()) {
            cmbCliente.getSelectionModel().selectFirst();
        }

        ItemContratable elementoAnterior = cmbElementoDisponible.getValue();
        List<ItemContratable> disponibles = listarElementosDisponibles();
        cmbElementoDisponible.getItems().setAll(disponibles);
        if (elementoAnterior != null && cmbElementoDisponible.getItems().contains(elementoAnterior)) {
            cmbElementoDisponible.setValue(elementoAnterior);
        } else if (!cmbElementoDisponible.getItems().isEmpty()) {
            cmbElementoDisponible.getSelectionModel().selectFirst();
        }

        lstItemsBorrador.getItems().setAll(itemsBorrador);

        SolicitudServicio solicitudAnterior = lstSolicitudes.getSelectionModel().getSelectedItem();
        lstSolicitudes.getItems().setAll(sistema.listarSolicitudes());
        if (solicitudAnterior != null && lstSolicitudes.getItems().contains(solicitudAnterior)) {
            lstSolicitudes.getSelectionModel().select(solicitudAnterior);
        } else if (!lstSolicitudes.getItems().isEmpty()) {
            lstSolicitudes.getSelectionModel().selectFirst();
        } else {
            txtDetalleSolicitud.clear();
        }
    }

    @FXML
    public void agregarElemento() {
        ItemContratable elemento = cmbElementoDisponible.getValue();
        if (elemento == null) {
            Mensajes.advertir("Agregar elemento", "Selecciona un servicio, personalizado o paquete.");
            return;
        }
        if (itemsBorrador.contains(elemento)) {
            Mensajes.advertir("Elemento repetido", "Ese elemento ya está en el borrador de la solicitud.");
            return;
        }
        itemsBorrador.add(elemento);
        lstItemsBorrador.getItems().setAll(itemsBorrador);
    }

    @FXML
    public void quitarElemento() {
        ItemContratable seleccionado = lstItemsBorrador.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Mensajes.advertir("Quitar elemento", "Selecciona un elemento del borrador.");
            return;
        }
        itemsBorrador.remove(seleccionado);
        lstItemsBorrador.getItems().setAll(itemsBorrador);
    }

    @FXML
    public void registrarSolicitud() {
        Cliente cliente = cmbCliente.getValue();
        if (cliente == null) {
            Mensajes.advertir("Registrar solicitud", "Selecciona un cliente.");
            return;
        }
        if (itemsBorrador.isEmpty()) {
            Mensajes.advertir("Registrar solicitud", "Agrega al menos un servicio o paquete.");
            return;
        }

        try {
            String codigo = sistema.generarCodigoSolicitud();
            SolicitudServicio.Builder builder = new SolicitudServicio.Builder(codigo, cliente);
            for (ItemContratable item : itemsBorrador) {
                builder.agregarItem(item);
            }

            LocalDate fecha = dpFechaProgramada.getValue();
            if (fecha != null) {
                builder.conFechaProgramada(fecha);
            }

            String observaciones = txtObservaciones.getText();
            if (observaciones != null && !observaciones.trim().isEmpty()) {
                builder.conObservaciones(observaciones.trim());
            }

            NivelPrioridad prioridad = cmbPrioridad.getValue();
            if (prioridad != null) {
                builder.conPrioridad(prioridad);
            }

            SolicitudServicio solicitud = builder.build();
            sistema.registrarSolicitud(solicitud);

            itemsBorrador.clear();
            lstItemsBorrador.getItems().clear();
            dpFechaProgramada.setValue(null);
            txtObservaciones.clear();
            cmbPrioridad.getSelectionModel().clearSelection();
            refrescarDatos();
            lstSolicitudes.getSelectionModel().select(solicitud);

            Mensajes.informar("Solicitud registrada",
                    "Se registró " + solicitud.getCodigo() + ".\nSubtotal de servicios y paquetes: "
                            + Mensajes.dinero(solicitud.calcularCostoItems())
                            + "\nEl costo de desplazamiento se calcula en la pestaña Técnicos.");
        } catch (RuntimeException excepcion) {
            Mensajes.error("No se pudo registrar la solicitud", excepcion.getMessage());
        }
    }

    private void mostrarDetalleSolicitud(SolicitudServicio solicitud) {
        if (solicitud == null) {
            txtDetalleSolicitud.clear();
            return;
        }
        StringBuilder detalle = new StringBuilder();
        detalle.append("Código: ").append(solicitud.getCodigo()).append('\n');
        detalle.append("Fecha de creación: ").append(solicitud.getFechaCreacion()).append('\n');
        detalle.append("Cliente: ").append(solicitud.getCliente().getNombre()).append('\n');
        detalle.append("Documento: ").append(solicitud.getCliente().getDocumento()).append('\n');
        detalle.append("Correo: ").append(solicitud.getCliente().getCorreoElectronico()).append('\n');
        detalle.append("Estado: ").append(solicitud.getEstado()).append('\n');
        if (solicitud.getFechaProgramada() != null) {
            detalle.append("Fecha programada: ").append(solicitud.getFechaProgramada()).append('\n');
        }
        if (solicitud.getPrioridad() != null) {
            detalle.append("Prioridad: ").append(solicitud.getPrioridad()).append('\n');
        }
        if (solicitud.getObservaciones() != null && !solicitud.getObservaciones().isEmpty()) {
            detalle.append("Observaciones: ").append(solicitud.getObservaciones()).append('\n');
        }
        detalle.append("\nElementos contratados:\n");
        for (ItemContratable item : solicitud.getItems()) {
            detalle.append("- ").append(item.getNombre()).append(" ( ")
                    .append(Mensajes.dinero(item.calcularCosto())).append(" )\n");
        }
        detalle.append("\nSubtotal sin desplazamientos: ")
                .append(Mensajes.dinero(solicitud.calcularCostoItems())).append('\n');
        detalle.append("Para verificar técnicos y calcular el costo total, abre la pestaña Técnicos.");
        txtDetalleSolicitud.setText(detalle.toString());
    }

    private List<ItemContratable> listarElementosDisponibles() {
        List<ItemContratable> elementos = new ArrayList<>();
        for (Servicio servicio : sistema.listarServicios()) {
            elementos.add(servicio);
        }
        elementos.addAll(sistema.listarPersonalizados());
        for (Paquete paquete : sistema.listarPaquetes()) {
            elementos.add(paquete);
        }
        return elementos;
    }
}
