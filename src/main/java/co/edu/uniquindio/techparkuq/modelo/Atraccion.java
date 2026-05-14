package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;
import co.edu.uniquindio.techparkuq.modelo.enums.TipoAtraccion;

public class Atraccion {
    private String nombre;
    private String descripcion;
    private int capacidadMaxima;
    private double estaturaMinima;
    private int edadMinima;
    private int contadorUso;
    private EstadoAtraccion estadoAtraccion;
    private TipoAtraccion tipoAtraccion;

    public Atraccion(String nombre, String descripcion, int capacidadMaxima, double estaturaMinima, int edadMinima, int contadorUso, EstadoAtraccion estadoAtraccion, TipoAtraccion tipoAtraccion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.estaturaMinima = estaturaMinima;
        this.edadMinima = edadMinima;
        this.contadorUso = contadorUso;
        this.estadoAtraccion = estadoAtraccion;
        this.tipoAtraccion = tipoAtraccion;
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

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public EstadoAtraccion getEstadoAtraccion() {
        return estadoAtraccion;
    }

    public void setEstadoAtraccion(EstadoAtraccion estadoAtraccion) {
        this.estadoAtraccion = estadoAtraccion;
    }

    public TipoAtraccion getTipoAtraccion() {
        return tipoAtraccion;
    }

    public void setTipoAtraccion(TipoAtraccion tipoAtraccion) {
        this.tipoAtraccion = tipoAtraccion;
    }

    public double getEstaturaMinima() {
        return estaturaMinima;
    }

    public void setEstaturaMinima(double estaturaMinima) {
        this.estaturaMinima = estaturaMinima;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public int getContadorUso() {
        return contadorUso;
    }

    public void setContadorUso(int contadorUso) {
        this.contadorUso = contadorUso;
    }

    @Override
    public String toString() {
        return "=== DETALLES DE ATRACCIÓN ===\n" +
                "Nombre: " + nombre + "\n" +
                "Tipo: " + tipoAtraccion + "\n" +
                "Estado: " + estadoAtraccion + "\n" +
                "Capacidad: " + capacidadMaxima + " personas\n" +
                "Estatura Mínima: " + estaturaMinima + "m\n" +
                "Descripción: " + descripcion;
    }
}
