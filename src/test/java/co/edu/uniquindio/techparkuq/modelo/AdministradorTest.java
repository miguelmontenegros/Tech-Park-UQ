package co.edu.uniquindio.techparkuq.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import co.edu.uniquindio.techparkuq.modelo.enums.EstadoAtraccion;
import static org.junit.jupiter.api.Assertions.*;

public class AdministradorTest {

    private Administrador admin;
    private Parque parque;

    @BeforeEach
    public void setUp() {
        parque = new Parque("TechPark UQ", 200);
        admin = new Administrador("Laura", "789", 35, "ALTO", parque);
    }

    @Test
    public void testConstructorEInicializacion() {
        assertEquals("Laura", admin.getNombre());
        assertEquals("ALTO", admin.getNivelAcceso());
        assertNotNull(admin.getParque());
    }

    @Test
    public void testCrearZona() {
        Zona zona = new Zona("Zona Aventura", 100);
        admin.crearZona(zona);
        assertTrue(parque.getListaZonas().contains(zona));
    }

    @Test
    public void testCrearYEliminarAtraccion() {
        Zona zona = new Zona("Zona Test", 50);
        admin.crearZona(zona);
        AtraccionGeneral atraccion = new AtraccionGeneral("A03", "Montaña Rusa", 30, 1.4, 12, 5000.0);
        zona.agregarAtraccion(atraccion);
        admin.eliminarAtraccion("A03");
        assertNotNull(zona.buscarAtraccion("Montaña Rusa"));
        assertEquals(EstadoAtraccion.CERRADA, zona.buscarAtraccion("Montaña Rusa").getEstado());
    }

    @Test
    public void testAsignarOperadorZona() {
        Zona zona = new Zona("Zona Mecánica", 80);
        Operador operador = new Operador("Pedro", "321", 28);
        admin.asignarOperadorZona(operador, zona);
        assertEquals(zona, operador.getZonaAsignada());
    }
}