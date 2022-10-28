import NeuronalesNetz.NeuronalNetwork;

//Das ist ein Test

public class Main {
    public static void main(String[] args) {
        NeuronalNetwork nn = new NeuronalNetwork();

        nn.createInput(1, 0, 0, 1);

        nn.createHiddenLayers(3/*,2*/);


        nn.createOutput(4);

        int[] biases = {1,1};
        double[][] weightsToInput = {{-0.081,0.06,-0.01,0.08},
                                    {0.08,0.02,0.003,-0.09},
                                    {-0.04,-0.003,-0.09,-0.05}};

        double[][] weights = {/*{1.0,2.0,3.0,4.0},            //erstes Neuron (1. im zweiten Layer)
                            {1.5,2.5,3.5,4.5},*/              //zweites Neuron(2. im zweiten Layer)
                            {-0.008,0.06,0.04,-0.08},       //drittes (erstes output)
                            {0.01,-0.06,0.06,0.06},         //viertes (zweites output)
                            {0.01,-0.027,0.08,0.09},        //fünftes (drittes output)
                            {0.0029,-0.01,0.08,-0.001}};    //sechstes(viertes output)


        nn.connectInput(weightsToInput);
        nn.connectOthers(weights,biases);

        nn.visualize(false);
        System.out.println('\n' + " ");

        nn.visualize(true);
        System.out.println('\n' + " ");

        nn.reset();

        /*System.out.println(nn.getOutputValues()[0] - 0.03504739174185117);
        System.out.println(nn.getOutputValues()[1] - 0.06379840926271282 );
        System.out.println(nn.getOutputValues()[2] - 0.11976621345436994 );
        System.out.println(nn.getOutputValues()[3] -  0.032371141308330784);*/
    }
}
