import java.io.IOException;
import java.io.RandomAccessFile;

public class ListarCategoria {
    static final int LONG_CODIGO = 8;
    static final int LONG_NOMBRE = 20;
    static final int LONG_CATEGORIA = 12;    
    // Tamaño total de cada registro (96 bytes)
    static final int TAM_REGISTRO = Integer.BYTES 
                                  + (LONG_CODIGO * Character.BYTES)
                                  + (LONG_NOMBRE * Character.BYTES)
                                  + (LONG_CATEGORIA * Character.BYTES)
                                  + Integer.BYTES 
                                  + Double.BYTES;

    public static void main(String[] args) {
        // Comprobamos que nos pasan exactamente una palabra
        if (args.length != 1) {
            System.out.println("Debes indicar una categoría para buscar.");
            return;
        }
        // Guardamos la palabra
        String categoriaBuscada = args[0];
        int equiposEncontrados = 0;

        // Abrimos el archivo en modo lectura
        try (RandomAccessFile raf = new RandomAccessFile("inventario.dat", "r")) {            
            // Calculamos cuántos registros hay en total para saber cuántas vueltas dará el bucle
            long totalRegistros = raf.length() / TAM_REGISTRO;
            System.out.println("EQUIPOS DE LA CATEGORÍA: " + categoriaBuscada.toUpperCase());
            // Bucle que lee el archivo desde el primer byte hasta el último registro
            for (int i = 0; i < totalRegistros; i++) {
                
                // Leemos los datos en orden
            	// ID
                int id = raf.readInt();
                // Código
                String codigo = "";
                for (int j = 0; j < LONG_CODIGO; j++) {
                    codigo += raf.readChar();
                }
                // Nombre
                String nombre = "";
                for (int j = 0; j < LONG_NOMBRE; j++) {
                    nombre += raf.readChar();
                }
                // Categoría
                String categoria = "";
                for (int j = 0; j < LONG_CATEGORIA; j++) {
                    categoria += raf.readChar();
                }
                // Stock
                int stock = raf.readInt();
                // Precio
                double precio = raf.readDouble();
                // Quitamos los espacios de relleno para poder comparar bien las palabras
                categoria = categoria.trim();                
                // Compara sin importar si son mayúsculas o minúsculas
                if (categoria.equalsIgnoreCase(categoriaBuscada)) {                    
                    // Si hay coincidencia, mostramos los datos
                    System.out.println("ID: " + id 
                                     + " Código: " + codigo 
                                     + " Nombre: " + nombre
                                     + " Stock: " + stock 
                                     + " Precio: " + precio + "€");
                    equiposEncontrados++;
                }
            }
            
            // Resumen
            System.out.println();
            if (equiposEncontrados > 0) {
                System.out.println("Total de equipos encontrados: " + equiposEncontrados);
            } else {
                System.out.println("No se ha encontrado ningún equipo en la categoría '" + categoriaBuscada + "'.");
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}