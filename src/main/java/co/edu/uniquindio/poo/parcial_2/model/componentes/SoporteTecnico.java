package co.edu.uniquindio.poo.parcial_2.model.componentes;

import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;

public class SoporteTecnico extends CaracteristicaDecorador {
    private static final double INCREMENTO = 50000;

    public SoporteTecnico(ItemContratable item) {
        super(item, INCREMENTO, "Soporte tecnico 30 dias");
    }
}
