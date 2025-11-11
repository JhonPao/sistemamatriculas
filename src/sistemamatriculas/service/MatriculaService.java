package sistemamatriculas.service;

import sistemamatriculas.model.*;
import sistemamatriculas.repository.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MatriculaService {

    // --- Repositorios ---
    private final ApoderadoRepository apoderadoRepo;
    private final EstudianteRepository estudianteRepo;
    private final ProfesorRepository profesorRepo;
    private final AulaRepository aulaRepo; // Reemplaza a Grado/Seccion
    private final MatriculaRepository matriculaRepo;
    // (Omitimos CursoRepository por simplicidad, se puede añadir luego)

    // --- Listas en Memoria ---
    private final ArrayList<Apoderado> listaApoderados;
    private final ArrayList<Estudiante> listaEstudiantes;
    private final ArrayList<Profesor> listaProfesores;
    private final ArrayList<Aula> listaAulas;
    private final ArrayList<Matricula> listaMatriculas;

    // --- Contadores de ID ---
    private int nextApoderadoId;
    private int nextEstudianteId;
    private int nextProfesorId;
    private int nextAulaId;
    private int nextMatriculaId;

    // --- Constructor ---
    public MatriculaService() {
        // Inicializa repositorios
        this.apoderadoRepo = new ApoderadoRepository();
        this.estudianteRepo = new EstudianteRepository();
        this.profesorRepo = new ProfesorRepository();
        this.aulaRepo = new AulaRepository();
        this.matriculaRepo = new MatriculaRepository();

        // Carga datos a memoria
        this.listaApoderados = apoderadoRepo.cargarApoderados();
        this.listaEstudiantes = estudianteRepo.cargarEstudiantes();
        this.listaProfesores = profesorRepo.cargarProfesores();
        this.listaAulas = aulaRepo.cargarAulas();
        this.listaMatriculas = matriculaRepo.cargarMatriculas();

        // Calcula IDs
        this.nextApoderadoId = calcularSiguienteId(listaApoderados, a -> a.getIdApoderado());
        this.nextEstudianteId = calcularSiguienteId(listaEstudiantes, e -> e.getIdEstudiante());
        this.nextProfesorId = calcularSiguienteId(listaProfesores, p -> p.getIdProfesor());
        this.nextAulaId = calcularSiguienteId(listaAulas, a -> a.getIdAula());
        this.nextMatriculaId = calcularSiguienteId(listaMatriculas, m -> m.getIdMatricula());
    }
    
    // Helper genérico para calcular el siguiente ID (optimización)
    private <T> int calcularSiguienteId(ArrayList<T> lista, java.util.function.ToIntFunction<T> getIdFunc) {
        if (lista.isEmpty()) {
            return 1;
        }
        return lista.stream().mapToInt(getIdFunc).max().orElse(0) + 1;
    }

    // --- LÓGICA: GESTIÓN DE ESTUDIANTES ---

    public Estudiante registrarEstudiante(String nombres, String apellidos, String dni, String fechaNac, int idApoderado) throws Exception {
        if (buscarApoderadoPorId(idApoderado) == null) {
            throw new Exception("Error: El Apoderado con ID " + idApoderado + " no existe.");
        }
        if (buscarEstudiantePorDni(dni) != null) {
            throw new Exception("Error: Ya existe un estudiante con el DNI " + dni);
        }
        
        Estudiante nuevo = new Estudiante(nextEstudianteId, nombres, apellidos, dni, fechaNac, idApoderado);
        this.listaEstudiantes.add(nuevo);
        this.estudianteRepo.guardarEstudiantes(this.listaEstudiantes);
        this.nextEstudianteId++;
        return nuevo;
    }
    
    public Estudiante buscarEstudiantePorDni(String dni) {
        for (Estudiante e : this.listaEstudiantes) {
            if (e.getDni().equals(dni)) {
                return e;
            }
        }
        return null; // No encontrado
    }
    
    public Apoderado registrarApoderado(String nombres, String apellidos, String dni, String telefono, String relacion) throws Exception {
        if (buscarApoderadoPorDni(dni) != null) {
            throw new Exception("Error: Ya existe un apoderado con el DNI " + dni);
        }
        Apoderado nuevo = new Apoderado(nextApoderadoId, nombres, apellidos, dni, telefono, relacion);
        this.listaApoderados.add(nuevo);
        this.apoderadoRepo.guardarApoderados(this.listaApoderados);
        this.nextApoderadoId++;
        return nuevo;
    }
    
    public Apoderado buscarApoderadoPorDni(String dni) {
        for (Apoderado a : this.listaApoderados) {
            if (a.getDni().equals(dni)) {
                return a;
            }
        }
        return null;
    }
/**
     * Registra un nuevo Profesor, lo guarda en memoria y en el archivo .txt
     */
    public Profesor registrarProfesor(String nombres, String apellidos, String dni, String especialidad) throws Exception {
        // Validación simple
        if (buscarProfesorPorDni(dni) != null) {
            throw new Exception("Error: Ya existe un profesor con el DNI " + dni);
        }
        
        Profesor nuevo = new Profesor(nextProfesorId, nombres, apellidos, dni, especialidad);
        this.listaProfesores.add(nuevo);
        this.profesorRepo.guardarProfesores(this.listaProfesores);
        this.nextProfesorId++;
        return nuevo;
    }
    
    public Profesor buscarProfesorPorDni(String dni) {
        for (Profesor p : this.listaProfesores) {
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }
    // --- LÓGICA: GESTIÓN ACADÉMICA ---
    
    public Aula crearAula(String grado, String seccion, int idProfesorTutor, int vacantes) throws Exception {
        if (buscarProfesorPorId(idProfesorTutor) == null) {
            throw new Exception("Error: El Profesor (Tutor) con ID " + idProfesorTutor + " no existe.");
        }
        // Validar que no exista un aula igual (mismo grado y seccion)
        for (Aula a : this.listaAulas) {
            if (a.getGrado().equalsIgnoreCase(grado) && a.getSeccion().equalsIgnoreCase(seccion)) {
                throw new Exception("Error: El aula " + a.getNombreCompleto() + " ya existe.");
            }
        }
        
        Aula nueva = new Aula(nextAulaId, grado, seccion, idProfesorTutor, vacantes);
        this.listaAulas.add(nueva);
        this.aulaRepo.guardarAulas(this.listaAulas);
        this.nextAulaId++;
        return nueva;
    }

    public ArrayList<Aula> getTodasLasAulas() {
        return this.listaAulas;
    }

    // --- LÓGICA: PROCESO DE MATRÍCULA ---
    
    public Matricula matricularEstudiante(int idEstudiante, int idAula, String anioAcademico) throws Exception {
        Estudiante est = buscarEstudiantePorId(idEstudiante);
        Aula aula = buscarAulaPorId(idAula);
        
        if (est == null) throw new Exception("Error: El Estudiante no existe.");
        if (aula == null) throw new Exception("Error: El Aula no existe.");
        
        // 1. Validar que el alumno no esté ya matriculado en este año
        for (Matricula m : this.listaMatriculas) {
            if (m.getIdEstudiante() == idEstudiante && m.getAnioAcademico().equals(anioAcademico) && m.getEstado().equals("ACTIVA")) {
                throw new Exception("Error: El estudiante ya tiene una matrícula activa para el año " + anioAcademico);
            }
        }
        
        // 2. Validar VACANTES (¡La lógica clave!)
        int vacantesDisponibles = getVacantesDisponibles(idAula);
        if (vacantesDisponibles <= 0) {
            throw new Exception("Error: No hay vacantes disponibles para el aula " + aula.getNombreCompleto());
        }

        String fechaHoy = LocalDate.now().toString(); // "YYYY-MM-DD"
        String estado = "ACTIVA";

        Matricula nueva = new Matricula(nextMatriculaId, idEstudiante, idAula, anioAcademico, fechaHoy, estado);
        this.listaMatriculas.add(nueva);
        this.matriculaRepo.guardarMatriculas(this.listaMatriculas);
        this.nextMatriculaId++;
        return nueva;
    }
    
    public Matricula anularMatricula(String dniEstudiante, String anio) throws Exception {
        Estudiante est = buscarEstudiantePorDni(dniEstudiante);
        if (est == null) throw new Exception("No se encontró estudiante con DNI " + dniEstudiante);
        
        Matricula matriculaActiva = null;
        for (Matricula m : this.listaMatriculas) {
            if (m.getIdEstudiante() == est.getIdEstudiante() && m.getAnioAcademico().equals(anio) && m.getEstado().equals("ACTIVA")) {
                matriculaActiva = m;
                break;
            }
        }
        
        if (matriculaActiva == null) {
            throw new Exception("No se encontró matrícula activa para el DNI " + dniEstudiante + " en el año " + anio);
        }
        
        // Modifica el estado
        matriculaActiva.setEstado("ANULADA");
        
        // Persiste el cambio
        this.matriculaRepo.guardarMatriculas(this.listaMatriculas);
        return matriculaActiva;
    }
    
    // --- LÓGICA: REPORTES Y CONSULTAS ---
    
    /**
     * Calcula las vacantes disponibles para un aula.
     * @return vacantes totales - matrículas activas
     */
    public int getVacantesDisponibles(int idAula) {
        Aula aula = buscarAulaPorId(idAula);
        if (aula == null) return 0;
        
        int matriculadosActivos = 0;
        for (Matricula m : this.listaMatriculas) {
            if (m.getIdAula() == idAula && m.getEstado().equals("ACTIVA")) {
                matriculadosActivos++;
            }
        }
        return aula.getVacantesTotales() - matriculadosActivos;
    }

    /**
     * Obtiene la Ficha de Matrícula activa de un estudiante por DNI y año.
     */
    public Matricula getFichaMatricula(String dniEstudiante, String anio) {
        Estudiante est = buscarEstudiantePorDni(dniEstudiante);
        if (est == null) return null;
        
        for (Matricula m : this.listaMatriculas) {
            if (m.getIdEstudiante() == est.getIdEstudiante() && m.getAnioAcademico().equals(anio)) {
                return m; // Retorna la primera que encuentra (activa o anulada)
            }
        }
        return null;
    }

    /**
     * Reporte de alumnos matriculados en un aula específica.
     */
    public ArrayList<Estudiante> getAlumnosPorAula(int idAula) {
        ArrayList<Estudiante> alumnos = new ArrayList<>();
        for (Matricula m : this.listaMatriculas) {
            // Solo matrículas activas de esa aula
            if (m.getIdAula() == idAula && m.getEstado().equals("ACTIVA")) {
                Estudiante e = buscarEstudiantePorId(m.getIdEstudiante());
                if (e != null) {
                    alumnos.add(e);
                }
            }
        }
        return alumnos;
    }
    
    public ArrayList<Matricula> getMatriculasActivas(String anio) {
        ArrayList<Matricula> activas = new ArrayList<>();
        for (Matricula m : this.listaMatriculas) {
            if (m.getAnioAcademico().equals(anio) && m.getEstado().equals("ACTIVA")) {
                activas.add(m);
            }
        }
        return activas;
    }

    // --- Métodos "Buscadores" (helpers) ---
    
    public Estudiante buscarEstudiantePorId(int id) {
        return listaEstudiantes.stream().filter(e -> e.getIdEstudiante() == id).findFirst().orElse(null);
    }
    public Apoderado buscarApoderadoPorId(int id) {
        return listaApoderados.stream().filter(a -> a.getIdApoderado() == id).findFirst().orElse(null);
    }
    public Profesor buscarProfesorPorId(int id) {
        return listaProfesores.stream().filter(p -> p.getIdProfesor() == id).findFirst().orElse(null);
    }
    public Aula buscarAulaPorId(int id) {
        return listaAulas.stream().filter(a -> a.getIdAula() == id).findFirst().orElse(null);
    }

    // (Getters para las listas, por si la VISTA los necesita)
    public ArrayList<Apoderado> getTodosLosApoderados() { return this.listaApoderados; }
    public ArrayList<Estudiante> getTodosLosEstudiantes() { return this.listaEstudiantes; }
    public ArrayList<Profesor> getTodosLosProfesores() { return this.listaProfesores; }
}