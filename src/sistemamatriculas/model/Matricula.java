package sistemamatriculas.model;

/**
 * Representa el acto de Matrícula.
 * Conecta a un Estudiante con un Aula para un año.
 */
public class Matricula {
    
    private int idMatricula;
    private int idEstudiante; // A quién matriculamos
    private int idAula; // Dónde lo matriculamos (reemplaza a idSeccion)
    private String anioAcademico; // Ej: "2025"
    private String fechaMatricula; // Ej: "2025-03-01"
    private String estado; // Ej: "ACTIVA", "ANULADA", "RETIRADO"

    public Matricula(int idMatricula, int idEstudiante, int idAula, String anioAcademico, String fechaMatricula, String estado) {
        this.idMatricula = idMatricula;
        this.idEstudiante = idEstudiante;
        this.idAula = idAula;
        this.anioAcademico = anioAcademico;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
    }
    
    // --- Getters ---
    public int getIdMatricula() { return idMatricula; }
    public int getIdEstudiante() { return idEstudiante; }
    public int getIdAula() { return idAula; }
    public String getAnioAcademico() { return anioAcademico; }
    public String getFechaMatricula() { return fechaMatricula; }
    public String getEstado() { return estado; }

    // --- Setter (solo para anular/modificar estado) ---
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Matricula [ID=" + idMatricula + " | IDEstudiante=" + idEstudiante + " | AulaID=" + idAula + " | Año=" + anioAcademico + " | Estado=" + estado + "]";
    }
}