package sistemamatriculas.service;

// Importa todos tus modelos y repositorios
import sistemamatriculas.model.*;
import sistemamatriculas.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Esta es la clase "Cerebro" de la aplicación.
 * Actúa como una fachada que coordina toda la lógica de negocio
 * y es la ÚNICA clase con la que la VISTA (consola/GUI) debe hablar.
 */
public class MatriculaService {

    // --- 1. Atributos (los 7 Repositorios) ---
    private final ApoderadoRepository apoderadoRepo;
    private final EstudianteRepository estudianteRepo;
    private final ProfesorRepository profesorRepo;
    private final CursoRepository cursoRepo;
    private final GradoRepository gradoRepo;
    private final SeccionRepository seccionRepo;
    private final MatriculaRepository matriculaRepo;

    // --- 2. Atributos (Listas en Memoria) ---
    // (Guardamos los datos aquí mientras el programa corre)
    private final ArrayList<Apoderado> listaApoderados;
    private final ArrayList<Estudiante> listaEstudiantes;
    private final ArrayList<Profesor> listaProfesores;
    private final ArrayList<Curso> listaCursos;
    private final ArrayList<Grado> listaGrados;
    private final ArrayList<Seccion> listaSecciones;
    private final ArrayList<Matricula> listaMatriculas;

    // --- 3. Atributos (Contadores de ID) ---
    // (Para saber qué ID le toca al próximo registro)
    private int nextApoderadoId;
    private int nextEstudianteId;
    private int nextProfesorId;
    private int nextCursoId;
    private int nextGradoId;
    private int nextSeccionId;
    private int nextMatriculaId;

    // --- 4. Constructor ---
    // (Se ejecuta 1 vez cuando el programa inicia)
    public MatriculaService() {
        // Inicializa todos los repositorios
        this.apoderadoRepo = new ApoderadoRepository();
        this.estudianteRepo = new EstudianteRepository();
        this.profesorRepo = new ProfesorRepository();
        this.cursoRepo = new CursoRepository();
        this.gradoRepo = new GradoRepository();
        this.seccionRepo = new SeccionRepository();
        this.matriculaRepo = new MatriculaRepository();

        // Carga los datos de los .txt a las listas en memoria
        this.listaApoderados = apoderadoRepo.cargarApoderados();
        this.listaEstudiantes = estudianteRepo.cargarEstudiantes();
        this.listaProfesores = profesorRepo.cargarProfesores();
        this.listaCursos = cursoRepo.cargarCursos();
        this.listaGrados = gradoRepo.cargarGrados();
        this.listaSecciones = seccionRepo.cargarSecciones();
        this.listaMatriculas = matriculaRepo.cargarMatriculas();

        // Calcula los siguientes IDs disponibles
        // (Toma el ID del último ítem y le suma 1)
        this.nextApoderadoId = listaApoderados.isEmpty() ? 1 : listaApoderados.get(listaApoderados.size() - 1).getIdApoderado() + 1;
        this.nextEstudianteId = listaEstudiantes.isEmpty() ? 1 : listaEstudiantes.get(listaEstudiantes.size() - 1).getIdEstudiante() + 1;
        this.nextProfesorId = listaProfesores.isEmpty() ? 1 : listaProfesores.get(listaProfesores.size() - 1).getIdProfesor() + 1;
        this.nextCursoId = listaCursos.isEmpty() ? 1 : listaCursos.get(listaCursos.size() - 1).getIdCurso() + 1;
        this.nextGradoId = listaGrados.isEmpty() ? 1 : listaGrados.get(listaGrados.size() - 1).getIdGrado() + 1;
        this.nextSeccionId = listaSecciones.isEmpty() ? 1 : listaSecciones.get(listaSecciones.size() - 1).getIdSeccion() + 1;
        this.nextMatriculaId = listaMatriculas.isEmpty() ? 1 : listaMatriculas.get(listaMatriculas.size() - 1).getIdMatricula() + 1;
    }

    // --- 5. Métodos de Negocio (Lógica de "Crear") ---
    // (Estos son los métodos que la VISTA llamará)

    /**
     * Registra un nuevo Apoderado, lo guarda en memoria y en el archivo .txt
     */
    public Apoderado registrarApoderado(String nombres, String apellidos, String dni, String telefono, String relacion) {
        // 1. Crea el objeto
        Apoderado nuevo = new Apoderado(nextApoderadoId, nombres, apellidos, dni, telefono, relacion);
        // 2. Lo añade a la lista en memoria
        this.listaApoderados.add(nuevo);
        // 3. Pide al repositorio que guarde la lista COMPLETA en el .txt
        this.apoderadoRepo.guardarApoderados(this.listaApoderados);
        // 4. Incrementa el ID para el próximo
        this.nextApoderadoId++;
        // 5. Retorna el objeto creado
        return nuevo;
    }

    /**
     * Registra un nuevo Estudiante, lo guarda en memoria y en el archivo .txt
     */
    public Estudiante registrarEstudiante(String nombres, String apellidos, String dni, String fechaNac, int idApoderado) {
        Estudiante nuevo = new Estudiante(nextEstudianteId, nombres, apellidos, dni, fechaNac, idApoderado);
        this.listaEstudiantes.add(nuevo);
        this.estudianteRepo.guardarEstudiantes(this.listaEstudiantes);
        this.nextEstudianteId++;
        return nuevo;
    }

