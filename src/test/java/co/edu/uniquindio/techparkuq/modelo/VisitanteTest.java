package co.edu.uniquindio.techparkuq.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VisitanteTest {

    private Visitante visitante;

    @BeforeEach
    public void setUp() {
        visitante = new Visitante("Carlos", "123", 20, 1.75, "foto.jpg");
    }

    @Test
    public void testConstructorEInicializacion() {
        assertEquals("Carlos", visitante.getNombre());
        assertEquals("123", visitante.getDocumento());
        assertEquals(20, visitante.getEdad());
        assertEquals(1.75, visitante.getEstatura());
        assertEquals(0.0, visitante.getSaldoVirtual());
        assertTrue(visitante.getHistorialVisitas().isEmpty());
        assertTrue(visitante.getListaFavoritos().isEmpty());
    }

    @Test
    public void testRecargarYDescontarSaldo() {
        visitante.recargarSaldo(50000.0);
        assertEquals(50000.0, visitante.getSaldoVirtual());
        assertTrue(visitante.descontarSaldo(20000.0));
        assertEquals(30000.0, visitante.getSaldoVirtual());
        assertFalse(visitante.descontarSaldo(99999.0));
    }

    @Test
    public void testComprarTicket() {
        TicketGeneral ticket = new TicketGeneral("T01", 30000.0);
        visitante.comprarTicket(ticket);
        assertNotNull(visitante.getTicket());
        assertEquals("T01", visitante.getTicket().getIdTicket());
    }

    @Test
    public void testAgregarFavorito() {
        AtraccionGeneral atraccion = new AtraccionGeneral("A01", "Carrusel", 20, 1.0, 5, 0.0);
        visitante.agregarFavorito(atraccion);
        assertEquals(1, visitante.getListaFavoritos().size());
        visitante.agregarFavorito(atraccion);
        assertEquals(1, visitante.getListaFavoritos().size());
    }

    @Test
    public void testRegistrarVisita() {
        visitante.registrarVisita("Montaña Rusa");
        assertEquals(1, visitante.getHistorialVisitas().size());
        assertEquals("Montaña Rusa", visitante.getHistorialVisitas().get(0).getNombreAtraccion());
    }

    @Test
    public void testRecibirNotificacion() {
        assertDoesNotThrow(() -> visitante.recibirNotificacion("Cierre por clima"));
    }
}