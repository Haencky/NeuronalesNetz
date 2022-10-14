package NeuronalNetwork;

/**
 * @author Tim Schulz
 * @version 07.10.2022
 * beschreibt die verbindung zweier Neuronen
 */
public class Connection {
    private Neuron neuron;
    private double weight;

    /**
     * Konstruktor für die Verbuindung zwischen zwei Neuronen
     * @param neuron vorgänger Neuron (input)
     * @param weight Wert, mit dem der Input verrechnet wird
     */
    public Connection (Neuron neuron, double weight) {
        this.neuron = neuron;
        this.weight = weight;
    }

    /**
     *
     * @return Wert, der über diese Verbindung zum Folgeneuron weitergeleitet wird
     */
    public double getValue () {
        return neuron.getValue() * weight;
    }

    /**
     * verändert das Gewicht durch aufaddieren des Delta-Werts des Gewichts
     * @param deltaWeight ermitteltes Gewicht, dass addiert wird
     */
    public void addWeight (double deltaWeight) {
        weight += deltaWeight;
    }

    public Neuron getNeuron () {
        return neuron;
    }

    public double getWeight() {
        return weight;
    }
}
