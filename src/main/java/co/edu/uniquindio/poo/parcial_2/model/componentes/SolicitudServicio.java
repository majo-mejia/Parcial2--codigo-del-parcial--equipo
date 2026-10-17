package co.edu.uniquindio.poo.parcial_2.model.componentes;

import co.edu.uniquindio.poo.parcial_2.model.enums.EstadoSolicitud;
import co.edu.uniquindio.poo.parcial_2.model.enums.NivelPrioridad;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ProveedorTecnicos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudServicio {
    private String codigo;
    private LocalDate fechaCreacion;
    private Cliente cliente;
    private List<ItemContratable> items;
    private double costoTotal;
    private EstadoSolicitud estado;
    private LocalDate fechaProgramada;   // opcional
    private String observaciones;        // opcional
    private NivelPrioridad prioridad;    // opcional

    /**
     * @param builder
     * Constructor privado: solo el Builder interno puede crear solicitudes
     */
    private SolicitudServicio(Builder builder) {
        this.codigo = builder.codigo;
        this.fechaCreacion = LocalDate.now();
        this.cliente = builder.cliente;
        this.items = builder.items;
        this.fechaProgramada = builder.fechaProgramada;
        this.observaciones = builder.observaciones;
        this.prioridad = builder.prioridad;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.costoTotal = calcularCostoItems();
    }

    /** suma los items (servicios, paquetes y extras) sin incluir desplazamientos. */
    public double calcularCostoItems() {
        double total = 0;
        for (ItemContratable item : items) {
            total = total + item.calcularCosto();
        }
        return total;
    }

    /** lista las especialidades presenciales sin repetir, para cobrar un solo desplazamiento por cada una. */
    public List<String> obtenerEspecialidadesPresenciales() {
        List<String> especialidades = new ArrayList<>();
        for (ItemContratable item : items) {
            for (String esp : item.getEspecialidadesPresenciales()) {
                if (!especialidades.contains(esp)) {
                    especialidades.add(esp);
                }
            }
        }
        return especialidades;
    }

    public boolean requierePresencial() {
        return !obtenerEspecialidadesPresenciales().isEmpty();
    }

    /** costo total = items + un desplazamiento por especialidad. Lanza excepcion si no hay tecnico. */
    public double calcularCosto(ProveedorTecnicos proveedor) {
        double total = calcularCostoItems();
        for (String especialidad : obtenerEspecialidadesPresenciales()) {
            InformacionTecnico tecnico = proveedor.consultarTecnico(especialidad);
            if (!tecnico.isDisponible()) {
                throw new IllegalStateException("No hay tecnico disponible para la especialidad " + especialidad);
            }
            total = total + tecnico.getCostoDesplazamiento();
        }
        return total;
    }

    public void confirmar() {
        estado = EstadoSolicitud.CONFIRMADA;
    }

    public String getCodigo() { return codigo; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }

    public Cliente getCliente() { return cliente; }

    public List<ItemContratable> getItems() { return items; }

    public double getCostoTotal() { return costoTotal; }

    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }

    public EstadoSolicitud getEstado() { return estado; }

    public LocalDate getFechaProgramada() { return fechaProgramada; }

    public String getObservaciones() { return observaciones; }

    public NivelPrioridad getPrioridad() { return prioridad; }

    @Override
    public String toString() {
        return codigo + " - " + cliente.getNombre() + " - " + estado;
    }

    // builder static
    public static class Builder {
        private String codigo;
        private Cliente cliente;
        private List<ItemContratable> items;
        private LocalDate fechaProgramada;
        private String observaciones;
        private NivelPrioridad prioridad;

        // Datos obligatorios: codigo y cliente
        public Builder(String codigo, Cliente cliente) {
            this.codigo = codigo;
            this.cliente = cliente;
            this.items = new ArrayList<>();
        }

        public Builder agregarItem(ItemContratable item) {
            items.add(item);
            return this;
        }

        public Builder conFechaProgramada(LocalDate fechaProgramada) {
            this.fechaProgramada = fechaProgramada;
            return this;
        }

        public Builder conObservaciones(String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public Builder conPrioridad(NivelPrioridad prioridad) {
            this.prioridad = prioridad;
            return this;
        }

        // aqui se valida lo obligatorio antes de construir
        public SolicitudServicio build() {
            if (codigo == null || codigo.isEmpty()) {
                throw new IllegalStateException("La solicitud necesita un codigo");
            }
            if (cliente == null) {
                throw new IllegalStateException("La solicitud necesita un cliente");
            }
            if (items.isEmpty()) {
                throw new IllegalStateException("La solicitud necesita al menos un servicio o paquete");
            }
            return new SolicitudServicio(this);
        }
    }
}
