package co.edu.uniquindio.techparkuq.modelo;

import co.edu.uniquindio.techparkuq.modelo.abstractas.Atraccion;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Ticket;
import java.util.LinkedList;
import java.util.List;


public class ColaVirtual {


    private List<Visitante> listEspera;

    public ColaVirtual() {
        this.listEspera = new LinkedList<>();

    }
    public void agregarVisitante(Visitante visitante) {
        if (visitante == null) return;

        Ticket ticket = visitante.getTicket();


        if (ticket instanceof TicketFastPass) {
            int posicionInsercion = 0;


            for (int i = 0; i < listEspera.size(); i++) {
                if (!(listEspera.get(i).getTicket() instanceof TicketFastPass)) {
                    posicionInsercion = i;
                    break;
                }

                posicionInsercion = i + 1;
            }
            listEspera.add(posicionInsercion, visitante);
            System.out.println("PRIORIDAD: " + visitante.getNombre() + " (Fast-Pass) ha ingresado a la cola virtual.");
        } else {

            listEspera.add(visitante);
            System.out.println("LOG: " + visitante.getNombre() + " se ha unido al final de la cola virtual.");
        }
    }


    public Visitante atenderSiguiente() {
        if (!listEspera.isEmpty()) {
            Visitante siguiente = listEspera.remove(0);
            System.out.println("TURNO: Atendiendo a " + siguiente.getNombre());
            return siguiente;
        }
        System.out.println("INFO: La cola virtual está vacía.");
        return null;
    }


    public int getTiempoEstimado() {

        int tiempoPorPersona = 5;
        return listEspera.size() * tiempoPorPersona;
    }




    public List<Visitante> getListEspera() {
        return listEspera;
    }

    public void setListEspera(List<Visitante> listEspera) {
        this.listEspera = listEspera;
    }
}