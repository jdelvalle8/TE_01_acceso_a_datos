import java.io.IOException;
import java.io.RandomAccessFile;

public class ActualizarStock {
    static final int LONG_CODIGO = 8;
    static final int LONG_NOMBRE = 20;
    static final int LONG_CATEGORIA = 12;    
    // Tamaño total del registro (96 bytes)
    static final int TAM_REGISTRO = Integer.BYTES + (LONG_CODIGO * Character.BYTES) + (LONG_NOMBRE * Character.BYTES) + (LONG_CATEGORIA * Character.BYTES) + Integer.BYTES + Double.BYTES; 
    // Saltos para acceder directamente a los campos
    static final int SALTOS_NOMBRE = Integer.BYTES + (LONG_CODIGO * Character.BYTES); 
    static final int SALTOS_STOCK = SALTOS_NOMBRE + (LONG_NOMBRE * Character.BYTES) + (LONG_CATEGORIA * Character.BYTES);

    public static void main(String[] args) {        
        // Validación de número de argumentos recomendado por la IA
        if (args.length != 2) {
            System.out.println("Error: Debes indicar el ID y el nuevo stock separados por un espacio.");
            return;
        }
        int idObjetivo;
        int nuevoStock;

        try {
            // Conversión de argumentos a enteros
            idObjetivo = Integer.parseInt(args[0]);
            nuevoStock = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
        	// Validación de que deben ser números y no letras recomendado por la IA
            System.out.println("Error: El ID y el stock deben ser números.");
            return;
        }
        // Validación para evitar stocks negativos
        if (nuevoStock < 0) {
            System.out.println("Error: El stock no puede ser un valor negativo.");
            return;
        }

        // Abrimos el archivo en modo lectura/escritura con try-with-resources
        try (RandomAccessFile raf = new RandomAccessFile("inventario.dat", "rw")) {
            
            // Comprobamos si el registro existe
            long totalRegistros = raf.length() / TAM_REGISTRO;
            
            if (idObjetivo < 1 || idObjetivo > totalRegistros) {
                System.out.println("No existe el equipo con ID " + idObjetivo);
                return;
            }

            // Cálculo de la posición exacta del registro
            long posicionRegistro = (idObjetivo - 1) * (long) TAM_REGISTRO;

            // Nos posicionamos y leemos el nombre del equipo
            raf.seek(posicionRegistro + SALTOS_NOMBRE);
            String nombreEquipo = "";
            for (int i = 0; i < LONG_NOMBRE; i++) {
                nombreEquipo += raf.readChar();
            }
            nombreEquipo = nombreEquipo.trim();

            // Nos posicionamos y leemos el stock anterior
            raf.seek(posicionRegistro + SALTOS_STOCK);
            int stockAnterior = raf.readInt();

            // Reposicionamos para sobrescribir únicamente el stock
            raf.seek(posicionRegistro + SALTOS_STOCK);
            raf.writeInt(nuevoStock);

            // Calculamos la variación y mostramos el resumen por pantalla
            int variacion = nuevoStock - stockAnterior;
            System.out.println("ACTUALIZACIÓN DE STOCK");
            System.out.println("Equipo: " + nombreEquipo);
            System.out.println("Stock anterior: " + stockAnterior);
            System.out.println("Stock nuevo: " + nuevoStock);
            System.out.println("Variación: " + (variacion > 0 ? "+" + variacion : variacion));

        } catch (IOException e) {
            System.err.println("Error al acceder al archivo: " + e.getMessage());
        }
    }
}