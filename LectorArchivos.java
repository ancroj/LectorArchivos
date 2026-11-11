import java.io.BufferedReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

public class LectorArchivos {

    // Ejecucion principal del programa
    public static void main(String[] args) throws ErrorCargaPoemaException, PosicionPoemaInvalidaException {
        
        // Se le da al usuario el poder elegir el nombre del archivo
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo a leer (por defecto: poema.txt): ");
        String direccionArchivo = scanner.nextLine();
        String[] palabras = null;
        int opcion = 0;

        // Aquí se obtiene la cantidad de palabras en el archivo y se llena el vector
        try {
            int contadorPalabras = obtenerContadorPalabras(direccionArchivo);
            palabras = obtenerPalabras(direccionArchivo, contadorPalabras);
        } catch (IOException e) { // Si no existe el archivo se da la excepción
            throw new ErrorCargaPoemaException("Error al cargar el archivo: " + e.getMessage());
        }

        // Bucle para el menú
        do {
            System.out.println("---------------------- ");
            System.out.println("Ingresa una opción del menú: \n1. Consultar el vector por índices.\n2. Consultar el vector por palabras.\n0. Salir");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                // En caso de elegir 1 el usuario puede consultar que palabra hay en este indice y se manejan las posibles excepciones
                case 1:
                    try {
                        consultarIndices(palabras, scanner);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new PosicionPoemaInvalidaException("Indice fuera de rango: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Ingrese un valor numérico para el indice: " + e.getMessage());
                    } catch (PalabraMuyCortaException e) {
                        System.out.println("La palabra elegida es muy corta: " + e.getMessage());
                    }
                    break;
            
                // En case de elegir 2 el usuario puede consultar si la palabra existe y se maneja la excepción
                case 2:
                    try {
                        consultarPalabra(palabras, scanner);
                    } catch (PalabraNoEncontradaException e) {
                        System.out.println("La palabra no se encontró: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                // Para salir del menú y para validar la opción elegida
                case 0:
                    break;

                default:
                    System.out.println("Ingrese una opción válida");
            }
        } while (opcion != 0);
    }

    // Método para contar palabras, cuenta cada linea y las agrega al contador global
    public static int obtenerContadorPalabras(String direccionArchivo) throws IOException {
        
        int contadorPalabras = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(direccionArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contadorPalabras += linea.split(" ").length;
                for (int i = 0; i < linea.length(); i++) {
                }
            }
        }

        return contadorPalabras;
    }
    
    // Metodo para obtener las palabras en un vector fijo con la cantidad de palabras existentes
    public static String[] obtenerPalabras(String direccionArchivo, int cantidadPalabras) throws IOException {

        String[] palabras = new String[cantidadPalabras];

        try (BufferedReader br = new BufferedReader(new FileReader(direccionArchivo))) 
        {
            String linea;
            int index = 0;
            while ((linea = br.readLine()) != null) {
                String[] palabrasLinea = linea.split(" ");
                for (String palabra : palabrasLinea) {
                    // Se recorre cada palabra y se le quitan los puntos y comas antes de agregarla al vector de palabras
                    palabra = palabra.replace(".", "");
                    palabra = palabra.replace(",", "");
                    palabras[index++] = palabra;
                }
            }
        }

        return palabras;
    }

    // Metodo para consultar la palabra en un indice dado
    public static void consultarIndices(String[] palabras, Scanner scanner) throws PalabraMuyCortaException {
        System.out.println("Ingrese el indice de una palabra a consultar: ");
        int indice = Integer.parseInt(scanner.nextLine());
        System.out.println("La palabra en el indice " + indice + " es: " + palabras[indice]);

        // Si la palabra elegida es menor a 4 letras se da esta excepción
        if (palabras[indice].length() < 4) {
            throw new PalabraMuyCortaException("La palabra escogida es menor de 4 letras");
        }
    }

    // Metodo para consultar si una palabra existe en el vector
    public static void consultarPalabra(String[] palabras, Scanner scanner) throws PalabraNoEncontradaException {
        System.out.println("Ingrese la palabra que desea buscar:");
        String buscada = scanner.nextLine().trim();
        boolean encontrada = false;

        for (int i = 0; i < palabras.length; i++) {
            if (palabras[i].equalsIgnoreCase(buscada)) {
                System.out.println("Palabra " + buscada + " encontrada en el índice " + i);
                encontrada = true;
            }
        }

        // Si la palabra no está se da esta excepción
        if (!encontrada) {
            throw new PalabraNoEncontradaException("La palabra no fue encontrada");
        }
    }
}

// Clases de error
class ErrorCargaPoemaException extends Exception {
    public ErrorCargaPoemaException(String message) {
        super(message);
    }
}

class PosicionPoemaInvalidaException extends Exception {
    public PosicionPoemaInvalidaException(String message) {
        super(message);
    }
}

class PalabraMuyCortaException extends Exception {
    public PalabraMuyCortaException(String message) {
        super(message);
    }
}

class PalabraNoEncontradaException extends Exception {
    public PalabraNoEncontradaException(String message) {
        super(message);
    }
}