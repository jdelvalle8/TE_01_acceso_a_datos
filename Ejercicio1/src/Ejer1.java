import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ejer1 {
    public static void main(String[] args) {    	
        // Usamos try-with-resources para manejar las excepciones y el cierre de recursos
        try (FileReader reader = new FileReader("entrada.txt");
            FileWriter writer = new FileWriter("salida.txt")) {        	
            int dato;
            // Mientras pueda leer seguirá haciéndolo
            while ((dato = reader.read()) != -1) {                
                // Convertimos el int a char para poder transformarlo
                char caracter = (char) dato;                
                // Aplicamos las transformaciones
                if (Character.isDigit(caracter)) {
                    // Si es un número escribimos un #
                    writer.write('#');
                } else if (Character.isLetter(caracter)) {
                    // Si es una letra la pasamos a mayúscula
                    writer.write(Character.toUpperCase(caracter));
                } else {
                	// El resto los mantenemos igual
                    writer.write(caracter);
                }
            }            
            System.out.println("Proceso terminado.");
            
        } catch (IOException e) {
            // Capturamos el error si falla la entrada o salida
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}
