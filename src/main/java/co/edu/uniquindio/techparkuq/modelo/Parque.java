package co.edu.uniquindio.techparkuq.modelo;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;

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
    /**
     * Valida si un visitante cumple los requisitos para entrar a una atracción.
     * @param visitante El visitante que intenta ingresar.
     * @param atraccion La atracción a la cual desea acceder.
     * @return true si la atracción está activa y el visitante tiene la estatura mínima; false de lo contrario.
     */

    public boolean validarAcceso(Visitante visitante, Atraccion atraccion){
        if(atraccion.getEstadoAtraccion().name().equals("EN_MANTENIMIENTO")){
            System.out.println("ACCESO DENEGADO: "+ atraccion.getNombre() + " está en mantenimento.");
            return false;
        }
        if (visitante.getEstatura()<atraccion.getEstaturaMinima()) {
            System.out.println("ACCESO DENEGADO: " + visitante.getNombre() + " no cumple con la estatura mínima (" + atraccion.getEstaturaMinima() + " m).");
            return false;
        }
        if (visitante.getEdad()<atraccion.getEdadMinima()) {
            System.out.println("ACCESO DENEGADO: Edad mínima requerida: " + atraccion.getEdadMinima() + " años.");
            return false;
        }

        if (listaVisitantes.size() >= 100){
            System.out.println("ACCESO DENEGADO: El parque alcanzó su capacidad máxima.");
            return false;
        }

        System.out.println("ACCESO CONCEDIDO: " + visitante.getNombre() + " ya puede disfrutar de " + atraccion.getNombre());
        return true;
    }
    /**
     * Registra el ingreso de un visitante al parque si supera la validación de seguridad.
     * @param visitante El visitante que ingresa.
     * @param atraccion La atracción a la que se dirige.
     */
    public void registrarIngreso(Visitante visitante, Atraccion atraccion) {
        if (validarAcceso(visitante, atraccion)) {
            listaVisitantes.add(visitante);

            atraccion.setContadorUso(atraccion.getContadorUso() + 1);

            if (atraccion.getContadorUso() >= 500){
                atraccion.setEstadoAtraccion(EstadoAtraccion.EN_MANTENIMIENTO);
                System.out.println("ALERTA: " + atraccion.getNombre() + " bloqueada automáticamente por alcanzar 500 usos.");
            }
            System.out.println("REGISTRO: " + visitante.getNombre() + " ha sido registrado en el sistema.");
        } else {
            System.out.println("REGISTRO FALLIDO: El visitante no cumple los requisitos de seguridad.");
        }
    }

}