package co.edu.uniquindio.poo.parcial_2.model;

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
