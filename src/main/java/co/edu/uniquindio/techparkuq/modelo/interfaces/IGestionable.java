package co.edu.uniquindio.techparkuq.modelo.interfaces;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.Zona;

public interface IGestionable {
    void crearAtraccion(Atraccion atraccion, String nombreZona);
    void eliminarAtraccion(String idUnico);
    void crearZona(Zona zona);
}
