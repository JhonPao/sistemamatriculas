package sistemamatriculas.repository;

import sistemamatriculas.model.Estudiante;
import java.io.*;
import java.util.ArrayList;

/**
 * Se encarga de guardar y leer los datos de Estudiantes (Estudiantes.txt).
 */
public class EstudianteRepository {
    
    private final String RUTA_ARCHIVO = "Estudiantes.txt";

    public ArrayList<Estudiante> cargarEstudiantes() {
        ArrayList<Estudiante> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 6) {
                    Estudiante e = new Estudiante(
                        Integer.parseInt(d[0]), // idEstudiante
                        d[1], // nombres
                        d[2], // apellidos
                        d[3], // dni
                        d[4], // fechaNacimiento
                        Integer.parseInt(d[5])  // idApoderado
                    );
                    lista.add(e);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error al cargar estudiantes: " + ex.getMessage());
        }
        return lista;
    }

    public void guardarEstudiantes(ArrayList<Estudiante> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Estudiante e : lista) {
                String linea = e.getIdEstudiante() + ";" +
                               e.getNombres() + ";" +
                               e.getApellidos() + ";" +
                               e.getDni() + ";" +
                               e.getFechaNacimiento() + ";" +
                               e.getIdApoderado();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Error al guardar estudiantes: " + ex.getMessage());
        }
    }
}