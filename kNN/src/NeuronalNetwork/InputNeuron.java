package NeuronalNetwork;

/**
 * @author Tim Schulz
 * @version 07.10.2022
 */
public class InputNeuron extends Neuron {
    private double value = 0;

    /**
     * ermittelt den Wert, den das Neuron hat
     * @return "Wert des Neurons"
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * weist dem Neuron einen Wert zu
     * @param value Wert den das Neuron haben soll
     */
    public void setValue(double value) {
        this.value = value;
    }
}
