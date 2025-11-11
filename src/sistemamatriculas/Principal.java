package sistemamatriculas;

// Importa las piezas clave
import sistemamatriculas.service.MatriculaService;
import sistemamatriculas.view.VistaConsola;

/**
 * Clase principal que inicia la aplicación.
 * Su ÚNICA responsabilidad es "encender" las capas.
 */
public class Principal {

    public static void main(String[] args) {
        
        // 1. Crear el "Cerebro":
        //    (El Service, al nacer, crea todos los Repositorios y carga los .txt)
        MatriculaService service = new MatriculaService();
        
        // 2. Crear la "Cara":
        //    (La Vista de Consola. Le pasamos el "cerebro" para que pueda hablar con él)
        VistaConsola vista = new VistaConsola(service);
        
        // 3. Encender la aplicación:
        //    (Le decimos a la Vista que empiece a mostrar el menú)
        vista.iniciar();
    }
}