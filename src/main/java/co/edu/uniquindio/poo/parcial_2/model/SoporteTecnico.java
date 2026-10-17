package co.edu.uniquindio.poo.parcial_2.model;

public class SoporteTecnico extends CaracteristicaDecorador {
    private static final double INCREMENTO = 50000;

    public SoporteTecnico(ItemContratable item) {
        super(item, INCREMENTO, "Soporte tecnico 30 dias");
    }
}
