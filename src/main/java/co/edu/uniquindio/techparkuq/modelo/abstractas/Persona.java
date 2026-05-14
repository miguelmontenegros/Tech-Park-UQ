package co.edu.uniquindio.techparkuq.modelo.abstractas;

public abstract class Persona {
    protected String nombre;
    protected String documento;

    public Persona(String nombre, String documento) {
        this.nombre = nombre;
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }


    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }
}

