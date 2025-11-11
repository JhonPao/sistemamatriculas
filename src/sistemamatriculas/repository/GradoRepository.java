package sistemamatriculas.repository;

import sistemamatriculas.model.Grado;
import java.io.*;
import java.util.ArrayList;

/**
 * Se encarga de guardar y leer los datos de Grados (Grados.txt).
 */
public class GradoRepository {
    
    private final String RUTA_ARCHIVO = "Grados.txt";

    public ArrayList<Grado> cargarGrados() {
        ArrayList<Grado> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 2) {
                    Grado g = new Grado(
                        Integer.parseInt(d[0]), // idGrado
                        d[1]  // nombre
                    );
                    lista.add(g);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error al cargar grados: " + ex.getMessage());
        }
        return lista;
    }

    public void guardarGrados(ArrayList<Grado> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Grado g : lista) {
                String linea = g.getIdGrado() + ";" +
                               g.getNombre();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Error al guardar grados: " + ex.getMessage());
        }
    }
}