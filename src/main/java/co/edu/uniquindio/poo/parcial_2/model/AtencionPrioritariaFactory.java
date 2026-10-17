package co.edu.uniquindio.poo.parcial_2.model;

public class AtencionPrioritariaFactory extends CaracteristicaFactory {
    public AtencionPrioritariaFactory() {
        super(TipoCaracteristica.ATENCION_PRIORITARIA);
    }

    @Override
    public ItemContratable crearCaracteristica(ItemContratable item) {
        return new AtencionPrioritaria(item);
    }
}
