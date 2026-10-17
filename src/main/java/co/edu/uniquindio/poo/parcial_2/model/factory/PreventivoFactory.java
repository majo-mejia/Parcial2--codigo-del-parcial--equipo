package co.edu.uniquindio.poo.parcial_2.model.factory;

import co.edu.uniquindio.poo.parcial_2.model.componentes.MantenimientoPreventivo;
import co.edu.uniquindio.poo.parcial_2.model.componentes.Servicio;
import co.edu.uniquindio.poo.parcial_2.model.enums.TipoServicio;

public class PreventivoFactory extends ServicioFactory {
    public PreventivoFactory() {
        super(TipoServicio.MANTENIMIENTO_PREVENTIVO);
    }

    @Override
    public Servicio crearServicio(String codigo) {
        return new MantenimientoPreventivo(codigo, "Mantenimiento preventivo",
                "Revision y limpieza periodica de equipos", 6);
    }
}
