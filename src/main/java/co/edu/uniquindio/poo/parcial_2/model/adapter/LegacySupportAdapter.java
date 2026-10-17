package co.edu.uniquindio.poo.parcial_2.model.adapter;


import co.edu.uniquindio.poo.parcial_2.model.componentes.InformacionTecnico;
import co.edu.uniquindio.poo.parcial_2.model.componentes.LegacySupport;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ProveedorTecnicos;

/**
 * Adapter: traduce nombres a codigos y el texto de LegacySupport a InformacionTecnico.
 */
public class LegacySupportAdapter implements ProveedorTecnicos {
    private LegacySupport legacy;

    public LegacySupportAdapter(LegacySupport legacy) {
        this.legacy = legacy;
    }

    @Override
    public InformacionTecnico consultarTecnico(String especialidad) {
        int codigo = convertirCodigo(especialidad);
        String respuesta = legacy.consultar(codigo);
        String[] partes = respuesta.split("\\|");
        String id = partes[0];
        boolean disponible = partes[1].equals("DISPONIBLE");
        double costo = Double.parseDouble(partes[2]);
        return new InformacionTecnico(id, especialidad, disponible, costo);
    }

    private int convertirCodigo(String especialidad) {
        if (especialidad.equals("HARDWARE")) {
            return 1;
        }
        if (especialidad.equals("REDES")) {
            return 2;
        }
        if (especialidad.equals("DATOS")) {
            return 3;
        }
        return 0;
    }
}
