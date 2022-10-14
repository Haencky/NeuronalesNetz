package NeuronalNetwork;

import ActivationFunctions.ActivationFunction;
import java.util.ArrayList;
import java.util.List;

/**
 * Beschreibt ein Neuron für die Ausgabe
 * @author Tim Schulz
 * @version 07.10.2022
 */
public class WorkingNeuron extends Neuron {

    private List<Connection> connections = new ArrayList<>();           //liste mit den Verbindungen zum Neuron
    private ActivationFunction activationFunction;                       //Art der Aktivierungsfunktion
    private double smallDelta = 0;

    public WorkingNeuron(ActivationFunction fnk)  {
        this.activationFunction = fnk;
    }

    /**
     * gibt den Wert des Ausagbeneurons an
     * @return Wert je nach Aktivierungsfunktion
     * Erwartungen der Ausgaben x:
     *  AP (ap): x = 0 oder x = 1
     *  Sigmoid(sigmoid): 0 < x < 1
     *  Linear(linear): Summe der (Inputs * Gewicht)
     */
    @Override
    public double getValue() {
        double sum = 0;                                 //summe der Eingänge über Verbindungen
        for (Connection c: connections) {
            sum += c.getValue();                        //summiert die Werte, die über die Verbindungen übermittelt werden
        }
        return activationFunction.activation(sum);
    }

    /**
     * fügt eine neue Verbindung zum Neuron hinzu
     * @param c eine Verbindung mit einem Vorgängerneuron und einem Gewicht
     */
    public void addConnection (Connection c) {
        connections.add(c);
    }
    public List<Connection> getConnections () {
        return connections;
    }

    /**
     * Delta-Lernregel
     * @param learnrate
     */
    public void deltaLearning(double learnrate) {
        double bigDeltaFaktor = activationFunction.ableitung(getValue()) * learnrate * smallDelta;
        for (int i = 0; i < connections.size(); i++) {
             double bigDelta = bigDeltaFaktor * connections.get(i).getNeuron().getValue();
            connections.get(i).addWeight(bigDelta);
        }
    }

    public void reset () {
        smallDelta = 0;
    }

    public void calculateSmallDelta (double should) {
        this.smallDelta = should - getValue();
    }

    public void backpropagateSmallDelta () {
        for (Connection c : connections) {
            Neuron n = c.getNeuron();
            if (n instanceof WorkingNeuron) {
                WorkingNeuron wn = (WorkingNeuron) n;
                wn.smallDelta += this.smallDelta * c.getWeight();
            }
        }
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
}
