package co.edu.uniquindio.techparkuq.modelo;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;

import java.util.ArrayList;
import java.util.List;

public class Zona {


    private String nombre;
    private int capacidadMaxima;
    private int aforoActual;
    private List<Atraccion> listaAtracciones = new ArrayList<>();
    private List<Operador> listOperadores;


    public Zona(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.aforoActual = 0;
        this.listaAtracciones = new ArrayList<>();
        this.listOperadores = new ArrayList<>();
    }


    public boolean verificarAforo() {
        return aforoActual < capacidadMaxima;
    }




    public void agregarAtraccion(Atraccion atraccion) {
        if (atraccion != null) {
            listaAtracciones.add(atraccion);
        }
    }
    public Atraccion buscarAtraccion(String nombreAtraccion) {
        for (Atraccion a : listaAtracciones) {
            if (a.getNombre().equalsIgnoreCase(nombreAtraccion)) {
                return a;
            }
        }
        return null;
    }

    public boolean eliminarAtraccion(String idUnico) {
        for (Atraccion a : listaAtracciones) {
            if (a.getIdUnico().equals(idUnico)) {
                a.setActivo(false);
                a.actualizarEstado(EstadoAtraccion.CERRADA, "Atracción deshabilitada por el administrador.");
                return true;
            }
        }
        return false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getAforoActual() {
        return aforoActual;
    }

    public void setAforoActual(int aforoActual) {
        this.aforoActual = aforoActual;
    }

    public List<Atraccion> getListaAtracciones() {
        return listaAtracciones;
    }

    public List<Operador> getListOperadores() { return listOperadores; }

    @Override
    public String toString(){
        return "Zona{" + "nombre=" + nombre + "cantidad de Atracciones: "  + listaAtracciones.size()+ "}" ;
    }
}
