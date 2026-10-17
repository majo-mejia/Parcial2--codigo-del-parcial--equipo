package co.edu.uniquindio.poo.parcial_2.model.factory;

import co.edu.uniquindio.poo.parcial_2.model.componentes.AtencionPrioritaria;
import co.edu.uniquindio.poo.parcial_2.model.enums.TipoCaracteristica;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;

public class AtencionPrioritariaFactory extends CaracteristicaFactory {
    public AtencionPrioritariaFactory() {
        super(TipoCaracteristica.ATENCION_PRIORITARIA);
    }

    @Override
    public ItemContratable crearCaracteristica(ItemContratable item) {
        return new AtencionPrioritaria(item);
    }
}
