package co.edu.uniquindio.poo.parcial_2.model.factory;

import co.edu.uniquindio.poo.parcial_2.model.componentes.SoporteTecnico;
import co.edu.uniquindio.poo.parcial_2.model.enums.TipoCaracteristica;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;

public class SoporteTecnicoFactory extends CaracteristicaFactory {
    public SoporteTecnicoFactory() {
        super(TipoCaracteristica.SOPORTE_TECNICO);
    }

    @Override
    public ItemContratable crearCaracteristica(ItemContratable item) {
        return new SoporteTecnico(item);
    }
}
