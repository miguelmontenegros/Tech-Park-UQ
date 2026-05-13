package co.edu.uniquindio.techparkuq.modelo.interfaces;
import co.edu.uniquindio.techparkuq.modelo.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.Zona;

public interface IGestionable {
    void crearAtraccion(Atraccion atraccion);
    void eliminarAtraccion(String nombre);
    void crearZona(Zona zona);
}
