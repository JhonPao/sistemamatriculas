package sistemamatriculas.repository;

import sistemamatriculas.model.Aula;
import java.io.*;
import java.util.ArrayList;

/**
 * Se encarga de guardar y leer los datos de Aulas (Aulas.txt).
 */
public class AulaRepository {

    private final String RUTA_ARCHIVO = "Aulas.txt";

    public ArrayList<Aula> cargarAulas() {
        ArrayList<Aula> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 5) {
                    Aula a = new Aula(
                        Integer.parseInt(d[0]), // idAula
                        d[1], // grado
                        d[2], // seccion
                        Integer.parseInt(d[3]), // idProfesorTutor
                        Integer.parseInt(d[4])  // vacantesTotales
                    );
                    lista.add(a);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error al cargar aulas: " + ex.getMessage());
        }
        return lista;
    }

    public void guardarAulas(ArrayList<Aula> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Aula a : lista) {
                String linea = a.getIdAula() + ";" +
                               a.getGrado() + ";" +
                               a.getSeccion() + ";" +
                               a.getIdProfesorTutor() + ";" +
                               a.getVacantesTotales();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Error al guardar aulas: " + ex.getMessage());
        }
    }
}