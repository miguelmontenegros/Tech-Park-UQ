package co.edu.uniquindio.techparkuq.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColaVirtualTest {

    private ColaVirtual cola;
    private Visitante visitanteGeneral;
    private Visitante visitanteFastPass;

    @BeforeEach
    public void setUp() {
        cola = new ColaVirtual();
        visitanteGeneral = new Visitante("Carlos", "123", 20, 1.7, "foto1.jpg");
        visitanteGeneral.comprarTicket(new TicketGeneral("T01", 50000));

        visitanteFastPass = new Visitante("Ana", "456", 25, 1.6, "foto2.jpg");
        visitanteFastPass.comprarTicket(new TicketFastPass("T02", 80000));
    }

    @Test
    public void testColaIniciaVacia() {
        assertTrue(cola.getListEspera().isEmpty());
        assertEquals(0, cola.getTiempoEstimado());
    }

    @Test
    public void testPrioridadFastPass() {
        cola.agregarVisitante(visitanteGeneral);
        cola.agregarVisitante(visitanteFastPass);
        assertEquals("Ana", cola.getListEspera().get(0).getNombre());
    }

    @Test
    public void testAtenderSiguiente() {
        cola.agregarVisitante(visitanteGeneral);
        Visitante atendido = cola.atenderSiguiente();
        assertEquals("Carlos", atendido.getNombre());
        assertTrue(cola.getListEspera().isEmpty());
    }

    @Test
    public void testAtenderColaVacia() {
        assertNull(cola.atenderSiguiente());
    }
}