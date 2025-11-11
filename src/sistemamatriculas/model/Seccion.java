package sistemamatriculas.model;

/**
 * Representa una división dentro de un Grado. Ej: "Sección A", "Sección B".
 * Aquí es donde realmente se inscribe un alumno.
 */
public class Seccion {
    
    private int idSeccion;
    private String nombre; // Ej: "A", "B", "Unica"
    private int idGrado; // A qué grado pertenece esta sección
    private int idProfesorTutor; // Quién es el tutor de esta sección

    public Seccion(int idSeccion, String nombre, int idGrado, int idProfesorTutor) {
        this.idSeccion = idSeccion;
        this.nombre = nombre;
        this.idGrado = idGrado;
        this.idProfesorTutor = idProfesorTutor;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public int getIdProfesorTutor() {
        return idProfesorTutor;
    }

    @Override
    public String toString() {
        // En el futuro, podríamos mostrar el nombre del grado en lugar del ID
        return "Sección [ID=" + idSeccion + " | " + nombre + " | GradoID=" + idGrado + "]";
    }
}
