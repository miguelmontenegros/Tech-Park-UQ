package co.edu.uniquindio.techparkuq.modelo;

import java.util.ArrayList;
import java.util.List;

public class Visitante extends Persona {

private double estatura;
private double saldoVirtual;
private String fotografia;
private List<String> historialVisitas;

public Visitante(String nombre, String documento, int edad, double estatura, double saldoVirtual, String fotografia){
    super(nombre, documento, edad);
    this.estatura=estatura;
    this.saldoVirtual=saldoVirtual;
    this.fotografia=fotografia;
    this.historialVisitas = new ArrayList<>();
}

    public boolean descontarSaldo(double valor) {
        if (this.saldoVirtual >= valor) {
            this.saldoVirtual -= valor;
            return true;
        }
        return false;
    }

    public void registrarVisita(String atraccionNombre) {
        this.historialVisitas.add("Visitó: " + atraccionNombre + " el " + java.time.LocalDate.now());
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double getSaldoVirtual() {
        return saldoVirtual;
    }

    public void setSaldoVirtual(double saldoVirtual) {
        this.saldoVirtual = saldoVirtual;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public List<String> getHistorialVisitas() {
        return historialVisitas;
    }

    public void setHistorialVisitas(List<String> historialVisitas) {
        this.historialVisitas = historialVisitas;
    }

    @Override
    public String toString(){
    return "Nombre: "+ getNombre() + "\n Documento: " + getDocumento() + "\n Edad: "+ getEdad()
            +"\n Estatura: "+ estatura + "\n Saldo Virtual: "+ saldoVirtual
            +"\n Fotografia: "+ fotografia;
    }
}
