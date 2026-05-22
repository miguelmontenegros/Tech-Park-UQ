package co.edu.uniquindio.techparkuq.modelo;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Empleado;
import co.edu.uniquindio.techparkuq.modelo.enums.AlertaClimatica;
import co.edu.uniquindio.techparkuq.modelo.interfaces.IGestionable;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.interfaces.IMantenible;

public class Administrador extends Empleado implements IGestionable{

    private String nivelAcceso;
    private Parque parque;

    public Administrador(String nombre, String documento, int edad, String nivelAcceso, Parque parque) {
        super("Administrador", nombre, documento, edad);
        this.nivelAcceso = nivelAcceso;
        this.parque = parque;
    }

    public void asignarOperadorZona(Operador operador, Zona zona) {
        if (operador != null && zona != null) {
            operador.setZonaAsignada(zona);
            zona.getListOperadores().add(operador);
            System.out.println("LOG: Operador " + operador.getNombre() + " asignado a " + zona.getNombre());
        }
    }
    public void activarAlertaClima(AlertaClimatica alerta) {
        if (parque != null && alerta != null) {
            parque.cambiarEstadoClima(alerta);
            System.out.println("ALERTA: El administrador ha cambiado el clima a " + alerta);
        }
    }
    public Reporte generarReporte() {
        if (parque != null) {
            return new Reporte(parque.getIngresosDiarios(), 5, 2);
        }
        return null;
    }


    @Override
    public void crearAtraccion(Atraccion atraccion, String nombreZona) {
        if (atraccion != null && parque != null) {
            parque.crearAtraccion(atraccion, nombreZona);
        }
    }

    @Override
    public void eliminarAtraccion(String idUnico) {
        if (parque != null && idUnico != null) {
            parque.eliminarAtraccion(idUnico);
        }
    }

    @Override
    public void crearZona(Zona zona) {
        if (zona != null && parque != null) {
            parque.crearZona(zona);
        }
    }
    public String getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(String nivelAcceso) { this.nivelAcceso = nivelAcceso; }
    public Parque getParque() { return parque; }
    public void setParque(Parque parque) { this.parque = parque; }
}