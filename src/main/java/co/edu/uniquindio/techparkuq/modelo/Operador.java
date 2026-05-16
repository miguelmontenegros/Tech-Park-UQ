package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Empleado;
import co.edu.uniquindio.techparkuq.modelo.interfaces.IMantenible;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Operador extends Empleado {


    private Zona zonaAsignada;
    private List<Atraccion> listAtraccionesGestionadas;
    private List<RevisionTecnica> listRevisiones;



    public Operador(String nombre, String documento, int edad) {
        super("Operador", nombre, documento, edad);
        this.listAtraccionesGestionadas = new ArrayList<>();
        this.listRevisiones = new ArrayList<>();
    }
    public boolean validarAcceso(Visitante visitante, Atraccion atraccion) {
        if (visitante == null || atraccion == null){
            return false;
    }

        if (!listAtraccionesGestionadas.contains(atraccion)) {
        System.out.println("Denegado: El operador no tiene jurisdicción sobre " + atraccion.getNombre());
        return false;
    }


        if (atraccion.getEstado() != EstadoAtraccion.ACTIVA) {
        System.out.println("Denegado: La atracción no está ACTIVA.");
        return false;
    }


        if (visitante.getEdad() < atraccion.getEdadMinima()) {
        System.out.println("Denegado: El visitante no cumple la edad mínima.");
        return false;
    }
        if (visitante.getEstatura() < atraccion.getAlturaMinima()) {
        System.out.println("Denegado: El visitante no cumple la altura mínima.");
        return false;
    }


        System.out.println("Acceso Aprobado para " + visitante.getNombre());
        return true;
}


public void cambiarEstadoAtraccion(Atraccion a, EstadoAtraccion e) {

    if (a != null && listAtraccionesGestionadas.contains(a)) {
        a.setEstado(e);
        System.out.println("Atracción " + a.getNombre() + " cambió a estado: " + e);
    } else {
        System.out.println("Error: No se pudo cambiar el estado (Atracción no gestionada por este operador).");
    }
}


public void gestionarMantenimiento(IMantenible atraccion) {
    if (atraccion != null) {
        System.out.println("Iniciando revisión técnica...");

        atraccion.registrarRevisionTecnica();


        if (atraccion instanceof Atraccion) {
            Atraccion atr = (Atraccion) atraccion;

            RevisionTecnica nuevaRevision = new RevisionTecnica("REV-" + System.currentTimeMillis(), LocalDate.now(), "Mantenimiento preventivo completado", true);
            listRevisiones.add(nuevaRevision);

            cambiarEstadoAtraccion(atr, EstadoAtraccion.ACTIVA);
            System.out.println("Mantenimiento finalizado con éxito.");
        }
    }
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

    public void setZonaAsignada(Zona zonaAsignada) {
        this.zonaAsignada = zonaAsignada;
    }
    public Zona getZonaAsignada() { return zonaAsignada; }
}

