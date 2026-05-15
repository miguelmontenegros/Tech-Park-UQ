package co.edu.uniquindio.techparkuq.modelo;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Empleado;
import co.edu.uniquindio.techparkuq.modelo.interfaces.IGestionable;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import java.util.Iterator;

public class Administrador extends Empleado implements IGestionable {

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
    public void activarAlertaClima(TipoAlertaClimatica alerta) {
        if (parque != null && alerta != null) {
            parque.setEstadoClima(alerta);
            System.out.println("ALERTA: El administrador ha cambiado el clima a " + alerta);
        }
    }
    public Reporte generarReporte() {
        if (parque != null) {
            return new Reporte(parque.getListaAtracciones(), parque.getListaVentas());
        }
        return null;
    }


    @Override
    public void crearAtraccion(Atraccion atraccion) {
        if (atraccion != null && parque != null) {
            parque.getListaAtracciones().add(atraccion);
            System.out.println("ÉXITO: Atracción '" + atraccion.getNombre() + "' añadida al inventario.");
        }
    }

    @Override
    public void eliminarAtraccion(String nombre) {
        if (parque != null && nombre != null) {
            boolean eliminada = false;
            for (int i = 0; i < parque.getListaAtracciones().size(); i++) {
                Atraccion a = parque.getListaAtracciones().get(i);
                if (a.getNombre().equalsIgnoreCase(nombre)) {
                    parque.getListaAtracciones().remove(i);
                    eliminada = true;
                    System.out.println("ÉXITO: Atracción '" + nombre + "' eliminada del sistema.");
                    break;
                }
            }
            if (!eliminada) {
                System.out.println("ERROR: No se encontró la atracción: " + nombre);
            }
        }
    }

    @Override
    public void crearZona(Zona zona) {
        if (zona != null && parque != null) {
            parque.getListaZonas().add(zona);
            System.out.println("ÉXITO: Nueva zona '" + zona.getNombre() + "' creada satisfactoriamente.");
        }
    }
    public String getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(String nivelAcceso) { this.nivelAcceso = nivelAcceso; }
    public Parque getParque() { return parque; }
    public void setParque(Parque parque) { this.parque = parque; }
}