package sistemamatriculas.model;

/**
 * Representa a un Apoderado o Tutor legal del estudiante.
 * Esta clase es un POJO (Plain Old Java Object).
 * Solo contiene datos, constructor y getters/setters.
 */
public class Apoderado {
    
    // --- Atributos ---
    private int idApoderado;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String relacion; // Ej: "Padre", "Madre", "Tutor"

    // --- Constructor ---
    public Apoderado(int idApoderado, String nombres, String apellidos, String dni, String telefono, String relacion) {
        this.idApoderado = idApoderado;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.relacion = relacion;
    }

    // --- Getters (Métodos de acceso) ---
    // (No creamos Setters para hacer nuestros objetos "inmutables" por ahora)
    
    public int getIdApoderado() {
        return idApoderado;
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

    public String getTelefono() {
        return telefono;
    }

    public String getRelacion() {
        return relacion;
    }

    // --- Métodos de utilidad ---
    
    /**
     * Devuelve el nombre completo del apoderado.
     * @return String con "Nombres Apellidos"
     */
    public String getNombreCompleto() {
        return this.nombres + " " + this.apellidos;
    }

    @Override
    public String toString() {
        return "Apoderado [ID=" + idApoderado + " | " + getNombreCompleto() + " | DNI=" + dni + "]";
    }
}