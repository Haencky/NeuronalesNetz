package NeuronalesNetz;

/**
 * Beschreibt die Verbindung zwischen zwei Neuronen
 * @author Tim Schulz
 * @version 15.10.2022
 */
public class Connection {
    private Neuron neuron; //vorheriges Neuron
    private double weight;
    private double momentum = 0;

    /**
     * Erzeugt eine neue Verbindung zwischen zwei Neuronen
     * @param neuron Vorgängerneuron des Neurons bei dem die Verbindung erzeugt wird
     * @param weight Gewicht des Neurons
     */
    public Connection (Neuron neuron, double weight) {
        this.neuron = neuron;
        this.weight = weight;
    }

    /**
     * @return Wert, der über diese Verbindung zum Neuron übertragen wird
     */
    public double getValue () {
        return neuron.getValue() * weight;
    }

    /**
     * Erhöht oder verringert das Gewicht der Verbindung
     * @param deltaWeight abweichung, die dazugerechnet wird
     */
    public void addWeight (double deltaWeight) {
        momentum += deltaWeight;
        momentum *= 0.9;
        this.weight += deltaWeight + momentum;
    }

    /**
     * @return Gewicht der Verbindung
     */
    public double getWeight () {
        return weight;
    }

    /**
     * gibt das Vorgängerneuron dieser Verbindung an
     * @return Vorgängerneuron über diese Verbindung
     */
    public Neuron getNeuron() {
        return neuron;
    }
}
