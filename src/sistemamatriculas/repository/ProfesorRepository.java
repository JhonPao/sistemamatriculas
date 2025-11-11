package sistemamatriculas.repository;

import sistemamatriculas.model.Profesor;
import java.io.*;
import java.util.ArrayList;

/**
 * Se encarga de guardar y leer los datos de Profesores (Profesores.txt).
 */
public class ProfesorRepository {
    
    private final String RUTA_ARCHIVO = "Profesores.txt";

    public ArrayList<Profesor> cargarProfesores() {
        ArrayList<Profesor> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 5) {
                    Profesor p = new Profesor(
                        Integer.parseInt(d[0]), // idProfesor
                        d[1], // nombres
                        d[2], // apellidos
                        d[3], // dni
                        d[4]  // especialidad
                    );
                    lista.add(p);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error al cargar profesores: " + ex.getMessage());
        }
        return lista;
    }

    public void guardarProfesores(ArrayList<Profesor> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Profesor p : lista) {
                String linea = p.getIdProfesor() + ";" +
                               p.getNombres() + ";" +
                               p.getApellidos() + ";" +
                               p.getDni() + ";" +
                               p.getEspecialidad();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Error al guardar profesores: " + ex.getMessage());
        }
    }
}