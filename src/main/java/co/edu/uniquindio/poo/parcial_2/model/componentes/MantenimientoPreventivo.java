package co.edu.uniquindio.poo.parcial_2.model.componentes;

public class MantenimientoPreventivo extends Servicio {
    public static final double COSTO_BASE = 80000;

    private int frecuenciaMeses;

    public MantenimientoPreventivo(String codigo, String nombre, String descripcion, int frecuenciaMeses) {
        super(codigo, nombre, descripcion, COSTO_BASE, "HARDWARE");
        this.frecuenciaMeses = frecuenciaMeses;
    }

    public int getFrecuenciaMeses() { return frecuenciaMeses; }

    @Override
    public boolean requierePresencial() {
        return true;
    }

    @Override
    public String describirCondiciones() {
        return "Visita presencial cada " + frecuenciaMeses + " meses";
    }
}
