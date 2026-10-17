package co.edu.uniquindio.poo.parcial_2.model.componentes;

public class MantenimientoCorrectivo extends Servicio {
    public static final double COSTO_BASE = 120000;

    private String tipoFalla;

    public MantenimientoCorrectivo(String codigo, String nombre, String descripcion, String tipoFalla) {
        super(codigo, nombre, descripcion, COSTO_BASE, "HARDWARE");
        this.tipoFalla = tipoFalla;
    }

    public String getTipoFalla() { return tipoFalla; }

    @Override
    public boolean requierePresencial() {
        return true;
    }

    @Override
    public String describirCondiciones() {
        return "Visita presencial para reparar: " + tipoFalla;
    }
}
