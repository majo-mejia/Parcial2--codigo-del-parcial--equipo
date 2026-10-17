package co.edu.uniquindio.poo.parcial_2.model;

public abstract class CaracteristicaFactory {
    private TipoCaracteristica tipo;


    public CaracteristicaFactory(TipoCaracteristica tipo) {
        this.tipo = tipo;
    }

    public TipoCaracteristica getTipo() {
        return tipo;
    }

    public abstract ItemContratable crearCaracteristica(ItemContratable item);
}
