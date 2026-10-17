package co.edu.uniquindio.poo.parcial_2.controllers;

import co.edu.uniquindio.poo.parcial_2.HelloApplication;
import co.edu.uniquindio.poo.parcial_2.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

/** Controlador para consultar, crear y personalizar servicios tecnológicos. */
public class ServiciosController {
    @FXML private ComboBox<TipoServicio> cmbTipoServicio;
    @FXML private ListView<Servicio> lstCatalogo;
    @FXML private ComboBox<Servicio> cmbServicio;
    @FXML private CheckBox chkPrioritaria;
    @FXML private CheckBox chkSoporte;
    @FXML private CheckBox chkDomicilio;
    @FXML private Label lblCostoBase;
    @FXML private Label lblIncrementos;
    @FXML private Label lblCostoFinal;
    @FXML private ListView<ItemContratable> lstPersonalizados;
    @FXML private TextArea txtDetalleServicio;

    private final TechSolutions sistema = HelloApplication.getSistema();

    @FXML
    public void initialize() {
        cmbTipoServicio.getItems().setAll(TipoServicio.values());
        cmbTipoServicio.setConverter(new StringConverter<TipoServicio>() {
            @Override
            public String toString(TipoServicio tipo) {
                return nombreTipo(tipo);
            }

            @Override
            public TipoServicio fromString(String texto) {
                return null;
            }
        });
        cmbTipoServicio.getSelectionModel().selectFirst();
        txtDetalleServicio.setEditable(false);

        cmbServicio.valueProperty().addListener((observable, anterior, actual) -> actualizarCalculo());
        lstCatalogo.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, actual) -> mostrarDetalleServicio(actual));
        refrescarDatos();
    }

    /** Actualiza las listas cuando se crean servicios o personalizaciones en otras pestañas. */
    public void refrescarDatos() {
        Servicio servicioAnterior = cmbServicio.getValue();
        lstCatalogo.getItems().setAll(sistema.listarServicios());
        cmbServicio.getItems().setAll(sistema.listarServicios());

        if (servicioAnterior != null && cmbServicio.getItems().contains(servicioAnterior)) {
            cmbServicio.setValue(servicioAnterior);
        } else if (!cmbServicio.getItems().isEmpty()) {
            cmbServicio.getSelectionModel().selectFirst();
        }

        lstPersonalizados.getItems().setAll(sistema.listarPersonalizados());
        actualizarCalculo();
    }

    @FXML
    public void crearServicio() {
        TipoServicio tipo = cmbTipoServicio.getValue();
        if (tipo == null) {
            Mensajes.advertir("Crear servicio", "Selecciona el tipo de servicio que deseas crear.");
            return;
        }

        Servicio servicio = sistema.crearServicio(tipo);
        refrescarDatos();
        cmbServicio.setValue(servicio);
        lstCatalogo.getSelectionModel().select(servicio);
        Mensajes.informar("Servicio creado", "Se creó el servicio " + servicio.getCodigo() + " correctamente.");
    }

    @FXML
    public void actualizarCalculo() {
        Servicio servicio = cmbServicio.getValue();
        if (servicio == null) {
            lblCostoBase.setText("$0");
            lblIncrementos.setText("$0");
            lblCostoFinal.setText("$0");
            return;
        }

        ItemContratable personalizado = construirPersonalizado(servicio);
        lblCostoBase.setText(Mensajes.dinero(servicio.getCostoBase()));
        lblIncrementos.setText(Mensajes.dinero(personalizado.calcularCosto() - servicio.getCostoBase()));
        lblCostoFinal.setText(Mensajes.dinero(personalizado.calcularCosto()));
    }

    @FXML
    public void guardarPersonalizado() {
        Servicio servicio = cmbServicio.getValue();
        if (servicio == null) {
            Mensajes.advertir("Personalizar servicio", "Selecciona primero un servicio del catálogo.");
            return;
        }
        if (!chkPrioritaria.isSelected() && !chkSoporte.isSelected() && !chkDomicilio.isSelected()) {
            Mensajes.advertir("Personalizar servicio", "Marca al menos una característica complementaria.");
            return;
        }

        ItemContratable personalizado = construirPersonalizado(servicio);
        sistema.guardarPersonalizado(personalizado);
        lstPersonalizados.getItems().setAll(sistema.listarPersonalizados());
        Mensajes.informar("Personalización guardada",
                "El servicio personalizado quedó disponible para paquetes y solicitudes.\nCosto: "
                        + Mensajes.dinero(personalizado.calcularCosto()));
    }

    @FXML
    public void limpiarCaracteristicas() {
        chkPrioritaria.setSelected(false);
        chkSoporte.setSelected(false);
        chkDomicilio.setSelected(false);
        actualizarCalculo();
    }

    private ItemContratable construirPersonalizado(ItemContratable original) {
        ItemContratable resultado = original;
        if (chkPrioritaria.isSelected()) {
            resultado = sistema.aplicarCaracteristica(TipoCaracteristica.ATENCION_PRIORITARIA, resultado);
        }
        if (chkSoporte.isSelected()) {
            resultado = sistema.aplicarCaracteristica(TipoCaracteristica.SOPORTE_TECNICO, resultado);
        }
        if (chkDomicilio.isSelected()) {
            resultado = sistema.aplicarCaracteristica(TipoCaracteristica.ATENCION_DOMICILIO, resultado);
        }
        return resultado;
    }

    private void mostrarDetalleServicio(Servicio servicio) {
        if (servicio == null) {
            txtDetalleServicio.clear();
            return;
        }
        txtDetalleServicio.setText(
                "Código: " + servicio.getCodigo() + "\n"
                + "Nombre: " + servicio.getNombre() + "\n"
                + "Descripción: " + servicio.getDescripcion() + "\n"
                + "Costo base: " + Mensajes.dinero(servicio.getCostoBase()) + "\n"
                + "Especialidad: " + servicio.getEspecialidad() + "\n"
                + "Condiciones: " + servicio.describirCondiciones());
    }

    private String nombreTipo(TipoServicio tipo) {
        if (tipo == null) {
            return "";
        }
        switch (tipo) {
            case MANTENIMIENTO_PREVENTIVO:
                return "Mantenimiento preventivo";
            case MANTENIMIENTO_CORRECTIVO:
                return "Mantenimiento correctivo";
            case INSTALACION_DE_REDES:
                return "Instalación de redes";
            case RECUPERACION_DE_INFORMACION:
                return "Recuperación de información";
            default:
                return tipo.toString();
        }
    }
}
