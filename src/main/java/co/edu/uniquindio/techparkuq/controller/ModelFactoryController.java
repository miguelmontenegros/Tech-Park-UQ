package co.edu.uniquindio.techparkuq.controller;

import co.edu.uniquindio.techparkuq.modelo.Parque;

public class ModelFactoryController {

    private Parque parque;

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        parque = new Parque("Tech-Park UQ", 500);
        System.out.println("LOG: Parque inicializado en el ModelFactory");
    }

    public Parque getParque() {
        return parque;
    }
}