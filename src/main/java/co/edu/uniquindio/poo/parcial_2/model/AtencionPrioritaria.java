package co.edu.uniquindio.poo.parcial_2.model;

public class AtencionPrioritaria extends CaracteristicaDecorador {
    private static final double INCREMENTO = 35000;

    public AtencionPrioritaria(ItemContratable item) {
        super(item, INCREMENTO, "Atencion prioritaria");
    }
}
