package co.edu.uniquindio.poo.parcial_2.model;

public class InformacionTecnico {
    private String id;
    private String especialidad;
    private boolean disponible;
    private double costoDesplazamiento;

    public InformacionTecnico(String id, String especialidad, boolean disponible, double costoDesplazamiento) {
        this.id = id;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.costoDesplazamiento = costoDesplazamiento;
    }

    public String getId() { return id; }

    public String getEspecialidad() { return especialidad; }

    public boolean isDisponible() { return disponible; }

    public double getCostoDesplazamiento() { return costoDesplazamiento; }

    @Override
    public String toString() {
        if (disponible) {
            return especialidad + ": tecnico " + id + " disponible, desplazamiento $" + costoDesplazamiento;
        }
        return especialidad + ": sin tecnico disponible";
    }
}
