package co.edu.uniquindio.techparkuq.modelo;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    private String nombre;
    private String descripcion;
    private List<Atraccion> listaAtracciones;

    public Zona(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        listaAtracciones = new ArrayList<>();
    }
    public void agregarAtraccion(Atraccion atraccion){
        listaAtracciones.add(atraccion);
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public List<Atraccion>getListaAtracciones(){
        return listaAtracciones;
    }

    @Override
    public String toString(){
        return "Zona{" + "nombre=" + nombre + "cantidad de Atracciones: "  + listaAtracciones.size()+ "}" ;
    }
}
