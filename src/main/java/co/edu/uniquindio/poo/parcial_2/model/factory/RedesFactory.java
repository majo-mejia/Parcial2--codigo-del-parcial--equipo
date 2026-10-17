package co.edu.uniquindio.poo.parcial_2.model.factory;

import co.edu.uniquindio.poo.parcial_2.model.componentes.InstalacionDeRedes;
import co.edu.uniquindio.poo.parcial_2.model.componentes.Servicio;
import co.edu.uniquindio.poo.parcial_2.model.enums.TipoServicio;

public class RedesFactory extends ServicioFactory {
    public RedesFactory() {
        super(TipoServicio.INSTALACION_DE_REDES);
    }

    @Override
    public Servicio crearServicio(String codigo) {
        return new InstalacionDeRedes(codigo, "Instalacion de redes",
                "Cableado y configuracion de red", 10);
    }
}
