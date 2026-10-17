package co.edu.uniquindio.poo.parcial_2.model;

public class RecuperacionDeInformacion extends Servicio {
    public static final double COSTO_BASE = 150000;

    private String tipoMedio;

    public RecuperacionDeInformacion(String codigo, String nombre, String descripcion, String tipoMedio) {
        super(codigo, nombre, descripcion, COSTO_BASE, "DATOS");
        this.tipoMedio = tipoMedio;
    }

    public String getTipoMedio() { return tipoMedio; }

    @Override
    public boolean requierePresencial() {
        return false;
    }

    @Override
    public String describirCondiciones() {
        return "Recuperacion remota desde " + tipoMedio;
    }
}
