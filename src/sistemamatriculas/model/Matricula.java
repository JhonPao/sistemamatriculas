package sistemamatriculas.model;

/**
 * Representa el acto de Matrícula.
 * Es la clase más importante, ya que conecta a un Estudiante
 * con una Sección en un año académico específico.
 */
public class Matricula {
    
    private int idMatricula;
    private int idEstudiante; // A quién matriculamos
    private int idSeccion; // Dónde lo matriculamos
    private String anioAcademico; // Ej: "2025"
    private String fechaMatricula; // Ej: "2025-03-01"
    private String estado; // Ej: "ACTIVA", "RETIRADO", "TRASLADADO"

    public Matricula(int idMatricula, int idEstudiante, int idSeccion, String anioAcademico, String fechaMatricula, String estado) {
        this.idMatricula = idMatricula;
        this.idEstudiante = idEstudiante;
        this.idSeccion = idSeccion;
        this.anioAcademico = anioAcademico;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
    }

    // --- Getters ---
    public int getIdMatricula() {
        return idMatricula;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public String getAnioAcademico() {
        return anioAcademico;
    }

    public String getFechaMatricula() {
        return fechaMatricula;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Matricula [ID=" + idMatricula + " | EstudianteID=" + idEstudiante + " | SeccionID=" + idSeccion + " | Año=" + anioAcademico + "]";
    }
}