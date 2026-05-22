package co.edu.uniquindio.techparkuq.modelo.abstractas;

import co.edu.uniquindio.techparkuq.modelo.Visitante;
import co.edu.uniquindio.techparkuq.modelo.ColaVirtual;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;
import co.edu.uniquindio.techparkuq.modelo.enums.AlertaClimatica;


public abstract class   Atraccion {

    private String idUnico;
    private String nombre;
    private int capacidadMaximaCiclo;
    private double alturaMinima;
    private int edadMinima;
    private double costoAdicional;
    private int contadorVisitantesAcumulado;
    private int tiempoEsperaEstimado;
    private EstadoAtraccion estado;
    private String motivoCierre;
    private boolean activo;
    private ColaVirtual colaVirtual;
    private static final int LIMITE_VISITANTES_MANTENIMIENTO = 500;


    public Atraccion(String idUnico, String nombre, int capacidadMaximaCiclo, double alturaMinima,
                     int edadMinima, double costoAdicional) {
        this.idUnico = idUnico;
        this.nombre = nombre;
        this.capacidadMaximaCiclo = capacidadMaximaCiclo;
        this.alturaMinima = alturaMinima;
        this.edadMinima = edadMinima;
        this.costoAdicional = costoAdicional;
        this.contadorVisitantesAcumulado = 0;
        this.tiempoEsperaEstimado = 0;
        this.estado = EstadoAtraccion.ACTIVA;
        this.motivoCierre = "";
        this.activo = true;
        this.colaVirtual = new ColaVirtual();
    }


    public boolean validarRestricciones(Visitante visitante) {

        if (visitante.getEdad() < this.edadMinima) {
            return false;
        }
        if (visitante.getEstatura() < this.alturaMinima) {
            return false;
        }
        if (this.estado != EstadoAtraccion.ACTIVA) {
            return false;
        }

        return true;
    }


    public void actualizarEstado(EstadoAtraccion nuevoEstado, String motivo) {
        this.estado = nuevoEstado;
        this.motivoCierre = motivo;
    }


    public void registrarIngresoVisitante() {
        if (this.estado == EstadoAtraccion.ACTIVA) {
            this.contadorVisitantesAcumulado++;


            if (requiereMantenimiento()) {
                actualizarEstado(EstadoAtraccion.EN_MANTENIMIENTO, "Límite de 500 visitantes alcanzado. Bloqueo preventivo.");
                System.out.println(" ALERTA: La atracción " + this.nombre + " ha pasado a mantenimiento automático.");
            }
        }
    }

    public void resetearContadorVisitantes() {
        this.contadorVisitantesAcumulado = 0;
    }


    public boolean requiereMantenimiento() {
        return this.contadorVisitantesAcumulado >= LIMITE_VISITANTES_MANTENIMIENTO;
    }


    public void registrarRevisionTecnica() {
        resetearContadorVisitantes();
        actualizarEstado(EstadoAtraccion.ACTIVA, "");
        System.out.println("TÉCNICO: Revisión completada en " + this.nombre + ". Operativa nuevamente.");
    }


    public abstract void reaccionarAlClima(AlertaClimatica alerta);


    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadMaximaCiclo() {
        return capacidadMaximaCiclo;
    }

    public void setCapacidadMaximaCiclo(int capacidadMaximaCiclo) {
        this.capacidadMaximaCiclo = capacidadMaximaCiclo;
    }

    public double getAlturaMinima() {
        return alturaMinima;
    }

    public void setAlturaMinima(double alturaMinima) {
        this.alturaMinima = alturaMinima;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public double getCostoAdicional() {
        return costoAdicional;
    }

    public void setCostoAdicional(double costoAdicional) {
        this.costoAdicional = costoAdicional;
    }

    public int getContadorUso() {
        return contadorVisitantesAcumulado;
    }

    public int getTiempoEsperaEstimado() {
        return colaVirtual != null ? colaVirtual.getTiempoEstimado() : tiempoEsperaEstimado;
    }

    public int getTiempoEsperaParaVisitante(Visitante visitante) {
        if (colaVirtual == null || visitante == null) return 0;
        int posicion = colaVirtual.getListEspera().indexOf(visitante);
        if (posicion == -1) return 0;
        return (posicion + 1) * 5;
    }

    public void setTiempoEsperaEstimado(int tiempoEsperaEstimado) {
        this.tiempoEsperaEstimado = tiempoEsperaEstimado;
    }

    public EstadoAtraccion getEstado() {
        return estado;
    }

    public void setEstado(EstadoAtraccion estado) {
        this.estado = estado;
    }

    public String getMotivoCierre() {
        return motivoCierre;
    }

    public void setMotivoCierre(String motivoCierre) {
        this.motivoCierre = motivoCierre;
    }

    public boolean isActivo() { return activo; }

    public void setActivo(boolean activo) { this.activo = activo; }

    public ColaVirtual getColaVirtual() { return colaVirtual; }

    @Override
    public String toString() {
        return "Atraccion{" +
                "idUnico='" + idUnico + '\'' +
                ", nombre='" + nombre + '\'' +
                ", capacidadMaximaCiclo=" + capacidadMaximaCiclo +
                ", alturaMinima=" + alturaMinima +
                ", edadMinima=" + edadMinima +
                ", costoAdicional=" + costoAdicional +
                ", contadorVisitantes=" + contadorVisitantesAcumulado +
                ", estado=" + estado +
                '}';
    }

}
