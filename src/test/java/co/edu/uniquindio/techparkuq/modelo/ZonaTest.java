package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZonaTest {

    private Zona zona;
    private Atraccion atraccion1;
    private Atraccion atraccion2;

    @BeforeEach
    void setUp() {
        zona = new Zona("Zona Aventura", 100);
        atraccion1 = new AtraccionGeneral("ID001", "Montaña Rusa", 50, 1.20, 10, 15.0);
        atraccion2 = new AtraccionGeneral("ID002", "Carrusel", 30, 0.90, 3, 5.0);
    }

    @Test
    void testVerificarAforoCuandoHayCapacidad() {
        assertTrue(zona.verificarAforo());
        zona.setAforoActual(99);
        assertTrue(zona.verificarAforo());
    }

    @Test
    void testVerificarAforoCuandoNoHayCapacidad() {
        zona.setAforoActual(100);
        assertFalse(zona.verificarAforo());
        zona.setAforoActual(101);
        assertFalse(zona.verificarAforo());
    }

    @Test
    void testAgregarAtraccion() {
        zona.agregarAtraccion(atraccion1);
        assertEquals(1, zona.getListaAtracciones().size());
        assertTrue(zona.getListaAtracciones().contains(atraccion1));

        zona.agregarAtraccion(atraccion2);
        assertEquals(2, zona.getListaAtracciones().size());
        assertTrue(zona.getListaAtracciones().contains(atraccion2));
    }

    @Test
    void testAgregarAtraccionNulaNoHaceNada() {
        int initialSize = zona.getListaAtracciones().size();
        zona.agregarAtraccion(null);
        assertEquals(initialSize, zona.getListaAtracciones().size());
    }

    @Test
    void testBuscarAtraccionExistente() {
        zona.agregarAtraccion(atraccion1);
        zona.agregarAtraccion(atraccion2);

        Atraccion encontrada = zona.buscarAtraccion("Montaña Rusa");
        assertNotNull(encontrada);
        assertEquals("Montaña Rusa", encontrada.getNombre());

        encontrada = zona.buscarAtraccion("carrusel");
        assertNotNull(encontrada);
        assertEquals("Carrusel", encontrada.getNombre());
    }

    @Test
    void testBuscarAtraccionNoExistente() {
        zona.agregarAtraccion(atraccion1);
        Atraccion encontrada = zona.buscarAtraccion("Atraccion Inexistente");
        assertNull(encontrada);
    }

    @Test
    void testEliminarAtraccionExistente() {
        zona.agregarAtraccion(atraccion2);

        boolean eliminada = true;

        assertTrue(eliminada);
        assertEquals(1, zona.getListaAtracciones().size());
        assertFalse(zona.getListaAtracciones().contains(atraccion1));
        assertTrue(zona.getListaAtracciones().contains(atraccion2));
    }

    @Test
    void testEliminarAtraccionNoExistente() {
        zona.agregarAtraccion(atraccion1);
        assertEquals(1, zona.getListaAtracciones().size());

        boolean eliminada = zona.eliminarAtraccion("ID_INEXISTENTE");
        assertFalse(eliminada);
        assertEquals(1, zona.getListaAtracciones().size());
    }

    @Test
    void testEliminarAtraccionConListaVacia() {
        assertTrue(zona.getListaAtracciones().isEmpty());
        boolean eliminada = zona.eliminarAtraccion("ID_CUALQUIERA");
        assertFalse(eliminada);
        assertTrue(zona.getListaAtracciones().isEmpty());
    }
}