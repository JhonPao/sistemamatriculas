package sistemamatriculas.model;

/**
 * Representa un "Aula" completa para un año académico.
 * Ej: "1ro Primaria 'A'" con 30 vacantes.
 * Reemplaza a Grado.java y Seccion.java.
 */
public class Aula {
    
    private int idAula;
    private String grado; // Ej: "1ro Primaria"
    private String seccion; // Ej: "A"
    private int idProfesorTutor;
    private int vacantesTotales;

    public Aula(int idAula, String grado, String seccion, int idProfesorTutor, int vacantesTotales) {
        this.idAula = idAula;
        this.grado = grado;
        this.seccion = seccion;
        this.idProfesorTutor = idProfesorTutor;
        this.vacantesTotales = vacantesTotales;
    }

    public int getIdAula() { return idAula; }
    public String getGrado() { return grado; }
    public String getSeccion() { return seccion; }
    public int getIdProfesorTutor() { return idProfesorTutor; }
    public int getVacantesTotales() { return vacantesTotales; }
    
    public String getNombreCompleto() {
        return grado + " '" + seccion + "'";
    }

    @Override
    public String toString() {
        return "Aula [ID=" + idAula + " | " + getNombreCompleto() + " | TutorID=" + idProfesorTutor + " | Vacantes=" + vacantesTotales + "]";
    }
}