    /**
     * Registra un nuevo Profesor, lo guarda en memoria y en el archivo .txt
     */
    public Profesor registrarProfesor(String nombres, String apellidos, String dni, String especialidad) {
        Profesor nuevo = new Profesor(nextProfesorId, nombres, apellidos, dni, especialidad);
        this.listaProfesores.add(nuevo);
        this.profesorRepo.guardarProfesores(this.listaProfesores);
        this.nextProfesorId++;
        return nuevo;
    }

    /**
     * Crea un nuevo Grado (Ej: "1ro Primaria"), lo guarda y persiste.
     */
    public Grado crearGrado(String nombre) {
        Grado nuevo = new Grado(nextGradoId, nombre);
        this.listaGrados.add(nuevo);
        this.gradoRepo.guardarGrados(this.listaGrados);
        this.nextGradoId++;
        return nuevo;
    }

    /**
     * Crea una nueva Sección (Ej: "A") y la asigna a un Grado y un Tutor.
     */
    public Seccion crearSeccion(String nombre, int idGrado, int idProfesorTutor) throws Exception {
        // --- Lógica de Validación (¡Esto es el "Service"!) ---
        if (buscarGradoPorId(idGrado) == null) {
            throw new Exception("Error: El Grado con ID " + idGrado + " no existe.");
        }
        if (buscarProfesorPorId(idProfesorTutor) == null) {
            throw new Exception("Error: El Profesor (Tutor) con ID " + idProfesorTutor + " no existe.");
        }
        // --- Fin de la Validación ---
        
        Seccion nuevo = new Seccion(nextSeccionId, nombre, idGrado, idProfesorTutor);
        this.listaSecciones.add(nuevo);
        this.seccionRepo.guardarSecciones(this.listaSecciones);
        this.nextSeccionId++;
        return nuevo;
    }

    /**
     * Realiza el acto de Matrícula.
     * Conecta un Estudiante con una Sección para un año.
     */
    public Matricula matricularEstudiante(int idEstudiante, int idSeccion, String anioAcademico) throws Exception {
        // --- Lógica de Validación ---
        if (buscarEstudiantePorId(idEstudiante) == null) {
            throw new Exception("Error: El Estudiante con ID " + idEstudiante + " no existe.");
        }
        if (buscarSeccionPorId(idSeccion) == null) {
            throw new Exception("Error: La Sección con ID " + idSeccion + " no existe.");
        }
        // Validar que el alumno no esté ya matriculado en este año
        for (Matricula m : this.listaMatriculas) {
            if (m.getIdEstudiante() == idEstudiante && m.getAnioAcademico().equals(anioAcademico) && m.getEstado().equals("ACTIVA")) {
                throw new Exception("Error: El estudiante ya tiene una matrícula activa para el año " + anioAcademico);
            }
        }
        // --- Fin de la Validación ---

        String fechaHoy = LocalDate.now().toString(); // "YYYY-MM-DD"
        String estado = "ACTIVA";

        Matricula nueva = new Matricula(nextMatriculaId, idEstudiante, idSeccion, anioAcademico, fechaHoy, estado);
        this.listaMatriculas.add(nueva);
        this.matriculaRepo.guardarMatriculas(this.listaMatriculas);
        this.nextMatriculaId++;
        return nueva;
    }

    // --- 6. Métodos de Negocio (Lógica de "Obtener" y "Buscar") ---
    // (Para que la VISTA pueda mostrar los datos)

    public ArrayList<Apoderado> getTodosLosApoderados() {
        return this.listaApoderados;
    }
    
    public ArrayList<Estudiante> getTodosLosEstudiantes() {
        return this.listaEstudiantes;
    }
    
    public ArrayList<Profesor> getTodosLosProfesores() {
        return this.listaProfesores;
    }
    
    public ArrayList<Grado> getTodosLosGrados() {
        return this.listaGrados;
    }
    
    public ArrayList<Seccion> getTodasLasSecciones() {
        return this.listaSecciones;
    }
    
    public ArrayList<Matricula> getTodasLasMatriculas() {
        return this.listaMatriculas;
    }
    
    // --- Métodos "Buscadores" (helpers) ---
    
    public Estudiante buscarEstudiantePorId(int id) {
        for (Estudiante e : this.listaEstudiantes) {
            if (e.getIdEstudiante() == id) {
                return e;
            }
        }
        return null; // No encontrado
    }
    
    public Apoderado buscarApoderadoPorId(int id) {
        for (Apoderado a : this.listaApoderados) {
            if (a.getIdApoderado() == id) {
                return a;
            }
        }
        return null; // No encontrado
    }
    
    public Profesor buscarProfesorPorId(int id) {
        for (Profesor p : this.listaProfesores) {
            if (p.getIdProfesor() == id) {
                return p;
            }
        }
        return null; // No encontrado
    }

    public Grado buscarGradoPorId(int id) {
        for (Grado g : this.listaGrados) {
            if (g.getIdGrado() == id) {
                return g;
            }
        }
        return null; // No encontrado
    }
    
    public Seccion buscarSeccionPorId(int id) {
        for (Seccion s : this.listaSecciones) {
            if (s.getIdSeccion() == id) {
                return s;
            }
        }
        return null; // No encontrado
    }
}