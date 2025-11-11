package sistemamatriculas.repository;

import sistemamatriculas.model.Apoderado;
import java.io.*;
import java.util.ArrayList;

/**
 * Se encarga de guardar y leer los datos de Apoderados
 * desde un archivo de texto (Apoderados.txt).
 */
public class ApoderadoRepository {
    
    // Ruta del archivo donde se guardarán los datos
    private final String RUTA_ARCHIVO = "Apoderados.txt";

    /**
     * Carga la lista completa de apoderados desde Apoderados.txt
     * @return Una lista de Apoderados. Si el archivo no existe, retorna una lista vacía.
     */
    public ArrayList<Apoderado> cargarApoderados() {
        ArrayList<Apoderado> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        
        // Si el archivo no existe, retornamos la lista vacía
        if (!archivo.exists()) {
            return lista;
        }
        
        // Usamos try-with-resources para que el BufferedReader se cierre solo
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Separamos los datos por punto y coma
                String[] d = linea.split(";");
                // Verificamos que la línea tenga todos los datos esperados
                if (d.length >= 6) {
                    Apoderado a = new Apoderado(
                        Integer.parseInt(d[0]), // id
                        d[1], // nombres
                        d[2], // apellidos
                        d[3], // dni
                        d[4], // telefono
                        d[5]  // relacion
                    );
                    lista.add(a);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            // | NumberFormatException es por si Integer.parseInt falla
            System.err.println("Error al cargar apoderados: " + ex.getMessage());
        }
        return lista;
    }

    /**
     * Guarda la lista completa de apoderados en Apoderados.txt.
     * Sobrescribe el archivo con la nueva lista.
     * @param lista La lista de Apoderados a guardar.
     */
    public void guardarApoderados(ArrayList<Apoderado> lista) {
        // Usamos try-with-resources para que el BufferedWriter se cierre solo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Apoderado a : lista) {
                // Creamos la línea de texto con los datos separados por punto y coma
                String linea = a.getIdApoderado() + ";" +
                               a.getNombres() + ";" +
                               a.getApellidos() + ";" +
                               a.getDni() + ";" +
                               a.getTelefono() + ";" +
                               a.getRelacion();
                bw.write(linea);
                bw.newLine(); // Escribimos un salto de línea
            }
        } catch (IOException ex) {
            System.err.println("Error al guardar apoderados: " + ex.getMessage());
        }
    }
}