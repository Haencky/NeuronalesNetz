import java.util.ArrayList;

/**
 * @author Tim Schulz
 * @version 02.10.2022
 * Diese Klasse beschreibt ein neuronales Netzwerk
 */
public class NeuronalesNetz {
    //Attribute
    public static ArrayList<Neuron> inputLayer;                //speichert die inputLayer Neuronen
    public static ArrayList<Neuron> hiddenLayer;               //speichert die hiddenLayer Neuronen
    public static ArrayList<Neuron> outputLayer;               //speichert die outputLayer Neuronen

    public NeuronalesNetz() {
        outputLayer = new ArrayList<>();
        outputLayer.add(new Neuron(2));
    }
    public void doSomething(int... input) {
        outputLayer.get(0).learn(2,1.0, 1.0);
    }


    public static void main(String[] args) {
        NeuronalesNetz n = new NeuronalesNetz();
        n.doSomething();
    }


}
