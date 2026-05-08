package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.enums.TipoTicket;

public class Visitante extends Persona {
private int edad;
private double estatura;
private double saldoVirtual;
private String fotografia;
private TipoTicket tipoTicket;

public Visitante(int edad, double estatura, double saldoVirtual, String fotografia, TipoTicket tipoTicket, String nombre, String documento){
    super(nombre, documento);
    this.edad=edad;
    this.estatura=estatura;
    this.saldoVirtual=saldoVirtual;
    this.fotografia=fotografia;
    this.tipoTicket=tipoTicket;
}

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
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
    public TipoTicket getTipoTicket() {
    return tipoTicket;
    }
    public void setTipoTicket(TipoTicket tipoTicket) {
        this.tipoTicket = tipoTicket;
    }

    @Override
    public String toString(){
    return "Nombre: "+ getNombre() + "\n Documento: " + getDocumento() + "\n Edad: "+ edad
            +"\n Estatura: "+ estatura + "\n Saldo Virtual: "+ saldoVirtual
            +"\n Fotografia: "+ fotografia  + "\n TipoTicket: "+ tipoTicket;
    }
}
