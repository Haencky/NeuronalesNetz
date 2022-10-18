import ActivationFunctions.ActivationFunctions;
import NeuronalesNetz.NeuronalNetwork;

public class Main {
    public static void main(String[] args) {
        NeuronalNetwork nn = new NeuronalNetwork();

        nn.createInput(1, 0, 0, 1);

        nn.createHiddenLayers(3);


        nn.createOutput(4);

        int[] biases = {1};
        double[][] weights = {{0.3, 0.6, 0.2},
                {0.1, 0.7, 0.4}};

        double[] toInput = {0.2, 0.5};

        //nn.connect(biases,weights,toInput);
        nn.connect(biases);

        nn.visualize(false);
        System.out.println('\n' + " ");

        nn.visualize(true);
        System.out.println('\n' + " ");

        nn.reset();
        int anzahl = 0;
        double toleranz = 0.00000000001;
        double erwartet = 10.6094485986;
        while (nn.getOutputValues()[1] < erwartet - toleranz | nn.getOutputValues()[1] > erwartet + toleranz) {
            nn.reset();
            nn.learn(0.003, erwartet, erwartet,erwartet,erwartet);
            //System.out.println(nn.getOutputValues()[1]);
            anzahl++;
            if (anzahl % 10000000 == 0) {
                System.out.println(nn.getOutputValues()[1] - erwartet);
            }
        }
        nn.visualize(false);
            System.out.println('\n' + " ");
            nn.visualize(true);

            System.out.println('\n' + "Anzahl der Lerndurchgänge " + anzahl);
    }
}
