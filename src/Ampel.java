import NeuronalesNetz.NeuronalNetwork;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Ampel {
    public static void main(String[] args) {
        String pathTrain = "/home/tim/Schreibtisch/Studium/S3/NN/src/KW41_traindata_trafficlights_classification.csv";
        String pathWeights = "/home/tim/Schreibtisch/Studium/S3/NN/src/KW41_weights_trafficlights_classification_simplified.csv";
        String data = "";
        double[] neuronWerte = null;

        try {
            BufferedReader trainData = new BufferedReader(new FileReader(pathTrain));
            BufferedReader weightsData = new BufferedReader(new FileReader(pathWeights));
            Path pfad = Paths.get("/home/tim/Schreibtisch/Studium/S3/NN/src/","ergebnis.csv");
            BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(pfad));
            String zeile = "";

            while ((data = trainData.readLine()) != null) {
                String[] values = data.split(",");
               neuronWerte = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    neuronWerte[i] = Double.parseDouble(values[i]);
                }
                /*double expect1 = neuronWerte[4];
                double expect2 = neuronWerte[5];
                double expect3 = neuronWerte[6];
                double expect4 = neuronWerte[7];
                System.out.print(expect1 + " " + expect2 + " " + expect3 + " " + expect4 + '\n');*/
                zeile = String.format("%f",neuronWerte[0]);
                writer.write(zeile);
                /*NeuronalNetwork nn = new NeuronalNetwork();
                nn.createInput(neuronWerte[0],neuronWerte[1],neuronWerte[2]);
                nn.createOutput(4);
                nn.visualize(false);
                System.out.println(" " + '\n');*/
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
