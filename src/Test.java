import NeuronalesNetz.NeuronalNetwork;

public class Test {
    public static void main(String[] args) {
        NeuronalNetwork nn = new NeuronalNetwork();

        nn.createInput(1,2);
        nn.createHiddenLayers(1,3);
        nn.createOutput(1);

        nn.connect(1);

        nn.visualize(false);
        nn.visualize(true);

        int laeufe = 0;
        double expect = 10;
        double toleranz = 0.0000000000001;
        while (nn.getOutputValues()[0] < expect - toleranz | nn.getOutputValues()[0] > expect + toleranz) {
            nn.reset();
            nn.learn(0.3,expect);
            laeufe++;
        }

        nn.visualize(false);

        System.out.println('\n' + "Anzahl der Läufe: " + laeufe);
    }
}
