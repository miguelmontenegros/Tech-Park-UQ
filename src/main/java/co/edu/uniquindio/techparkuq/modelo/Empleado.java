package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Persona;

public class Empleado extends Persona {
    private String rol;

    public Empleado(String rol, String nombre, String documento, int edad) {
        super(nombre, documento, edad);
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Nombre: " + getNombre() + "\n Documento: " + getDocumento() + "\n Edad: " + getEdad() + "\n Rol: " + rol;
    }
}
