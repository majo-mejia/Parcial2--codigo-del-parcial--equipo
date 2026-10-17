package co.edu.uniquindio.poo.parcial_2.model.componentes;

import java.util.ArrayList;
import java.util.List;

public class CatalogoServicios {
    private static CatalogoServicios instancia;
    private List<Servicio> servicios;

    private CatalogoServicios() {
        servicios = new ArrayList<>();
    }

    public static CatalogoServicios getInstancia() {
        if (instancia == null) {
            instancia = new CatalogoServicios();
        }
        return instancia;
    }

    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
    }

    public List<Servicio> listar() {
        return servicios;
    }

    public Servicio buscarPorCodigo(String codigo) {
        for (Servicio servicio : servicios) {
            if (servicio.getCodigo().equals(codigo)) {
                return servicio;
            }
        }
        return null;
    }
}
