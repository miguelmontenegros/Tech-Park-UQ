package co.edu.uniquindio.techparkuq.modelo;

import java.util.ArrayList;

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
    public String getNombre() {}
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {}
    public void setDescripcion(String descripcion) {}
    public List<Atracciones>getListaAtracciones(){return listaAtracciones;}
    @Override
    public String toString(){
        return "Zona{" + "nombre=" + nombre + "cantidad de Atracciones: "  + listaAtracciones+ "}" ;
    }
}
