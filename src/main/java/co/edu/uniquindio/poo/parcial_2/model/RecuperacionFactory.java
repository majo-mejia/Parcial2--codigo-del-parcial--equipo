package co.edu.uniquindio.poo.parcial_2.model;

public class RecuperacionFactory extends ServicioFactory {
    public RecuperacionFactory() {
        super(TipoServicio.RECUPERACION_DE_INFORMACION);
    }

    @Override
    public Servicio crearServicio(String codigo) {
        return new RecuperacionDeInformacion(codigo, "Recuperacion de informacion",
                "Rescate de datos perdidos", "Disco duro");
    }
}
