package co.edu.uniquindio.poo.parcial_2.model;

import java.util.List;

/** Envuelve un item y le suma un incremento sin modificar el original. */
public abstract class CaracteristicaDecorador implements ItemContratable {
    protected ItemContratable envuelto;
    protected double incremento;
    protected String nombreExtra;

    public CaracteristicaDecorador(ItemContratable envuelto, double incremento, String nombreExtra) {
        this.envuelto = envuelto;
        this.incremento = incremento;
        this.nombreExtra = nombreExtra;
    }

    @Override
    public double calcularCosto() {
        return envuelto.calcularCosto() + incremento;
    }

    public double getIncremento() { return incremento; }

    public String getNombreExtra() { return nombreExtra; }

    public ItemContratable getEnvuelto() { return envuelto; }

    @Override
    public String getCodigo() { return envuelto.getCodigo(); }

    @Override
    public String getNombre() { return envuelto.getNombre() + " + " + nombreExtra; }

    @Override
    public String getDescripcion() { return envuelto.getDescripcion(); }

    @Override
    public boolean requierePresencial() { return envuelto.requierePresencial(); }

    @Override
    public List<String> getEspecialidades() { return envuelto.getEspecialidades(); }

    @Override
    public List<String> getEspecialidadesPresenciales() { return envuelto.getEspecialidadesPresenciales(); }

    @Override
    public String toString() {
        return getNombre() + " ($" + calcularCosto() + ")";
    }
}
