package co.edu.uniquindio.poo.parcial_2.model.componentes;

import co.edu.uniquindio.poo.parcial_2.model.enums.TipoCaracteristica;
import co.edu.uniquindio.poo.parcial_2.model.enums.TipoServicio;
import co.edu.uniquindio.poo.parcial_2.model.factory.*;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ProveedorTecnicos;

import java.util.ArrayList;
import java.util.List;


public class TechSolutions {
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;

    private CatalogoServicios catalogo;
    private List<Cliente> clientes;
    private List<Paquete> paquetes;
    private List<ItemContratable> personalizados;
    private List<SolicitudServicio> solicitudes;
    private List<ServicioFactory> fabricasServicios;
    private List<CaracteristicaFactory> fabricasCaracteristicas;
    private ProveedorTecnicos proveedor;

    private int contadorServicios;
    private int contadorPaquetes;
    private int contadorSolicitudes;

    public TechSolutions(String nombre, String nit, String direccion, String telefono, ProveedorTecnicos proveedor) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.proveedor = proveedor;

        this.catalogo = CatalogoServicios.getInstancia();
        this.clientes = new ArrayList<>();
        this.paquetes = new ArrayList<>();
        this.personalizados = new ArrayList<>();
        this.solicitudes = new ArrayList<>();
        this.fabricasServicios = new ArrayList<>();
        this.fabricasCaracteristicas = new ArrayList<>();

        cargarFabricas();
        cargarClientes();
        cargarServiciosIniciales();
    }

    private void cargarFabricas() {
        fabricasServicios.add(new PreventivoFactory());
        fabricasServicios.add(new CorrectivoFactory());
        fabricasServicios.add(new RedesFactory());
        fabricasServicios.add(new RecuperacionFactory());

        fabricasCaracteristicas.add(new AtencionPrioritariaFactory());
        fabricasCaracteristicas.add(new SoporteTecnicoFactory());
        fabricasCaracteristicas.add(new AtencionDomicilioFactory());
    }

    private void cargarClientes() {
        clientes.add(new Cliente("1001", "Laura Gomez", "laura@correo.com"));
        clientes.add(new Cliente("1002", "Carlos Ruiz", "carlos@correo.com"));
        clientes.add(new Cliente("1003", "Empresa Andes SAS", "contacto@andes.com"));
    }

    private void cargarServiciosIniciales() {
        for (TipoServicio tipo : TipoServicio.values()) {
            crearServicio(tipo);
        }
    }

    // servicios
    public List<Servicio> listarServicios() {
        return catalogo.listar();
    }

    public Servicio crearServicio(TipoServicio tipo) {
        for (ServicioFactory fabrica : fabricasServicios) {
            if (fabrica.getTipo() == tipo) {
                contadorServicios++;
                String codigo = "SRV-" + String.format("%02d", contadorServicios);
                Servicio servicio = fabrica.crearServicio(codigo);
                catalogo.agregarServicio(servicio);
                return servicio;
            }
        }
        throw new IllegalArgumentException("Tipo de servicio desconocido");
    }

    public Servicio buscarServicio(String codigo) {
        return catalogo.buscarPorCodigo(codigo);
    }

    public ItemContratable aplicarCaracteristica(TipoCaracteristica tipo, ItemContratable item) {
        for (CaracteristicaFactory fabrica : fabricasCaracteristicas) {
            if (fabrica.getTipo() == tipo) {
                return fabrica.crearCaracteristica(item);
            }
        }
        throw new IllegalArgumentException("Caracteristica desconocida");
    }

    /** Guarda un servicio personalizado (con extras) para poder usarlo en paquetes y solicitudes. */
    public void guardarPersonalizado(ItemContratable item) {
        personalizados.add(item);
    }

    public List<ItemContratable> listarPersonalizados() {
        return personalizados;
    }

    // paquetes
    public Paquete crearPaquete(String nombrePaquete, String descripcion) {
        if (nombrePaquete == null || nombrePaquete.trim().isEmpty()) {
            throw new IllegalArgumentException("El paquete necesita un nombre");
        }
        contadorPaquetes++;
        String codigo = "PAQ-" + String.format("%02d", contadorPaquetes);
        Paquete paquete = new Paquete(codigo, nombrePaquete, descripcion);
        paquetes.add(paquete);
        return paquete;
    }

    public Paquete buscarPaquete(String codigo) {
        for (Paquete paquete : paquetes) {
            if (paquete.getCodigo().equals(codigo)) {
                return paquete;
            }
        }
        return null;
    }

    public void agregarAPaquete(String codigoPaquete, ItemContratable item) {
        Paquete paquete = buscarPaquete(codigoPaquete);
        if (paquete == null) {
            throw new IllegalArgumentException("El paquete no existe");
        }
        paquete.agregar(item); // aqui se valida que no se contenga a si mismo
    }

    public List<ItemContratable> listarComponentes(String codigoPaquete) {
        Paquete paquete = buscarPaquete(codigoPaquete);
        if (paquete == null) {
            throw new IllegalArgumentException("El paquete no existe");
        }
        return paquete.getComponentes();
    }

    public List<Paquete> listarPaquetes() {
        return paquetes;
    }

    // clientes
    public List<Cliente> listarClientes() {
        return clientes;
    }

    public Cliente buscarCliente(String documento) {
        for (Cliente cliente : clientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null;
    }

    // solicitudes
    public String generarCodigoSolicitud() {
        contadorSolicitudes++;
        return "SOL-" + String.format("%04d", contadorSolicitudes);
    }

    public void registrarSolicitud(SolicitudServicio solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }
        if (consultarSolicitud(solicitud.getCodigo()) != null) {
            throw new IllegalStateException("Ya existe una solicitud con ese codigo");
        }
        solicitudes.add(solicitud);
    }

    public SolicitudServicio consultarSolicitud(String codigo) {
        for (SolicitudServicio solicitud : solicitudes) {
            if (solicitud.getCodigo().equals(codigo)) {
                return solicitud;
            }
        }
        return null;
    }

    public List<SolicitudServicio> listarSolicitudes() {
        return solicitudes;
    }

    public void confirmarSolicitud(String codigo) {
        SolicitudServicio solicitud = consultarSolicitud(codigo);
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no existe");
        }
        // Si no hay tecnico, calcularCosto lanza excepcion y no se confirma
        double total = solicitud.calcularCosto(proveedor);
        solicitud.setCostoTotal(total);
        solicitud.confirmar();
    }

    // tecnicos y costo
    public InformacionTecnico consultarTecnico(String especialidad) {
        return proveedor.consultarTecnico(especialidad);
    }

    public double calcularCosto(String codigoSolicitud) {
        SolicitudServicio solicitud = consultarSolicitud(codigoSolicitud);
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no existe");
        }
        double total = solicitud.calcularCosto(proveedor);
        solicitud.setCostoTotal(total);
        return total;
    }

    public String getNombre() { return nombre; }

    public String getNit() { return nit; }

    public String getDireccion() { return direccion; }

    public String getTelefono() { return telefono; }
}
