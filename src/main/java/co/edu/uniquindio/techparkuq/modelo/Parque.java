package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Empleado;
import co.edu.uniquindio.techparkuq.modelo.enums.AlertaClimatica;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;
import co.edu.uniquindio.techparkuq.modelo.interfaces.IGestionable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

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
            return false;
        }
        visitantesActivos.add(v);
        return true;
    }

    public void cambiarEstadoClima(AlertaClimatica nuevaAlerta) {
        this.estadoClima = nuevaAlerta;

        for (Zona zona : listaZonas) {
            for (Atraccion atr : zona.getListaAtracciones()) {
                if (nuevaAlerta != AlertaClimatica.NINGUNA) {
                    if (atr instanceof AtraccionAcuatica || atr.isAltoRiesgo()) {
                        atr.actualizarEstado(EstadoAtraccion.CERRADA, "Cierre automático: " + nuevaAlerta);
                    }
                } else {
                    atr.actualizarEstado(EstadoAtraccion.ACTIVA, "");
                }
                atr.reaccionarAlClima(nuevaAlerta);
            }
        }

        String msg = (nuevaAlerta != AlertaClimatica.NINGUNA)
                ? "Alerta climática activa: " + nuevaAlerta + ". Atracciones de riesgo cerradas."
                : "Clima normalizado. Todas las atracciones operativas.";
        notificarVisitantes(msg);
    }

    public void notificarVisitantes(String mensaje) {
        for (Visitante v : visitantesActivos) {
            if (v.getTicket() != null && v.getTicket().isActivo()) {
                v.agregarNotificacion(mensaje);
            }
        }
    }

    public List<Atraccion> getListaAtracciones() {
        List<Atraccion> todas = new ArrayList<>();
        for (Zona z : listaZonas) todas.addAll(z.getListaAtracciones());
        return todas;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Zona> getListaZonas() { return listaZonas; }
    public void setListaZonas(List<Zona> listaZonas) { this.listaZonas = listaZonas; }
    public List<Empleado> getListaEmpleados() { return listaEmpleados; }
    public void setListaEmpleados(List<Empleado> listaEmpleados) { this.listaEmpleados = listaEmpleados; }
    public List<Visitante> getListaVisitantes() { return visitantesActivos; }
    public void setListaVisitantes(List<Visitante> listaVisitantes) { this.visitantesActivos = listaVisitantes; }
    public double getIngresosDiarios() { return ingresosDiarios; }

    @Override
    public void eliminarAtraccion(String idUnico) {
        for (Zona z : listaZonas) {
            z.eliminarAtraccion(idUnico);
        }
    }

    @Override
    public void crearZona(Zona zona) {
        if (zona != null) listaZonas.add(zona);
    }

    @Override
    public void crearAtraccion(Atraccion atraccion, String nombreZona) {
        for (Zona z : listaZonas) {
            if (z.getNombre().equalsIgnoreCase(nombreZona)) {
                z.agregarAtraccion(atraccion);
                return;
            }
        }
    }

    public void registrarUsoAtraccion(Visitante v, Atraccion a) {
        if (a.validarRestricciones(v)) {
            a.registrarIngresoVisitante();
            v.registrarVisita(a.getNombre());
        }
    }

    // --- ESTADÍSTICAS AVANZADAS MEJORADAS ---
    public String generarReporteEstadistico() {
        List<Atraccion> todas = getListaAtracciones();

        Atraccion popular = todas.stream()
                .max(Comparator.comparingInt(Atraccion::getContadorUso))
                .orElse(null);

        int totalEnEspera = todas.stream()
                .mapToInt(a -> a.getColaVirtual().getListEspera().size())
                .sum();

        long cerradas = todas.stream()
                .filter(a -> a.getEstado() == EstadoAtraccion.CERRADA)
                .count();

        double promedioEspera = todas.stream()
                .mapToInt(Atraccion::getTiempoEsperaEstimado)
                .average()
                .orElse(0.0);

        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS AVANZADAS ===\n\n");
        sb.append("• Atracción estrella: ").append(popular != null ? popular.getNombre() : "N/A")
                .append(" (").append(popular != null ? popular.getContadorUso() : 0).append(" usos)\n");
        sb.append("• Atracciones fuera de servicio: ").append(cerradas).append("\n");
        sb.append("• Total personas en colas: ").append(totalEnEspera).append("\n");
        sb.append("• Promedio espera general: ").append(String.format("%.2f", promedioEspera)).append(" min\n\n");

        sb.append("=== USO POR ATRACCIÓN ===\n");
        for(Atraccion a : todas) {
            sb.append("- ").append(a.getNombre()).append(": ")
                    .append(a.getContadorUso()).append(" usos | ")
                    .append(a.getColaVirtual().getListEspera().size()).append(" en cola\n");
        }

        return sb.toString();
    }
}