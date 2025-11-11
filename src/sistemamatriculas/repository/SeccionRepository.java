package sistemamatriculas.repository;

import sistemamatriculas.model.Seccion;
import java.io.*;
import java.util.ArrayList;

/**
 * Se encarga de guardar y leer los datos de Secciones (Secciones.txt).
 */
public class SeccionRepository {
    
    private final String RUTA_ARCHIVO = "Secciones.txt";

    public ArrayList<Seccion> cargarSecciones() {
        ArrayList<Seccion> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 4) {
                    Seccion s = new Seccion(
                        Integer.parseInt(d[0]), // idSeccion
                        d[1], // nombre
                        Integer.parseInt(d[2]), // idGrado
                        Integer.parseInt(d[3])  // idProfesorTutor
                    );
                    lista.add(s);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error al cargar secciones: " + ex.getMessage());
        }
        return lista;
    }

    public void guardarSecciones(ArrayList<Seccion> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Seccion s : lista) {
                String linea = s.getIdSeccion() + ";" +
                               s.getNombre() + ";" +
                               s.getIdGrado() + ";" +
                               s.getIdProfesorTutor();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Error al guardar secciones: " + ex.getMessage());
        }
    }
}