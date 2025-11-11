package sistemamatriculas.model;

/**
 * Representa una asignatura o curso. Ej: "Matemática", "Historia".
 */
public class Curso {
    
    private int idCurso;
    private String nombre;
    private String descripcion;

    public Curso(int idCurso, String nombre, String descripcion) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Curso [ID=" + idCurso + " | " + nombre + "]";
    }
}