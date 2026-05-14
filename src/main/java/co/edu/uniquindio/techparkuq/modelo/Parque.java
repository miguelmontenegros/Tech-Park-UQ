package co.edu.uniquindio.techparkuq.modelo;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;
import co.edu.uniquindio.techparkuq.modelo.interfaces.IGestionable;

import java.util.ArrayList;
import java.util.List;

public class Parque implements IGestionable {
    private String nombre;
    private int capacidadMaxima;
    private double ingresosDiarios;
    private List<Zona> listaZonas;
    private List<Empleado> listaEmpleados;
    private List<Visitante> visitantesActivos;

    public Parque(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.ingresosDiarios = 0.0;
        this.listaZonas = new ArrayList<>();
        this.listaEmpleados = new ArrayList<>();
        this.visitantesActivos = new ArrayList<>();

    }

    public void contratarEmpleado(Empleado empleado) {
        listaEmpleados.add(empleado);

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

    @Override
    public String toString() {
        return "Parque: " + nombre + " \n" +
                "Zonas registradas: " + listaZonas.size() + "\n" +
                "Nómina de Empleados: " + listaEmpleados.size() + "\n" +
                "Visitantes actuales: " + visitantesActivos.size();
    }


    public boolean validarAcceso(Visitante v, Atraccion a) {
        if (a.getEstadoAtraccion().name().equals("EN_MANTENIMIENTO")) {
            System.out.println("Acceso denegado: " + a.getNombre() + " está en mantenimiento.");
            return false;
        }

        if (v.getEstatura() < a.getEstaturaMinima() || v.getEdad() < a.getEdadMinima()) {
            System.out.println("Acceso denegado: No cumple requisitos de seguridad.");
            return false;
        }

        return true;
    }

    public void registrarUsoAtraccion(Visitante v, Atraccion a) {
        if (validarAcceso(v, a)) {
            a.setContadorUso(a.getContadorUso() + 1);

            v.registrarVisita(a.getNombre());

            if (a.getContadorUso() >= 500) {
                a.setEstadoAtraccion(EstadoAtraccion.EN_MANTENIMIENTO);
                System.out.println("Alerta: " + a.getNombre() + " bloqueada por alcanzar 500 usos.");
            }
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



        @Override

        public void eliminarAtraccion(String nombreAtraccion) {

            for (Zona zona : listaZonas) {
                List<Atraccion> lista = zona.getListaAtracciones();

                for (int i = lista.size() - 1; i >= 0; i--) {

                    if (lista.get(i).getNombre().equalsIgnoreCase(nombreAtraccion)) {
                        lista.remove(i);
                        System.out.println("Atracción eliminada con éxito.");
                    }
                }
            }
        }
    @Override

    public void crearZona(Zona zona) {
        if (zona != null) {
            listaZonas.add(zona);
            System.out.println("Zona '" + zona.getNombre() + "' creada y registrada.");
        }
    }

    @Override

    public void crearAtraccion(Atraccion atraccion) {
        if (atraccion == null) {
            System.out.println(" Error: Atracción nula.");
            return;
        }

        if (listaZonas.isEmpty()) {
            System.out.println("Error: No hay zonas creadas para asignar la atracción.");
            return;
        }

        listaZonas.get(0).agregarAtraccion(atraccion);
        System.out.println("Atracción '" + atraccion.getNombre() + "' registrada con éxito.");
    }
    }
