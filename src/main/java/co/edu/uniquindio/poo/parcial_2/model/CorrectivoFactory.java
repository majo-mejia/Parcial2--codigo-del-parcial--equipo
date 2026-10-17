package co.edu.uniquindio.poo.parcial_2.model;

public class CorrectivoFactory extends ServicioFactory {
    public CorrectivoFactory() {
        super(TipoServicio.MANTENIMIENTO_CORRECTIVO);
    }

    @Override
    public Servicio crearServicio(String codigo) {
        return new MantenimientoCorrectivo(codigo, "Mantenimiento correctivo",
                "Reparacion de fallas en equipos", "Falla de hardware");
    }
}
