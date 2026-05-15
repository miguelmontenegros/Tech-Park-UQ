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

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public double getIngresosDiarios() { return ingresosDiarios; }
    public void setIngresosDiarios(double ingresosDiarios) { this.ingresosDiarios = ingresosDiarios; }

    public int getCierresPorClima() { return cierresPorClima; }
    public void setCierresPorClima(int cierresPorClima) { this.cierresPorClima = cierresPorClima; }

    public int getAlertasMantenimiento() { return alertasMantenimiento; }
    public void setAlertasMantenimiento(int alertasMantenimiento) { this.alertasMantenimiento = alertasMantenimiento; }
}