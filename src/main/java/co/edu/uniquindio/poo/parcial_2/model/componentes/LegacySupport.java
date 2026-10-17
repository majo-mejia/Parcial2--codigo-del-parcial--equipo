package co.edu.uniquindio.poo.parcial_2.model.componentes;

/**
 * Plataforma externa simulada. NO se modifica: recibe un codigo numerico y devuelve texto.
 */
public class LegacySupport {
    public String consultar(int codigoEspecialidad) {
        if (codigoEspecialidad == 1) {
            return "TEC-105|DISPONIBLE|30000";
        }
        if (codigoEspecialidad == 2) {
            return "TEC-210|DISPONIBLE|45000";
        }
        return "SIN_TECNICO|NO_DISPONIBLE|0";
    }
}
