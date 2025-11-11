package sistemamatriculas.view;

import sistemamatriculas.model.*;
import sistemamatriculas.service.MatriculaService;
import java.util.ArrayList;
import java.util.Scanner;

public class VistaConsola {

    private final MatriculaService service; 
    private final Scanner scanner; 

    public VistaConsola(MatriculaService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia el bucle principal del menú de la aplicación.
     */
    public void iniciar() {
        System.out.println("=============================================");
        System.out.println("==  SISTEMA DE GESTIÓN DE MATRÍCULA v2.0   ==");
        System.out.println("=============================================");
        
        while (true) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("[1] Proceso de Matrícula");
            System.out.println("[2] Gestión de Estudiantes");
            System.out.println("[3] Gestión Académica (Aulas)");
            System.out.println("[4] Reportes y Consultas");
            System.out.println("[5] Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    menuProcesoMatricula();
                    break;
                case "2":
                    menuGestionEstudiantes();
                    break;
                case "3":
                    menuGestionAcademica();
                    break;
                case "4":
                    menuReportes();
                    break;
                case "5":
                    System.out.println("Gracias por usar el sistema. ¡Adiós!");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    // --- MENÚ [1] PROCESO DE MATRÍCULA ---
    private void menuProcesoMatricula() {
        while (true) {
            System.out.println("\n--- PROCESO DE MATRÍCULA ---");
            System.out.println("[1] Matricular Alumno (Nuevo o Antiguo)");
            System.out.println("[2] Ver Ficha de Matrícula (por DNI)");
            System.out.println("[3] Anular Matrícula (por DNI)");
            System.out.println("[4] < Volver al Menú Principal");
            System.out.print("Seleccione: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    uiMatricularAlumno();
                    break;
                case "2":
                    uiVerFichaMatricula();
                    break;
                case "3":
                    uiAnularMatricula();
                    break;
                case "4":
                    return; // Vuelve al menú principal
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // --- MENÚ [2] GESTIÓN DE ESTUDIANTES ---
    private void menuGestionEstudiantes() {
        while (true) {
            System.out.println("\n--- GESTIÓN DE ESTUDIANTES ---");
            System.out.println("[1] Registrar Nuevo Alumno");
            System.out.println("[2] Buscar Estudiante (por DNI)");
            System.out.println("[3] Gestionar Apoderados");
            System.out.println("[4] < Volver al Menú Principal");
            System.out.print("Seleccione: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    uiRegistrarEstudiante();
                    break;
                case "2":
                    uiBuscarEstudiante();
                    break;
                case "3":
                    uiGestionarApoderados();
                    break;
                case "4":
                    return; // Vuelve al menú principal
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // --- MENÚ [3] GESTIÓN ACADÉMICA ---
    private void menuGestionAcademica() {
        System.out.println("\n(Gestión para el Año Académico 2025)");
        while (true) {
            System.out.println("\n--- GESTIÓN ACADÉMICA (AULAS) ---");
            System.out.println("[1] Crear Aulas (Ej: 1ro 'A', 30 vacantes)");
            System.out.println("[2] Listar Aulas y Vacantes Disponibles");
            System.out.println("[3] < Volver al Menú Principal");
            System.out.print("Seleccione: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    uiCrearAula();
                    break;
                case "2":
                    uiListarAulas();
                    break;
                case "3":
                    return; // Vuelve al menú principal
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // --- MENÚ [4] REPORTES Y CONSULTAS ---
    private void menuReportes() {
        while (true) {
            System.out.println("\n--- REPORTES Y CONSULTAS ---");
            System.out.println("[1] Lista de Alumnos por Aula");
            System.out.println("[2] Generar Constancia de Matrícula (por DNI)");
            System.out.println("[3] Lista General de Alumnos Matriculados (Año 2025)");
            System.out.println("[4] < Volver al Menú Principal");
            System.out.print("Seleccione: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    uiReporteAlumnosPorAula();
                    break;
                case "2":
                    uiConstanciaMatricula();
                    break;
                case "3":
                    uiReporteGeneralMatriculados();
                    break;
                case "4":
                    return; // Vuelve al menú principal
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // --- Implementaciones de UI (Lógica de cada opción) ---

    // [1] Matricular
    private void uiMatricularAlumno() {
        System.out.println("\n--- Nueva Matrícula ---");
        try {
            Estudiante est = seleccionarEstudianteUI("Ingrese DNI del estudiante a matricular:");
            if (est == null) {
                System.out.println("No se encontró estudiante. Registrelo primero en 'Gestión de Estudiantes'.");
                return;
            }
            System.out.println("Estudiante: " + est.getNombreCompleto());

            Aula aula = seleccionarAulaUI("Seleccione el aula para la matrícula:");
            if (aula == null) {
                System.out.println("No se seleccionó aula. Operación cancelada.");
                return;
            }
            System.out.println("Aula: " + aula.getNombreCompleto());
            
            System.out.print("Año Académico (Ej: 2025): ");
            String anio = scanner.nextLine();
            
            Matricula nueva = service.matricularEstudiante(est.getIdEstudiante(), aula.getIdAula(), anio);
            System.out.println("¡MATRÍCULA EXITOSA!");
            System.out.println("Ficha: " + nueva);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // [1] Ver Ficha
    private void uiVerFichaMatricula() {
        System.out.println("\n--- Consultar Ficha de Matrícula ---");
        System.out.print("DNI del Estudiante: ");
        String dni = scanner.nextLine();
        System.out.print("Año Académico (Ej: 2025): ");
        String anio = scanner.nextLine();
        
        Matricula m = service.getFichaMatricula(dni, anio);
        if (m == null) {
            System.out.println("No se encontró matrícula para el DNI " + dni + " en el año " + anio);
        } else {
            System.out.println("Ficha encontrada: " + m);
        }
    }
    
    // [1] Anular Matrícula
    private void uiAnularMatricula() {
        System.out.println("\n--- Anular Matrícula ---");
        System.out.print("DNI del Estudiante: ");
        String dni = scanner.nextLine();
        System.out.print("Año Académico de la matrícula (Ej: 2025): ");
        String anio = scanner.nextLine();
        
        try {
            Matricula m = service.anularMatricula(dni, anio);
            System.out.println("¡Matrícula ANULADA con éxito!");
            System.out.println("Estado final: " + m);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // [2] Registrar Estudiante
    private void uiRegistrarEstudiante() {
        System.out.println("\n--- Registrar Nuevo Estudiante ---");
        try {
            Apoderado apo = seleccionarApoderadoUI("Ingrese DNI del Apoderado (o 0 para registrar uno nuevo):");
            if (apo == null) {
                System.out.println("No se seleccionó apoderado. Registre uno primero.");
                return;
            }
            
            System.out.println("Apoderado seleccionado: " + apo.getNombreCompleto());
            System.out.print("Nombres del Estudiante: ");
            String nombres = scanner.nextLine();
            System.out.print("Apellidos del Estudiante: ");
            String apellidos = scanner.nextLine();
            System.out.print("DNI del Estudiante: ");
            String dni = scanner.nextLine();
            System.out.print("Fecha Nacimiento (YYYY-MM-DD): ");
            String fechaNac = scanner.nextLine();

            Estudiante nuevo = service.registrarEstudiante(nombres, apellidos, dni, fechaNac, apo.getIdApoderado());
            System.out.println("¡Estudiante registrado con éxito! ID: " + nuevo.getIdEstudiante());
        
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // [2] Buscar Estudiante
    private void uiBuscarEstudiante() {
        System.out.println("\n--- Buscar Estudiante ---");
        System.out.print("Ingrese DNI del Estudiante: ");
        String dni = scanner.nextLine();
        Estudiante e = service.buscarEstudiantePorDni(dni);
        if (e == null) {
            System.out.println("No se encontró estudiante con DNI " + dni);
        } else {
            System.out.println("Estudiante encontrado:");
            System.out.println(e);
            Apoderado a = service.buscarApoderadoPorId(e.getIdApoderado());
            System.out.println("  Apoderado: " + (a != null ? a.getNombreCompleto() : "No asignado"));
        }
    }
    
    // [2] Gestionar Apoderados
    private void uiGestionarApoderados() {
        System.out.println("\n--- Gestión de Apoderados ---");
        System.out.println("[1] Registrar Apoderado");
        System.out.println("[2] Buscar Apoderado (por DNI)");
        System.out.print("Seleccione: ");
        String opcion = scanner.nextLine();

        if (opcion.equals("1")) {
            System.out.println("--- Nuevo Apoderado ---");
            System.out.print("Nombres: "); String nombres = scanner.nextLine();
            System.out.print("Apellidos: "); String apellidos = scanner.nextLine();
            System.out.print("DNI: "); String dni = scanner.nextLine();
            System.out.print("Teléfono: "); String tel = scanner.nextLine();
            System.out.print("Relación (Padre/Madre/Tutor): "); String rel = scanner.nextLine();
            try {
                Apoderado nuevo = service.registrarApoderado(nombres, apellidos, dni, tel, rel);
                System.out.println("¡Éxito! Apoderado registrado: " + nuevo);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        } else if (opcion.equals("2")) {
            System.out.print("Ingrese DNI del Apoderado: ");
            String dni = scanner.nextLine();
            Apoderado a = service.buscarApoderadoPorDni(dni);
            System.out.println(a != null ? "Encontrado: " + a : "No encontrado.");
        }
    }

    // [3] Crear Aula
    private void uiCrearAula() {
        System.out.println("\n--- Crear Nueva Aula ---");
        try {
            Profesor tutor = seleccionarProfesorUI("Seleccione el Profesor (Tutor) para esta aula:");
            if (tutor == null) {
                System.out.println("No hay profesores. Registre uno primero (Función no implementada).");
                return;
            }
            
            System.out.println("Tutor seleccionado: " + tutor.getNombreCompleto());
            System.out.print("Nombre del Grado (Ej: 1ro Primaria): ");
            String grado = scanner.nextLine();
            System.out.print("Nombre de la Sección (Ej: A): ");
            String seccion = scanner.nextLine();
            System.out.print("N° de Vacantes Totales (Ej: 30): ");
            int vacantes = Integer.parseInt(scanner.nextLine());
            
            Aula nueva = service.crearAula(grado, seccion, tutor.getIdProfesor(), vacantes);
            System.out.println("¡Aula creada con éxito!");
            System.out.println(nueva);
        
        } catch (NumberFormatException e) {
            System.err.println("Error: El número de vacantes debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // [3] Listar Aulas
    private void uiListarAulas() {
        System.out.println("\n--- Aulas Disponibles y Vacantes ---");
        ArrayList<Aula> lista = service.getTodasLasAulas();
        if (lista.isEmpty()) {
            System.out.println("No hay aulas creadas para este año.");
            return;
        }
        for (Aula a : lista) {
            int vacantesDisponibles = service.getVacantesDisponibles(a.getIdAula());
            System.out.println(a.getNombreCompleto() + " (ID: " + a.getIdAula() + ") | Vacantes Disponibles: " + vacantesDisponibles + "/" + a.getVacantesTotales());
        }
    }
    
    // [4] Reporte Alumnos por Aula
    private void uiReporteAlumnosPorAula() {
        System.out.println("\n--- Reporte: Alumnos por Aula ---");
        Aula aula = seleccionarAulaUI("Seleccione el aula para el reporte:");
        if (aula == null) {
            System.out.println("Operación cancelada.");
            return;
        }
        
        ArrayList<Estudiante> alumnos = service.getAlumnosPorAula(aula.getIdAula());
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos matriculados (activos) en " + aula.getNombreCompleto());
            return;
        }
        
        System.out.println("--- Alumnos en " + aula.getNombreCompleto() + " ---");
        int i = 1;
        for (Estudiante e : alumnos) {
            System.out.println(i + ". " + e.getNombreCompleto() + " (DNI: " + e.getDni() + ")");
            i++;
        }
    }
    
    // [4] Generar Constancia
    private void uiConstanciaMatricula() {
        System.out.println("\n--- Generar Constancia de Matrícula ---");
        System.out.print("DNI del Estudiante: ");
        String dni = scanner.nextLine();
        System.out.print("Año Académico (Ej: 2025): ");
        String anio = scanner.nextLine();
        
        Matricula m = service.getFichaMatricula(dni, anio);
        if (m == null) {
            System.out.println("No se encontró matrícula.");
            return;
        }
        
        Estudiante e = service.buscarEstudiantePorId(m.getIdEstudiante());
        Aula a = service.buscarAulaPorId(m.getIdAula());
        Apoderado apo = service.buscarApoderadoPorId(e.getIdApoderado());
        
        System.out.println("\n--- CONSTANCIA DE MATRÍCULA " + anio + " ---");
        System.out.println("Se deja constancia que el/la estudiante:");
        System.out.println("  ALUMNO: " + e.getNombreCompleto());
        System.out.println("  DNI: " + e.getDni());
        System.out.println("Se encuentra matriculado(a) en el aula:");
        System.out.println("  AULA: " + a.getNombreCompleto());
        System.out.println("El día: " + m.getFechaMatricula());
        System.out.println("Apoderado: " + apo.getNombreCompleto() + " (DNI: " + apo.getDni() + ")");
        System.out.println("ESTADO DE MATRÍCULA: " + m.getEstado());
        System.out.println("-------------------------------------");
    }

    // [4] Reporte General
    private void uiReporteGeneralMatriculados() {
        System.out.println("\n--- Reporte: General de Matriculados (Año 2025) ---");
        ArrayList<Matricula> matriculas = service.getMatriculasActivas("2025");
        if (matriculas.isEmpty()) {
            System.out.println("No hay matrículas activas en 2025.");
            return;
        }
        
        System.out.println("Total de alumnos matriculados: " + matriculas.size());
        for (Matricula m : matriculas) {
            Estudiante e = service.buscarEstudiantePorId(m.getIdEstudiante());
            Aula a = service.buscarAulaPorId(m.getIdAula());
            System.out.println(" - " + e.getNombreCompleto() + " (DNI: " + e.getDni() + ") en " + a.getNombreCompleto());
        }
    }

    // --- MÉTODOS AYUDANTES DE UI (Helpers) ---

    private Apoderado seleccionarApoderadoUI(String mensaje) {
        System.out.println(mensaje);
        System.out.print("DNI del Apoderado: ");
        String dni = scanner.nextLine();
        if (dni.equals("0")) { // Opción de registrar nuevo
            uiGestionarApoderados(); // Llama al menú de apoderados
            System.out.print("Vuelva a ingresar el DNI del apoderado registrado: ");
            dni = scanner.nextLine();
        }
        Apoderado a = service.buscarApoderadoPorDni(dni);
        if (a == null) System.out.println("DNI de apoderado no encontrado.");
        return a;
    }

    private Estudiante seleccionarEstudianteUI(String mensaje) {
        System.out.println(mensaje);
        System.out.print("DNI del Estudiante: ");
        String dni = scanner.nextLine();
        Estudiante e = service.buscarEstudiantePorDni(dni);
        if (e == null) System.out.println("DNI de estudiante no encontrado.");
        return e;
    }

    private Aula seleccionarAulaUI(String mensaje) {
        System.out.println(mensaje);
        uiListarAulas(); // Muestra la lista de aulas con vacantes
        System.out.print("Ingrese el ID del Aula (o 0 para cancelar): ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (id == 0) return null;
            Aula a = service.buscarAulaPorId(id);
            if (a == null) System.out.println("ID de aula no válido.");
            return a;
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return null;
        }
    }
    
    private Profesor seleccionarProfesorUI(String mensaje) {
        System.out.println(mensaje);
        ArrayList<Profesor> lista = service.getTodosLosProfesores();
        if (lista.isEmpty()) {
            // Auto-crea un profesor de emergencia si no hay ninguno
            System.out.println("No hay profesores. Creando profesor de prueba...");
            try {
                return service.registrarProfesor("Profesor", "Prueba", "00000000", "Primaria");
            } catch (Exception e) { return null; }
        }
        for (Profesor p : lista) {
            System.out.println(p);
        }
        System.out.print("Ingrese el ID del Profesor (o 0 para cancelar): ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (id == 0) return null;
            Profesor p = service.buscarProfesorPorId(id);
            if (p == null) System.out.println("ID de profesor no válido.");
            return p;
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return null;
        }
    }
}