package co.edu.uniquindio.techparkuq.modelo;

public class Empleado extends Persona{
    private String rol;

    public Empleado(String rol, String nombre, String documento){
        super(nombre, documento);
        this.rol = rol;
    }
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString(){
        return "Nombre: "+ getNombre() + "\n Documento: "+ getDocumento() + "\n Rol: "+ rol;
    }
}
