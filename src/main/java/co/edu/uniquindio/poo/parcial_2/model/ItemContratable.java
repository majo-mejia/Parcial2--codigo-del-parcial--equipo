package co.edu.uniquindio.poo.parcial_2.model;

import java.util.List;


public interface ItemContratable {
    String getCodigo();
    String getNombre();
    String getDescripcion();
    double calcularCosto();
    boolean requierePresencial();
    List<String> getEspecialidades();
    List<String> getEspecialidadesPresenciales();
}
