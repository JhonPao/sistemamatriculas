package sistemamatriculas.repository;

import sistemamatriculas.model.Curso;
import java.io.*;
import java.util.ArrayList;

/**
 * Se encarga de guardar y leer los datos de Cursos (Cursos.txt).
 */
public class CursoRepository {
    
    private final String RUTA_ARCHIVO = "Cursos.txt";

    public ArrayList<Curso> cargarCursos() {
        ArrayList<Curso> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 3) {
                    Curso c = new Curso(
                        Integer.parseInt(d[0]), // idCurso
                        d[1], // nombre
                        d[2]  // descripcion
                    );
                    lista.add(c);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error al cargar cursos: " + ex.getMessage());
        }
        return lista;
    }

    public void guardarCursos(ArrayList<Curso> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Curso c : lista) {
                String linea = c.getIdCurso() + ";" +
                               c.getNombre() + ";" +
                               c.getDescripcion();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Error al guardar cursos: " + ex.getMessage());
        }
    }
}