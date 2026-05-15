package co.edu.uniquindio.techparkuq.modelo;

import java.time.LocalDate;
public class Reporte {

    private LocalDate fecha;
    private double ingresosDiarios;
    private int cierresPorClima;
    private int alertasMantenimiento;

    public Reporte(double ingresosDiarios, int cierresPorClima, int alertasMantenimiento) {
        this.fecha = LocalDate.now();
        this.ingresosDiarios = ingresosDiarios;
        this.cierresPorClima = cierresPorClima;
        this.alertasMantenimiento = alertasMantenimiento;
    }

    public void generarEstadisticasAtracciones() {
        System.out.println("\n==== ESTADÍSTICAS DEL TECH-PARK UQ ====");
        System.out.println("Fecha de corte: " + this.fecha);
        System.out.println("Ingresos Diarios Totales: $" + this.ingresosDiarios);
        System.out.println("Atracciones cerradas por clima hoy: " + this.cierresPorClima);
        System.out.println("Alertas de mantenimiento registradas: " + this.alertasMantenimiento);
        System.out.println("=======================================\n");
    }
    public void exportarReporte() {
        String contenidoExportacion =
                "REPORTE OFICIAL TECH-PARK UQ\n" +
                        "Fecha: " + this.fecha + "\n" +
                        "Ingresos Diarios: $" + this.ingresosDiarios + "\n" +
                        "Cierres de Atracciones por Clima: " + this.cierresPorClima + "\n" +
                        "Alertas de Mantenimiento Ejecutadas: " + this.alertasMantenimiento;

        System.out.println("Simulando exportación de datos...");
        System.out.println("Contenido preparado para exportar:\n" + contenidoExportacion);
        System.out.println("ÉXITO: Reporte procesado correctamente en memoria.");
    }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public double getIngresosDiarios() { return ingresosDiarios; }
    public void setIngresosDiarios(double ingresosDiarios) { this.ingresosDiarios = ingresosDiarios; }

    public int getCierresPorClima() { return cierresPorClima; }
    public void setCierresPorClima(int cierresPorClima) { this.cierresPorClima = cierresPorClima; }

    public int getAlertasMantenimiento() { return alertasMantenimiento; }
    public void setAlertasMantenimiento(int alertasMantenimiento) { this.alertasMantenimiento = alertasMantenimiento; }
}