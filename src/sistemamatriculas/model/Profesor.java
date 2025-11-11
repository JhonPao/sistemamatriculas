package sistemamatriculas.model;

/**
 * Representa a un Docente del colegio.
 */
public class Profesor {
    
    // --- Atributos ---
    private int idProfesor;
    private String nombres;
    private String apellidos;
    private String dni;
    private String especialidad; // Ej: "Matemáticas", "Primaria"

    // --- Constructor ---
    public Profesor(int idProfesor, String nombres, String apellidos, String dni, String especialidad) {
        this.idProfesor = idProfesor;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.especialidad = especialidad;
    }

    // --- Getters ---
    public int getIdProfesor() {
        return idProfesor;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public String getEspecialidad() {
        return especialidad;
    }
    
    public String getNombreCompleto() {
        return this.nombres + " " + this.apellidos;
    }

    @Override
    public String toString() {
        return "Profesor [ID=" + idProfesor + " | " + getNombreCompleto() + " | Esp=" + especialidad + "]";
    }
}