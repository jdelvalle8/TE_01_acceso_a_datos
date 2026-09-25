import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Ejer3 {
    public static void main(String[] args) {
        byte[] esperada = {80, 75, 3, 4};
        byte[] leidos = new byte[esperada.length];        
        // Recorremos todos los archivos pasados como argumentos
        for (String rutaArchivo : args) {
        	// Mostramos por pantalla el archivo analizado en cada vuelta
            System.out.println("Analizando: " + rutaArchivo);            
            try (InputStream lectorFichero = new FileInputStream(rutaArchivo)) {                
                int cantidadLeida = lectorFichero.read(leidos);                
                if (cantidadLeida < esperada.length) {
                    System.out.println("El fichero contiene menos de cuatro bytes.");
                } else if (Arrays.equals(leidos, esperada)) {
                    System.out.println("La cabecera es compatible con un fichero ZIP.");
                } else {
                    System.out.println("La cabecera no corresponde a la firma esperada.");
                }
                
            } catch (IOException e) {
                System.err.println("No se pudo leer el archivo: " + e.getMessage());
            }            
            System.out.println();
        }
    }
}