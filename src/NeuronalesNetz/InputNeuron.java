package NeuronalesNetz;

/**
 *Diese Klasse beschreibt ein InputNeuron und Bias in einem neuronalen Netz
 * @author Tim Schulz
 * @version 15.10.2022
 */
public class InputNeuron extends Neuron{

    private double value = 0;       //Wert der an die anderen Neuronen weitergegeben wird

    public InputNeuron (double value) {
        this.value = value;
    }

    /**
     * Funktion, um an den Wert des InputNeurons zur weiteren Verwendung zu kommen
     * @return value, den Wert, den das Neuron hat
     */
    @Override
    public double getValue() {
        return value;
    }

}
