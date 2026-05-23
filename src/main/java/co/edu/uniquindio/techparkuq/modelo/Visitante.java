package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Ticket;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Persona;
import co.edu.uniquindio.techparkuq.modelo.interfaces.INotificable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Visitante extends Persona implements INotificable, Serializable {

    private static final long serialVersionUID = 1L;
    private int edad;
    private double estatura;
    private double saldoVirtual;
    private String fotografia;
    private List<RegistroVisita> historialVisitas;
    private List<Atraccion> listaFavoritos;
    private Ticket ticket;
    private List<String> notificaciones;

    public Visitante(String nombre, String documento, int edad, double estatura, String fotografia){
        super(nombre, documento);
        this.edad = edad;
        this.estatura=estatura;
        this.saldoVirtual=0.0;
        this.fotografia=fotografia;
        this.historialVisitas = new ArrayList<>();
        this.listaFavoritos=new ArrayList<>();
        this.notificaciones = new ArrayList<>();
    }

    public void registrarVisita(String atraccionNombre) {
        this.historialVisitas.add(new RegistroVisita(atraccionNombre));
    }

    public List<RegistroVisita> getHistorialVisitas() {
        return historialVisitas;
    }

    public void agregarFavorito(Atraccion atraccion) {
        if (atraccion != null && !listaFavoritos.contains(atraccion)) {
            this.listaFavoritos.add(atraccion);
        }
    }

    public void comprarTicket(Ticket ticket) {
        if (ticket != null) this.ticket = ticket;
    }

    public void recargarSaldo(double monto) {
        if (monto > 0) this.saldoVirtual += monto;
    }

    public boolean descontarSaldo(double valor) {
        if (this.saldoVirtual >= valor) {
            this.saldoVirtual -= valor;
            return true;
        }
        return false;
    }

    @Override
    public void recibirNotificacion(String mensaje) {
        System.out.println("Notificación para " + getNombre() + ": " + mensaje);
    }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public double getEstatura() { return estatura; }
    public void setEstatura(double estatura) { this.estatura = estatura; }
    public double getSaldoVirtual() { return saldoVirtual; }
    public void setSaldoVirtual(double saldoVirtual) { this.saldoVirtual = saldoVirtual; }
    public String getFotografia() { return fotografia; }
    public void setFotografia(String fotografia) { this.fotografia = fotografia; }
    public List<Atraccion> getListaFavoritos() { return listaFavoritos; }
    public Ticket getTicket() { return ticket; }
    public void agregarNotificacion(String mensaje) { this.notificaciones.add(java.time.LocalDateTime.now() + " — " + mensaje); }
    public List<String> getNotificaciones() { return notificaciones; }
}