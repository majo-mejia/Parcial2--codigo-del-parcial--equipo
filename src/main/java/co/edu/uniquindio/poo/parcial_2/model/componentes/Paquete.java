package co.edu.uniquindio.poo.parcial_2.model.componentes;

import co.edu.uniquindio.poo.parcial_2.model.interfac.ItemContratable;

import java.util.ArrayList;
import java.util.List;

public class Paquete implements ItemContratable {
    private String codigo;
    private String nombre;
    private String descripcion;
    private List<ItemContratable> componentes;

    public Paquete(String codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.componentes = new ArrayList<>();
    }

    public void agregar(ItemContratable item) {
        if (item == null) {
            throw new IllegalArgumentException("Debe seleccionar un componente");
        }
        if (incluye(item, this)) {
            throw new IllegalArgumentException("Un paquete no puede incluirse a si mismo, ni directa ni indirectamente");
        }
        componentes.add(item);
    }

    public void remover(ItemContratable item) {
        componentes.remove(item);
    }

    /**
     * Devuelve true si el paquete buscado esta dentro de este paquete, en cualquier nivel.
     */
    public boolean contiene(Paquete buscado) {
        for (ItemContratable componente : componentes) {
            if (incluye(componente, buscado)) {
                return true;
            }
        }
        return false;
    }

    // Revisa si el item es el paquete buscado o lo contiene (atravesando los extras que lo envuelvan)
    private boolean incluye(ItemContratable item, Paquete buscado) {
        ItemContratable actual = item;
        while (actual instanceof CaracteristicaDecorador) {
            actual = ((CaracteristicaDecorador) actual).getEnvuelto();
        }
        if (actual == buscado) {
            return true;
        }
        if (actual instanceof Paquete) {
            return ((Paquete) actual).contiene(buscado);
        }
        return false;
    }

    public List<ItemContratable> getComponentes() {
        return componentes;
    }

    @Override
    public double calcularCosto() {
        double total = 0;
        for (ItemContratable componente : componentes) {
            total = total + componente.calcularCosto();
        }
        return total;
    }

    @Override
    public boolean requierePresencial() {
        for (ItemContratable componente : componentes) {
            if (componente.requierePresencial()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getEspecialidades() {
        List<String> lista = new ArrayList<>();
        for (ItemContratable componente : componentes) {
            for (String esp : componente.getEspecialidades()) {
                if (!lista.contains(esp)) {
                    lista.add(esp);
                }
            }
        }
        return lista;
    }

    @Override
    public List<String> getEspecialidadesPresenciales() {
        List<String> lista = new ArrayList<>();
        for (ItemContratable componente : componentes) {
            for (String esp : componente.getEspecialidadesPresenciales()) {
                if (!lista.contains(esp)) {
                    lista.add(esp);
                }
            }
        }
        return lista;
    }

    @Override
    public String getCodigo() { return codigo; }

    @Override
    public String getNombre() { return nombre; }

    @Override
    public String getDescripcion() { return descripcion; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " ($" + calcularCosto() + ")";
    }
}
