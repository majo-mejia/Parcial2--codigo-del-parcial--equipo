package co.edu.uniquindio.poo.parcial_2.model;

public class InstalacionDeRedes extends Servicio {
    public static final double COSTO_BASE = 180000;

    private int cantidadPuntos;

    public InstalacionDeRedes(String codigo, String nombre, String descripcion, int cantidadPuntos) {
        super(codigo, nombre, descripcion, COSTO_BASE, "REDES");
        this.cantidadPuntos = cantidadPuntos;
    }

    public int getCantidadPuntos() { return cantidadPuntos; }

    @Override
    public boolean requierePresencial() {
        return true;
    }

    @Override
    public String describirCondiciones() {
        return "Instalacion presencial de " + cantidadPuntos + " puntos de red";
    }
}
