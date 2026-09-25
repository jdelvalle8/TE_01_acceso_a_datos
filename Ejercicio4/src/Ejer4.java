import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejer4 {
	static final int LONG_CODIGO = 8;
    static final int LONG_NOMBRE = 20;
    static final int LONG_CATEGORIA = 12;
    // Calculamos el tamaño del registro en bytes.
    static final int TAM_REGISTRO = Integer.BYTES + (LONG_CODIGO * Character.BYTES) + (LONG_NOMBRE * Character.BYTES) + (LONG_CATEGORIA * Character.BYTES)+ Integer.BYTES + Double.BYTES;

    // Para garantizar que las cadenas ocupen siempre lo mismo
    static void escribirCadenaFija(RandomAccessFile raf, String texto, int longitud) throws IOException {
        // Recomendación de la IA para evitar que el programa falle si el valor es null
    	String valor = texto == null ? "" : texto;
        if (valor.length() > longitud) {
            valor = valor.substring(0, longitud);
        }
        for (int i = 0; i < longitud; i++) {
            char c = i < valor.length() ? valor.charAt(i) : ' ';
            raf.writeChar(c);
        }
    }

    public static void main(String[] args) {
        
        // Arrays del archivo txt
        int[] ids = {1, 2, 3, 4, 5, 6, 7, 8};
        String[] codigos = {"EQ0001", "EQ0002", "EQ0003", "EQ0004", "EQ0005", "EQ0006", "EQ0007", "EQ0008"};
        String[] nombres = {"Portatil Lenovo", "Monitor Dell 24", "Teclado Logitech", "Raton Inalambrico", "Webcam Logitech", "Proyector Epson", "Dock USB-C", "Auriculares Jabra"};
        String[] categorias = {"portatil", "monitor", "periferico", "periferico", "periferico", "proyector", "accesorio", "audio"};
        int[] stocks = {6, 12, 18, 25, 9, 4, 14, 11};
        double[] precios = {899.90, 189.95, 49.90, 24.50, 79.00, 549.99, 129.00, 159.90};

        // Abrimos el fichero en modo lectura/escritura con try-with-resources
        try (RandomAccessFile raf = new RandomAccessFile("inventario.dat", "rw")) {
            
            // Recorremos usando la longitud de un array
            for (int i = 0; i < ids.length; i++) {
                // Escribimos respetando el orden
                raf.writeInt(ids[i]);
                escribirCadenaFija(raf, codigos[i], LONG_CODIGO);
                escribirCadenaFija(raf, nombres[i], LONG_NOMBRE);
                escribirCadenaFija(raf, categorias[i], LONG_CATEGORIA);
                raf.writeInt(stocks[i]);
                raf.writeDouble(precios[i]);
            }
            
            // Mostramos los resultados obligatorios por pantalla
            System.out.println("Número de registros guardados: " + ids.length);
            System.out.println("Tamaño total de cada registro: " + TAM_REGISTRO + " bytes.");
            
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}