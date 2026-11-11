import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorArchivos {

    final static String filePath = "poema.txt";
    
    public static void main(String[] args) {

        String[] palabras = obtenerPalabras();

    }

    public static int obtenerContadorPalabras() {
        int contadorPalabras = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contadorPalabras += linea.split(" ").length;
                for (int i = 0; i < linea.length(); i++) {
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return contadorPalabras;
    }

    public static String[] obtenerPalabras() {

        int cantidadPalabras = obtenerContadorPalabras();

        String[] palabras = new String[cantidadPalabras];
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            int index = 0;
            while ((linea = br.readLine()) != null) {
                String[] palabrasLinea = linea.split(" ");
                for (String palabra : palabrasLinea) {
                    palabra = palabra.replace(".", "");
                    palabra = palabra.replace(",", "");
                    palabras[index++] = palabra;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        for (String palabra : palabras) {
            System.out.println(palabra);
        }

        return palabras;
    }

}