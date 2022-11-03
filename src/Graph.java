import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Graph {
    public static void main(String[] args) {

        Double[] arr = Ampel2.getData();
        FileWriter writer;
        File datei = new File("/home/tim/Schreibtisch/Studium/S3/NN/Ergebnisse/4HiddenLayers.txt");

        try {
            writer = new FileWriter(datei,true);
            for (int i = 0; i < arr.length; i++) {
               writer.write(arr[i].toString());
               writer.write(",");
               //writer.write('\n');
               writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
