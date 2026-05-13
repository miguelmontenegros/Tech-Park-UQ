package co.edu.uniquindio.techparkuq.modelo.interfaces;

public interface IMantenible {
    void realizarMantenimiento();
    boolean necesitaRevision();
    int getContadorUso();
}
