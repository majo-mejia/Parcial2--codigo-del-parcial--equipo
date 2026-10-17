package co.edu.uniquindio.poo.parcial_2.model.interfac;

import co.edu.uniquindio.poo.parcial_2.model.componentes.InformacionTecnico;

/**
 * Contrato que conoce el sistema. Nadie depende de LegacySupport directamente.
 */
public interface ProveedorTecnicos {
    InformacionTecnico consultarTecnico(String especialidad);
}
