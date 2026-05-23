package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Empleado;
import co.edu.uniquindio.techparkuq.modelo.enums.AlertaClimatica;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;
import co.edu.uniquindio.techparkuq.modelo.interfaces.IGestionable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parque implements IGestionable, Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private int capacidadMaxima;
    private double ingresosDiarios;
    private List<Zona> listaZonas;
    private List<Empleado> listaEmpleados;
    private List<Visitante> visitantesActivos;
    private AlertaClimatica estadoClima;

    public Parque(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.ingresosDiarios = 0.0;
        this.listaZonas = new ArrayList<>();
        this.listaEmpleados = new ArrayList<>();
        this.visitantesActivos = new ArrayList<>();
        this.estadoClima = AlertaClimatica.NINGUNA;
    }

    public void contratarEmpleado(Empleado empleado) {
        listaEmpleados.add(empleado);
    }

    public AlertaClimatica getEstadoClima() {
        return estadoClima;
    }

    public void agregarZona(Zona zona) {
        listaZonas.add(zona);
    }

    public boolean admitirVisitante(Visitante v) {
        if (visitantesActivos.size() >= capacidadMaxima) {
            System.out.println("Aforo máximo del parque alcanzado.");
            return false;
        }
        visitantesActivos.add(v);
        System.out.println(v.getNombre() + " ha ingresado al parque.");
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Zona> getListaZonas() {
        return listaZonas;
    }

    public void setListaZonas(List<Zona> listaZonas) {
        this.listaZonas = listaZonas;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<Visitante> getListaVisitantes() {
        return visitantesActivos;
    }

    public void setListaVisitantes(List<Visitante> listaVisitantes) {
        this.visitantesActivos = listaVisitantes;
    }

    public List<Atraccion> getListaAtracciones() {
        return obtenerTodasLasAtracciones();
    }

    public double getIngresosDiarios() { return ingresosDiarios; }

    @Override
    public String toString() {
        return "Parque: " + nombre + " \n" +
                "Zonas registradas: " + listaZonas.size() + "\n" +
                "Nómina de Empleados: " + listaEmpleados.size() + "\n" +
                "Visitantes actuales: " + visitantesActivos.size();
    }

    public boolean validarAcceso(Visitante v, Atraccion a) {
        if (!a.validarRestricciones(v)) {
            System.out.println("Acceso denegado: El visitante no cumple requisitos o la atracción " + a.getNombre() + " no está activa.");
            return false;
        }
        return true;
    }

    public void registrarUsoAtraccion(Visitante v, Atraccion a) {
        if (validarAcceso(v, a)) {
            a.registrarIngresoVisitante();
            v.registrarVisita(a.getNombre());
            System.out.println("Registro exitoso: " + v.getNombre() + " ingresó a " + a.getNombre());
        }
    }

    public List<Atraccion> obtenerTodasLasAtracciones() {
        List<Atraccion> todas = new ArrayList<>();
        for (Zona z : listaZonas) {
            todas.addAll(z.getListaAtracciones());
        }
        return todas;
    }

    public Atraccion buscarAtraccionPorNombre(String nombreAtraccion) {
        for (Zona zona : listaZonas) {
            for (Atraccion atraccion : zona.getListaAtracciones()) {
                if (atraccion.getNombre().equalsIgnoreCase(nombreAtraccion)) {
                    System.out.println("Atracción encontrada en la zona: " + zona.getNombre());
                    return atraccion;
                }
            }
        }
        System.out.println("La atracción '" + nombreAtraccion + "' no se encuentra en el parque.");
        return null;
    }

    public void cambiarEstadoClima(AlertaClimatica nuevaAlerta) {
        this.estadoClima = nuevaAlerta;
        System.out.println("\nSISTEMA CLIMA: Alerta cambiada a " + nuevaAlerta);
        for (Zona zona : listaZonas) {
            for (Atraccion atr : zona.getListaAtracciones()) {
                atr.reaccionarAlClima(nuevaAlerta);
            }
        }
        if (nuevaAlerta != AlertaClimatica.NINGUNA) {
            String msg = "Alerta climática activa: " + nuevaAlerta
                    + ". Algunas atracciones pueden estar cerradas por seguridad.";
            notificarVisitantes(msg);
        } else {
            notificarVisitantes("Clima normalizado. Todas las atracciones disponibles han vuelto a operar.");
        }
    }

    public void notificarVisitantes(String mensaje) {
        for (Visitante v : visitantesActivos) {
            if (v.getTicket() != null && v.getTicket().isActivo()) {
                v.recibirNotificacion(mensaje);
                v.agregarNotificacion(mensaje);
            }
        }
    }

    public void notificarInicioShow(AtraccionShow show) {
        String msg = "¡El show '" + show.getNombre() + "' comenzará pronto! Horario: " + show.getHorarioFuncion();
        for (Visitante v : visitantesActivos) {
            if (v.getTicket() != null && v.getTicket().isActivo()) {
                boolean esFavorito = v.getListaFavoritos().contains(show);
                if (esFavorito) {
                    v.recibirNotificacion(msg);
                    v.agregarNotificacion(msg);
                }
            }
        }
    }

    @Override
    public void eliminarAtraccion(String idUnico) {
        if (idUnico == null) return;
        boolean encontrada = false;
        for (Zona zona : listaZonas) {
            for (Atraccion atr : zona.getListaAtracciones()) {
                if (atr.getIdUnico().equals(idUnico)) {
                    atr.setEstado(EstadoAtraccion.CERRADA);
                    atr.setMotivoCierre("Atracción deshabilitada por administración.");
                    encontrada = true;
                    break;
                }
            }
            if (encontrada) break;
        }
        System.out.println("LOG: Proceso de eliminación finalizado para ID: " + idUnico);
    }

    @Override
    public void crearZona(Zona zona) {
        if (zona != null) {
            listaZonas.add(zona);
            System.out.println("Zona '" + zona.getNombre() + "' creada y registrada.");
        }
    }

    @Override
    public void crearAtraccion(Atraccion atraccion, String nombreZona) {
        if (atraccion == null) {
            System.out.println("Error: Atracción nula.");
            return;
        }
        Zona zonaDestino = null;
        for (Zona z : listaZonas) {
            if (z.getNombre().equalsIgnoreCase(nombreZona)) {
                zonaDestino = z;
                break;
            }
        }
        if (zonaDestino != null) {
            zonaDestino.agregarAtraccion(atraccion);
            System.out.println("Atracción '" + atraccion.getNombre() + "' registrada con éxito en la zona: " + nombreZona);
        } else {
            System.out.println("Error: La zona '" + nombreZona + "' no existe. No se pudo asignar la atracción.");
        }
    }
}