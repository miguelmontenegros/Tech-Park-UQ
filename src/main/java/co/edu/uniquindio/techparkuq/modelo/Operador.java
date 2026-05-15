package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.interfaces.IMantenible;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Operador extends Empleado {


    private List<Atraccion> listAtraccionesGestionadas;
    private List<RevisionTecnica> listRevisiones;


    public Operador(String nombre, String documento, int edad) {
        super("Operador", nombre, documento, edad);
        this.listAtraccionesGestionadas = new ArrayList<>();
        this.listRevisiones = new ArrayList<>();
    }








    public List<Atraccion> getListAtraccionesGestionadas() {
        return listAtraccionesGestionadas;
    }

    public void setListAtraccionesGestionadas(List<Atraccion> listAtraccionesGestionadas) {
        this.listAtraccionesGestionadas = listAtraccionesGestionadas;
    }

    public List<RevisionTecnica> getListRevisiones() {
        return listRevisiones;
    }

    public void setListRevisiones(List<RevisionTecnica> listRevisiones) {
        this.listRevisiones = listRevisiones;
    }
}