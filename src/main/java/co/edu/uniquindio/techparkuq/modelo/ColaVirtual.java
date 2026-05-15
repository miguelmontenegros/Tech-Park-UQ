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




    public List<Visitante> getListEspera() {
        return listEspera;
    }

    public void setListEspera(List<Visitante> listEspera) {
        this.listEspera = listEspera;
    }
}