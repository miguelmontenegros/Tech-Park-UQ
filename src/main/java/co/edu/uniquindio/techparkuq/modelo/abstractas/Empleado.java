package co.edu.uniquindio.techparkuq.modelo.abstractas;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Persona;

public abstract class Empleado extends Persona {

    private String rol;
    private int edad;
    private double salario;


    public Empleado(String rol, String nombre, String documento, int edad) {
        super(nombre, documento);
        this.rol = rol;
        this.edad = edad;
        this.salario = 0.0;
    }



    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }




    @Override
    public String toString() {
        return "Empleado {" +
                "Nombre: '" + getNombre() + '\'' +
                ", Documento: '" + getDocumento() + '\'' +
                ", Rol: '" + rol + '\'' +
                ", Edad: " + edad +
                ", Salario: $" + salario +
                '}';
    }
}