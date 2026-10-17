package co.edu.uniquindio.poo.parcial_2.model;

public class Cliente {
    private String documento;
    private String nombre;
    private String correoElectronico;

    public Cliente(String documento, String nombre, String correoElectronico) {
        this.documento = documento;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
    }

    public String getDocumento() { return documento; }

    public String getNombre() { return nombre; }

    public String getCorreoElectronico() { return correoElectronico; }

    @Override
    public String toString() {
        return documento + " - " + nombre;
    }
}
