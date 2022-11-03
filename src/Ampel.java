import NeuronalesNetz.NeuronalNetwork;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ampel {
    public static void main(String[] args) {
        String pathTrain = "/home/tim/Schreibtisch/Studium/S3/NN/src/KW41_traindata_trafficlights_classification.csv";
        String pathWeights = "/home/tim/Schreibtisch/Studium/S3/NN/src/KW41_weights_trafficlights_classification_simplified.csv";
        String data = "";
        double[] neuronWerte = null;
        double[][] weightsToInput = {{-0.081,0.06,-0.01,0.08},
                {0.08,0.02,0.003,-0.09},
                {-0.04,-0.003,-0.09,-0.05}};

        double[][] weights = {{-0.008,0.06,0.04,-0.08},
                            {0.01,-0.06,0.06,0.06},
                            {0.01,-0.027,0.08,0.09},
                            {0.0029,-0.01,0.08,-0.001}};

        int[] biases = {1};
        NeuronalNetwork nn = new NeuronalNetwork();

        try {
            BufferedReader trainData = new BufferedReader(new FileReader(pathTrain));
            BufferedReader weightsData = new BufferedReader(new FileReader(pathWeights));
            Path pfad = Paths.get("/home/tim/Schreibtisch/Studium/S3/NN/src/", "ergebnis.csv");
            BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(pfad));
            String zeile = "";

            while ((data = trainData.readLine()) != null) {
                String[] values = data.split(",");
                neuronWerte = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    neuronWerte[i] = Double.parseDouble(values[i]);
                }
                double expect1 = neuronWerte[4];
                double expect2 = neuronWerte[5];
                double expect3 = neuronWerte[6];
                double expect4 = neuronWerte[7];
                //System.out.print(expect1 + " " + expect2 + " " + expect3 + " " + expect4 + '\n');
                int i = 0;
                nn.createInput(neuronWerte[0], neuronWerte[1], neuronWerte[2], neuronWerte[3]);
                nn.createOutput(4);
                nn.createHiddenLayers(3);
                nn.connectInput(weightsToInput);
                nn.connectOthers(weights,biases);
                while (i < 10000) {
                    nn.learn(0.03,expect1,expect2,expect3,expect4);
                    //System.out.println(i);
                    i++;
                }
                nn.visualize(false);
                System.out.println(" " + '\n');
            }


        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
