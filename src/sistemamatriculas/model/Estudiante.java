package sistemamatriculas.model;

/**
 * Representa a un Estudiante del colegio.
 * Contiene una referencia (ID) a su Apoderado.
 */
public class Estudiante {

    // --- Atributos ---
    private int idEstudiante;
    private String nombres;
    private String apellidos;
    private String dni;
    private String fechaNacimiento;
    private int idApoderado; // Esta es la "Clave Foránea" que lo conecta con Apoderado

    // --- Constructor ---
    public Estudiante(int idEstudiante, String nombres, String apellidos, String dni, String fechaNacimiento, int idApoderado) {
        this.idEstudiante = idEstudiante;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.idApoderado = idApoderado;
    }

    // --- Getters ---
    public int getIdEstudiante() {
        return idEstudiante;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getIdApoderado() {
        return idApoderado;
    }
    
    // --- Métodos de utilidad ---

    public String getNombreCompleto() {
        return this.nombres + " " + this.apellidos;
    }
    
    @Override
    public String toString() {
        return "Estudiante [ID=" + idEstudiante + " | " + getNombreCompleto() + " | DNI=" + dni + "]";
    }
}