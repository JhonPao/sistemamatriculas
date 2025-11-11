package sistemamatriculas.view;

import sistemamatriculas.model.*;
import sistemamatriculas.service.MatriculaService;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Esta clase maneja TODA la interacción con el usuario (consola).
 * Es la "Vista": solo muestra información y pide datos.
 * NO contiene lógica de negocio (no valida, no calcula, no guarda).
 * Solo se comunica con el "MatriculaService".
 */
public class VistaConsola {

    // El "cerebro" de la app
    private final MatriculaService service; 
    // La herramienta para leer la entrada del usuario
    private final Scanner scanner; 

    // El constructor recibe el "cerebro" para poder usarlo
    public VistaConsola(MatriculaService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia el bucle principal del menú de la aplicación.
     */
    public void iniciar() {
        System.out.println("=============================================");
        System.out.println("==  SISTEMA DE GESTIÓN DE MATRÍCULAS 1.0   ==");
        System.out.println("=============================================");
        
        // Bucle principal del menú
        while (true) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Gestionar Apoderados");
            System.out.println("2. Gestionar Estudiantes");
            System.out.println("3. Gestionar Profesores");
            System.out.println("4. Gestionar Grados y Secciones");
            System.out.println("5. Realizar Matrícula");
            System.out.println("0. Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    menuApoderados();
                    break;
                case "2":
                    menuEstudiantes();
                    break;
                case "3":
                    menuProfesores();
                    break;
                case "4":
                    menuGradosYSecciones();
                    break;
                case "5":
                    menuMatriculas();
                    break;
                case "0":
                    System.out.println("Gracias por usar el sistema. ¡Adiós!");
                    return; // Termina el método iniciar() y el programa
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    // --- SUB-MENÚS ---

    private void menuApoderados() {
        System.out.println("\n-- Gestión de Apoderados --");
        System.out.println("1. Registrar Apoderado");
        System.out.println("2. Listar Apoderados");
        System.out.print("Seleccione: ");
        String opcion = scanner.nextLine();

        if (opcion.equals("1")) {
            // --- UI para Registrar Apoderado ---
            System.out.println("--- Nuevo Apoderado ---");
            System.out.print("Nombres: ");
            String nombres = scanner.nextLine();
            System.out.print("Apellidos: ");
            String apellidos = scanner.nextLine();
            System.out.print("DNI: ");
            String dni = scanner.nextLine();
            System.out.print("Teléfono: ");
            String tel = scanner.nextLine();
            System.out.print("Relación (Padre/Madre/Tutor): ");
            String rel = scanner.nextLine();
            
            // Llama al SERVICIO para registrar
            Apoderado nuevo = service.registrarApoderado(nombres, apellidos, dni, tel, rel);
            System.out.println("¡Éxito! Apoderado registrado: " + nuevo);
        
        } else if (opcion.equals("2")) {
            // --- UI para Listar Apoderados ---
            System.out.println("--- Lista de Apoderados ---");
            ArrayList<Apoderado> lista = service.getTodosLosApoderados();
            if (lista.isEmpty()) {
                System.out.println("No hay apoderados registrados.");
            } else {
                for (Apoderado a : lista) {
                    System.out.println(a); // Llama al .toString()
                }
            }
        }
    }

    private void menuEstudiantes() {
        System.out.println("\n-- Gestión de Estudiantes --");
        System.out.println("1. Registrar Estudiante");
        System.out.println("2. Listar Estudiantes");
        System.out.print("Seleccione: ");
        String opcion = scanner.nextLine();
        
        if (opcion.equals("1")) {
            // --- UI para Registrar Estudiante ---
            System.out.println("--- Nuevo Estudiante ---");
            
            // Paso 1: Seleccionar un Apoderado
            Apoderado apoderado = seleccionarApoderadoUI();
            if (apoderado == null) {
                System.out.println("Registro de estudiante cancelado.");
                return;
            }
            
            System.out.println("Apoderado seleccionado: " + apoderado.getNombreCompleto());
            System.out.print("Nombres del Estudiante: ");
            String nombres = scanner.nextLine();
            System.out.print("Apellidos del Estudiante: ");
            String apellidos = scanner.nextLine();
            System.out.print("DNI del Estudiante: ");
            String dni = scanner.nextLine();
            System.out.print("Fecha Nacimiento (YYYY-MM-DD): ");
            String fechaNac = scanner.nextLine();
            
            // Llama al SERVICIO
            Estudiante nuevo = service.registrarEstudiante(nombres, apellidos, dni, fechaNac, apoderado.getIdApoderado());
            System.out.println("¡Éxito! Estudiante registrado: " + nuevo);
        
        } else if (opcion.equals("2")) {
            // --- UI para Listar Estudiantes ---
            System.out.println("--- Lista de Estudiantes ---");
            ArrayList<Estudiante> lista = service.getTodosLosEstudiantes();
            if (lista.isEmpty()) {
                System.out.println("No hay estudiantes registrados.");
            } else {
                for (Estudiante e : lista) {
                    System.out.println(e);
                }
            }
        }
    }

    private void menuProfesores() {
        // (Similar a Apoderados: crear y listar)
        System.out.println("Opción de Profesores (aún en construcción).");
    }

    private void menuGradosYSecciones() {
        // (Similar: crear y listar)
        System.out.println("Opción de Grados y Secciones (aún en construcción).");
    }

    private void menuMatriculas() {
        System.out.println("\n-- Nueva Matrícula --");
        try {
            // Paso 1: Seleccionar Estudiante
            Estudiante est = seleccionarEstudianteUI();
            if (est == null) return; // Cancela si no elige

            // Paso 2: Seleccionar Sección
            Seccion sec = seleccionarSeccionUI();
            if (sec == null) return; // Cancela si no elige
            
            System.out.print("Año Académico (Ej: 2025): ");
            String anio = scanner.nextLine();
            
            // Llama al SERVICIO para matricular
            Matricula nueva = service.matricularEstudiante(est.getIdEstudiante(), sec.getIdSeccion(), anio);
            System.out.println("¡MATRÍCULA EXITOSA!");
            System.out.println(nueva);

        } catch (Exception e) {
            // ¡Capturamos los errores del Service y los mostramos al usuario!
            System.err.println("Error al matricular: " + e.getMessage());
        }
    }

    // --- MÉTODOS AYUDANTES (Helpers) ---

    /**
     * Un helper de UI para listar y seleccionar un Apoderado por ID.
     * @return El Apoderado seleccionado, o null si cancela.
     */
    private Apoderado seleccionarApoderadoUI() {
        System.out.println("--- Seleccionar Apoderado ---");
        ArrayList<Apoderado> lista = service.getTodosLosApoderados();
        if (lista.isEmpty()) {
            System.out.println("Error: No hay apoderados. Registre uno primero.");
            return null;
        }
        for (Apoderado a : lista) {
            System.out.println(a);
        }
        System.out.print("Ingrese el ID del Apoderado (o 0 para cancelar): ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (id == 0) return null;
            Apoderado a = service.buscarApoderadoPorId(id);
            if (a == null) System.out.println("ID no válido.");
            return a;
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return null;
        }
    }
    
    /**
     * Un helper de UI para listar y seleccionar un Estudiante por ID.
     */
    private Estudiante seleccionarEstudianteUI() {
        System.out.println("--- Seleccionar Estudiante ---");
        ArrayList<Estudiante> lista = service.getTodosLosEstudiantes();
        if (lista.isEmpty()) {
            System.out.println("Error: No hay estudiantes. Registre uno primero.");
            return null;
        }
        for (Estudiante e : lista) {
            System.out.println(e);
        }
        System.out.print("Ingrese el ID del Estudiante (o 0 para cancelar): ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (id == 0) return null;
            Estudiante e = service.buscarEstudiantePorId(id);
            if (e == null) System.out.println("ID no válido.");
            return e;
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return null;
        }
    }
    
    /**
     * Un helper de UI para listar y seleccionar una Sección por ID.
     */
    private Seccion seleccionarSeccionUI() {
        System.out.println("--- Seleccionar Sección ---");
        ArrayList<Seccion> lista = service.getTodasLasSecciones();
        if (lista.isEmpty()) {
            System.out.println("Error: No hay secciones. Cree una primero.");
            return null;
        }
        for (Seccion s : lista) {
            System.out.println(s);
        }
        System.out.print("Ingrese el ID de la Sección (o 0 para cancelar): ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (id == 0) return null;
            Seccion s = service.buscarSeccionPorId(id);
            if (s == null) System.out.println("ID no válido.");
            return s;
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return null;
        }
    }
}