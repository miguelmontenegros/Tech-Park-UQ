package co.edu.uniquindio.techparkuq.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ReporteTest {

    private Reporte reporte;
    private double ingresosEsperados;
    private int cierresEsperados;
    private int alertasEsperadas;

    @BeforeEach
    public void setUp() {

        ingresosEsperados = 1500500.0;
        cierresEsperados = 3;
        alertasEsperadas = 5;
        reporte = new Reporte(ingresosEsperados, cierresEsperados, alertasEsperadas);
    }

    @Test
    public void testConstructorEInicializacionFecha() {

        assertEquals(ingresosEsperados, reporte.getIngresosDiarios(), "Los ingresos diarios no coinciden.");
        assertEquals(cierresEsperados, reporte.getCierresPorClima(), "Los cierres por clima no coinciden.");
        assertEquals(alertasEsperadas, reporte.getAlertasMantenimiento(), "Las alertas de mantenimiento no coinciden.");


        assertNotNull(reporte.getFecha(), "La fecha no debería ser nula.");
        assertEquals(LocalDate.now(), reporte.getFecha(), "La fecha debería ser la del día de hoy.");
    }

    @Test
    public void testSettersYGetters() {

        LocalDate nuevaFecha = LocalDate.now().minusDays(1);
        reporte.setFecha(nuevaFecha);
        reporte.setIngresosDiarios(2000000.0);
        reporte.setCierresPorClima(1);
        reporte.setAlertasMantenimiento(2);


        assertEquals(nuevaFecha, reporte.getFecha());
        assertEquals(2000000.0, reporte.getIngresosDiarios());
        assertEquals(1, reporte.getCierresPorClima());
        assertEquals(2, reporte.getAlertasMantenimiento());
    }

    @Test
    public void testGenerarEstadisticasAtraccionesNoFalla() {

        assertDoesNotThrow(() -> reporte.generarEstadisticasAtracciones(),
                "El método generarEstadisticasAtracciones lanzó una excepción inesperada.");
    }

    @Test
    public void testExportarReporteNoFalla() {

        assertDoesNotThrow(() -> reporte.exportarReporte(),
                "El método exportarReporte lanzó una excepción inesperada.");
    }
}