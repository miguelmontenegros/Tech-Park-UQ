package co.edu.uniquindio.techparkuq.modelo;

import java.util.ArrayList;
import java.util.List;

public class Zona {


    private String nombre;
    private int capacidadMaxima;
    private int aforoActual;
    private List<Atraccion> listaAtracciones;


    public Zona(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.aforoActual = 0;
        this.listaAtracciones = new ArrayList<>();
    }


    public boolean verificarAforo() {
        return aforoActual < capacidadMaxima;
    }




    public void agregarAtraccion(Atraccion atraccion) {
        if (atraccion != null) {
            listaAtracciones.add(atraccion);
        }
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

    @Override
    public String toString(){
        return "Zona{" + "nombre=" + nombre + "cantidad de Atracciones: "  + listaAtracciones.size()+ "}" ;
    }
}
