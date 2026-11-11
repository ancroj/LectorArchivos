import java.io.BufferedReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LectorArchivos {

    final static String filePath = "poema.txt";
    
    public static void main(String[] args) throws ErrorCargaPoemaException, PosicionPoemaInvalidaException {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo a leer (por defecto: poema.txt): ");
        String direccionArchivo = scanner.nextLine();
        String[] palabras = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(direccionArchivo));
            palabras = obtenerPalabras(br);
        } catch (IOException e) {
            throw new ErrorCargaPoemaException("Error al cargar el archivo: " + e.getMessage());
        }

        try {
            System.out.println("Ingrese el indice de una palabra a consultar: ");
            int indice = scanner.nextInt();
            System.out.println("La palabra en el indice " + indice + " es: " + palabras[indice]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new PosicionPoemaInvalidaException("Indice fuera de rango: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Ingrese un valor numérico para el indice: " + e.getMessage());
        }
    }

    public static int obtenerContadorPalabras(BufferedReader br) throws IOException {

        int contadorPalabras = 0;
        String linea;
        while ((linea = br.readLine()) != null) {
            contadorPalabras += linea.split(" ").length;
            for (int i = 0; i < linea.length(); i++) {
            }
        }

        return contadorPalabras;
    }

    public static String[] obtenerPalabras(BufferedReader br) throws IOException {

        int cantidadPalabras = obtenerContadorPalabras(br);

        String[] palabras = new String[cantidadPalabras];
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

        for (String palabra : palabras) {
            System.out.println(palabra);
        }

        return palabras;
    }

}