package co.edu.uniquindio.techparkuq.controlador;

import co.edu.uniquindio.techparkuq.modelo.Parque;
import co.edu.uniquindio.techparkuq.modelo.Administrador;
import co.edu.uniquindio.techparkuq.modelo.abstractas.Empleado;
import java.io.*;

public class ModelFactoryController {

    private Parque parque;
    private static final String RUTA_ARCHIVO = "src/main/resources/data/parque.dat";

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    private ModelFactoryController() {
        cargarDatosSerializable();
        if (this.parque == null) {
            System.out.println("No se encontró archivo de datos. Iniciando parque por defecto.");
            this.parque = new Parque("Tech-Park UQ", 500);
            guardarDatosSerializable();
        } else {
            boolean existeAdmin = false;
            for (Empleado e : this.parque.getListaEmpleados()) {
                if (e instanceof Administrador && "12345".equals(e.getDocumento())) {
                    existeAdmin = true;
                    break;
                }
            }
            if (!existeAdmin) {
                Administrador adminMaestro = new Administrador("Admin Principal", "12345", 30, "ALTO", this.parque);
                this.parque.getListaEmpleados().add(adminMaestro);
                guardarDatosSerializable();
            }
        }
    }

    public Parque getParque() {
        return parque;
    }

    public void guardarDatosSerializable() {
        try {
            File carpeta = new File("src/main/resources/data");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
                oos.writeObject(this.parque);
                System.out.println("Datos guardados correctamente.");
            }
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cargarDatosSerializable() {
        File archivo = new File(RUTA_ARCHIVO);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                this.parque = (Parque) ois.readObject();
                System.out.println("Datos cargados correctamente desde el archivo.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar los datos: " + e.getMessage());
            }
        }
    }
}