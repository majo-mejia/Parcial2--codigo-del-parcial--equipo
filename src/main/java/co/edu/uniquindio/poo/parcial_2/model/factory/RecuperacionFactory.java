package co.edu.uniquindio.poo.parcial_2.model.factory;

import co.edu.uniquindio.poo.parcial_2.model.componentes.RecuperacionDeInformacion;
import co.edu.uniquindio.poo.parcial_2.model.componentes.Servicio;
import co.edu.uniquindio.poo.parcial_2.model.enums.TipoServicio;

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
