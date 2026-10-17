package co.edu.uniquindio.poo.parcial_2.model;

public abstract class ServicioFactory {
    private TipoServicio tipo;

    public ServicioFactory(TipoServicio tipo) {
        this.tipo = tipo;
    }

    public TipoServicio getTipo() {
        return tipo;
    }

    public abstract Servicio crearServicio(String codigo);
}
