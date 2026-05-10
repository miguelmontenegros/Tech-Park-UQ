package co.edu.uniquindio.techparkuq.modelo;
import java.util.ArrayList;
import java.util.List;

public class Parque {
    private String nombre;
    private List<Zona> listaZonas;
    private List<Empleado> listaEmpleados;
    private List<Visitante> listaVisitantes;

    public Parque(String nombre) {
        this.nombre = nombre;
        this.listaZonas = new ArrayList<>();
        this.listaEmpleados = new ArrayList<>();
        this.listaVisitantes = new ArrayList<>();

    }

    public void agregarEmpleado(Empleado empleado) {
        listaEmpleados.add(empleado);

    }

    public void agregarZona(Zona zona) {
        listaZonas.add(zona);
    }

    public void agregarVisitante(Visitante visitante) {
        listaVisitantes.add(visitante);
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
        return listaVisitantes;
    }

    public void setListaVisitantes(List<Visitante> listaVisitantes) {
        this.listaVisitantes = listaVisitantes;
    }

    @Override
    public String toString() {
        return "Parque: " + nombre + " \n" +
                "Zonas registradas: " + listaZonas.size() + "\n" +
                "Nómina de Empleados: " + listaEmpleados.size() + "\n" +
                "Visitantes actuales: " + listaVisitantes.size();
    }

    public Atraccion buscarAtraccionPorNombre(String nombreAtraccion) {


        for (Zona zona : listaZonas) {


            for (Atraccion atraccion : zona.getListaAtracciones()) {


                if (atraccion.getNombre().equalsIgnoreCase(nombreAtraccion)) {
                    System.out.println("📍 Atracción encontrada en la zona: " + zona.getNombre());
                    return atraccion;
                }
            }
        }

        System.out.println("⚠️ La atracción '" + nombreAtraccion + "' no se encuentra en el parque.");
        return null;
    }
}