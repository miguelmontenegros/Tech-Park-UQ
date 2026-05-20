package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OperadorTest {

    private Operador operador;
    private AtraccionGeneral atraccion;
    private Visitante visitante;

    @BeforeEach
    public void setUp() {
        operador = new Operador("Juan", "111", 30);
        atraccion = new AtraccionGeneral("A01", "Carrusel", 20, 1.0, 5, 0.0);
        visitante = new Visitante("Maria", "222", 10, 1.2, "foto.jpg");
        operador.getListAtraccionesGestionadas().add(atraccion);
    }

    @Test
    public void testConstructorEInicializacion() {
        assertEquals("Juan", operador.getNombre());
        assertEquals("Operador", operador.getRol());
        assertNotNull(operador.getListAtraccionesGestionadas());
        assertNotNull(operador.getListRevisiones());
    }

    @Test
    public void testValidarAccesoAprobado() {
        assertTrue(operador.validarAcceso(visitante, atraccion));
    }

    @Test
    public void testValidarAccesoDenegadoPorEstado() {
        atraccion.setEstado(EstadoAtraccion.EN_MANTENIMIENTO);
        assertFalse(operador.validarAcceso(visitante, atraccion));
    }

    @Test
    public void testValidarAccesoDenegadoPorEdad() {
        Visitante menor = new Visitante("Niño", "333", 3, 1.2, "foto2.jpg");
        assertFalse(operador.validarAcceso(menor, atraccion));
    }

    @Test
    public void testCambiarEstadoAtraccion() {
        operador.cambiarEstadoAtraccion(atraccion, EstadoAtraccion.EN_MANTENIMIENTO);
        assertEquals(EstadoAtraccion.EN_MANTENIMIENTO, atraccion.getEstado());
    }

}