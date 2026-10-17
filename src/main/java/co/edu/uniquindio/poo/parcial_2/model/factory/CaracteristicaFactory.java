package co.edu.uniquindio.poo.parcial_2.model.factory;

import co.edu.uniquindio.poo.parcial_2.model.enums.TipoCaracteristica;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;

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
