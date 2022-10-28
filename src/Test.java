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
        while (nn.getOutputValues()[0] < 4 | nn.getOutputValues()[0] > 4.1) {
            nn.reset();
            nn.learn(0.3,4);
            laeufe++;
        }

        nn.visualize(false);

        System.out.println('\n' + "Anzahl der Läufe: " + laeufe);
    }
}
