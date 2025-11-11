package sistemamatriculas.repository;

import sistemamatriculas.model.Matricula;
import java.io.*;
import java.util.ArrayList;

/**
 * Se encarga de guardar y leer los datos de Matrículas (Matriculas.txt).
 */
public class MatriculaRepository {
    
    private final String RUTA_ARCHIVO = "Matriculas.txt";

    public ArrayList<Matricula> cargarMatriculas() {
        ArrayList<Matricula> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 6) {
                    Matricula m = new Matricula(
                        Integer.parseInt(d[0]), // idMatricula
                        Integer.parseInt(d[1]), // idEstudiante
                        Integer.parseInt(d[2]), // idSeccion
                        d[3], // anioAcademico
                        d[4], // fechaMatricula
                        d[5]  // estado
                    );
                    lista.add(m);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error al cargar matrículas: " + ex.getMessage());
        }
        return lista;
    }

    public void guardarMatriculas(ArrayList<Matricula> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Matricula m : lista) {
                String linea = m.getIdMatricula() + ";" +
                               m.getIdEstudiante() + ";" +
                               m.getIdSeccion() + ";" +
                               m.getAnioAcademico() + ";" +
                               m.getFechaMatricula() + ";" +
                               m.getEstado();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Error al guardar matrículas: " + ex.getMessage());
        }
    }
}