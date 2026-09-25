import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ejer2 {
    public static void main(String[] args) {
        // Llevar la cuenta total de los errores
        int totalErrores = 0;        
        // Usamos try-with-resources para manejar las excepciones y el cierre de recursos
        try(BufferedReader reader = new BufferedReader(new FileReader("accesos.log"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("errores.log"))) {            
            String linea;            
            // Mientras haya líneas sigue leyendo
            while ((linea = reader.readLine()) != null) {                
                // Miramos si acaba en ERROR y sumamos 1 al contador
                if (linea.endsWith("ERROR")) {                    
                    writer.write(linea);
                    writer.newLine();                    
                    totalErrores++;
                }
            }            
            writer.write("Total de errores: " + totalErrores);
            System.out.println("Proceso terminado.");
            
        } catch (IOException e) {
            // Capturamos si falla el abrir, leer o escribir los ficheros
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}