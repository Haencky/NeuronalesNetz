import NeuronalesNetz.NeuronalNetwork;

import java.io.*;
import java.util.Scanner;

public class Ampel {
    public static void main(String[] args) {
        String pathTrain = "/home/tim/Schreibtisch/Studium/S3/NN/src/KW41_traindata_trafficlights_classification.csv";
        String pathWeights = "/home/tim/Schreibtisch/Studium/S3/NN/src/KW41_weights_trafficlights_classification_simplified.csv";
        String data = "";

        try {
            BufferedReader trainData = new BufferedReader(new FileReader(pathTrain));
            BufferedReader weightsData = new BufferedReader(new FileReader(pathWeights));

            while ((data = trainData.readLine()) != null) {
                String[] values = data.split(";");
               double[] neuronWerte = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    neuronWerte[i] = Integer.parseInt(values[i]);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        /*NeuronalNetwork nn1 = new NeuronalNetwork();
        NeuronalNetwork nn2 = new NeuronalNetwork();
        double [][] weightsToInput = {{}};
        double [][] weigths = {{}};
        int[] biases = {0};

        nn1.createInput(1,0,0);
        nn1.connectInput(weightsToInput);
        nn1.connectOthers(weigths,biases);
        nn1.createOutput(4);

        nn2.createInput(0.8,0,0.1);
        nn2.connectInput(weightsToInput);
        nn2.connectOthers(weigths,biases);
        nn2.createOutput(4);*/

    }
}
