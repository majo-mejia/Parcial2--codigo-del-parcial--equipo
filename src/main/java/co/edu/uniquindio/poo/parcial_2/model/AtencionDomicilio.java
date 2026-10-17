package co.edu.uniquindio.poo.parcial_2.model;

import java.util.List;

public class AtencionDomicilio extends CaracteristicaDecorador {
    private static final double INCREMENTO = 25000;

    public AtencionDomicilio(ItemContratable item) {
        super(item, INCREMENTO, "Atencion a domicilio");
    }


    @Override
    public boolean requierePresencial() {
        return true;
    }

    @Override
    public List<String> getEspecialidadesPresenciales() {
        return envuelto.getEspecialidades();
    }
}
