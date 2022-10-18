import ActivationFunctions.ActivationFunctions;
import NeuronalesNetz.NeuronalNetwork;
import ActivationFunctions.*;

public class Main {
    public static void main(String[] args) {
        NeuronalNetwork nn = new NeuronalNetwork();

        nn.createInput(1, 0, 0, 1);

        nn.createHiddenLayers(3);


        nn.createOutput(4);

        int[] biases = {1};
        double[][] weightsToInput = {{-0.081,0.06,-0.01,0.08},
                                    {0.08,0.02,0.003,-0.09},
                                    {-0.04,-0.003,-0.09,-0.05}};

        double[][] weights = {{-0.008,0.06,0.04,-0.08},
                            {0.01,-0.06,0.06,0.06},
                            {0.01,-0.027,0.08,0.09},
                            {0.0029,-0.01,0.08,-0.001}};

        nn.connectInput(weightsToInput);
        nn.connectOthers(weights,biases);

        nn.visualize(false);
        System.out.println('\n' + " ");

        nn.visualize(true);
        System.out.println('\n' + " ");

        nn.reset();
        /*int anzahl = 0;
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

            System.out.println('\n' + "Anzahl der Lerndurchgänge " + anzahl);*/
        System.out.println(nn.getOutputValues()[0] + -0.03504739174185117);
        System.out.println(nn.getOutputValues()[1] - 0.06379840926271282 );
        System.out.println(nn.getOutputValues()[2] - 0.11976621345436994 );
        System.out.println(nn.getOutputValues()[3] -  0.032371141308330784);
    }
}
