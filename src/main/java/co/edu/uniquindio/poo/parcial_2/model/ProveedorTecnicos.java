package co.edu.uniquindio.poo.parcial_2.model;

/**
 * Contrato que conoce el sistema. Nadie depende de LegacySupport directamente.
 */
public interface ProveedorTecnicos {
    InformacionTecnico consultarTecnico(String especialidad);
}
