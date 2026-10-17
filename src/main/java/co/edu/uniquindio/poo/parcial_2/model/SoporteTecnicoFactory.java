package co.edu.uniquindio.poo.parcial_2.model;

public class SoporteTecnicoFactory extends CaracteristicaFactory {
    public SoporteTecnicoFactory() {
        super(TipoCaracteristica.SOPORTE_TECNICO);
    }

    @Override
    public ItemContratable crearCaracteristica(ItemContratable item) {
        return new SoporteTecnico(item);
    }
}
