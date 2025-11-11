package sistemamatriculas.model;

/**
 * Representa el nivel académico. Ej: "1ro Primaria", "5to Secundaria".
 */
public class Grado {
    
    private int idGrado;
    private String nombre; // Ej: "1ro Primaria"

    public Grado(int idGrado, String nombre) {
        this.idGrado = idGrado;
        this.nombre = nombre;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Grado [ID=" + idGrado + " | " + nombre + "]";
    }
}