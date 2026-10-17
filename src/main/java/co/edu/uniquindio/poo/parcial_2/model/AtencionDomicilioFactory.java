package co.edu.uniquindio.poo.parcial_2.model;

public class AtencionDomicilioFactory extends CaracteristicaFactory {
    public AtencionDomicilioFactory() {
        super(TipoCaracteristica.ATENCION_DOMICILIO);
    }

    @Override
    public ItemContratable crearCaracteristica(ItemContratable item) {
        return new AtencionDomicilio(item);
    }
}
