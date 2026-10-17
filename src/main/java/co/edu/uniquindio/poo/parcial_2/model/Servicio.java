package co.edu.uniquindio.poo.parcial_2.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Servicio implements ItemContratable {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double costoBase;
    private String especialidad;

    public Servicio(String codigo, String nombre, String descripcion, double costoBase, String especialidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoBase = costoBase;
        this.especialidad = especialidad;
    }

    public abstract boolean requierePresencial();

    public abstract String describirCondiciones();

    @Override
    public double calcularCosto() {
        return costoBase;
    }

    @Override
    public List<String> getEspecialidades() {
        List<String> lista = new ArrayList<>();
        lista.add(especialidad);
        return lista;
    }

    @Override
    public List<String> getEspecialidadesPresenciales() {
        List<String> lista = new ArrayList<>();
        if (requierePresencial()) {
            lista.add(especialidad);
        }
        return lista;
    }

    @Override
    public String getCodigo() { return codigo; }

    @Override
    public String getNombre() { return nombre; }

    @Override
    public String getDescripcion() { return descripcion; }

    public double getCostoBase() { return costoBase; }

    public String getEspecialidad() { return especialidad; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " ($" + costoBase + ")";
    }
}
