package co.edu.uniquindio.poo.parcial_2.model.factory;

import co.edu.uniquindio.poo.parcial_2.model.componentes.AtencionDomicilio;
import co.edu.uniquindio.poo.parcial_2.model.enums.TipoCaracteristica;
import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;

public class AtencionDomicilioFactory extends CaracteristicaFactory {
    public AtencionDomicilioFactory() {
        super(TipoCaracteristica.ATENCION_DOMICILIO);
    }

    @Override
    public ItemContratable crearCaracteristica(ItemContratable item) {
        return new AtencionDomicilio(item);
    }
}
