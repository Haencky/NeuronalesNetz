package NeuronalesNetz;

import ActivationFunctions.ActivationFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Beschreibt ein Output- bzw. ein HiddenNeuron
 * @author Tim Schulz
 * @version 15.10.2022
 */
public class WorkingNeuron extends Neuron{
    private List<Connection> connections = new ArrayList<>();
    private final double schwellwert;
    private final ActivationFunctions fnk;
    private double smallDelta;
    private double value = 0;
    private boolean valueClean = false;


    /**
     * Konstruktor für das WorkingNeuron
     * @param schwellwert, nur relevant für die binäre Aktivierungsfunktion (AktionsPotential, bestimmt ab wann gefeuert wird)
     * @param fnk, Aktivierungsfunktion des Neurons
     */
    public WorkingNeuron (double schwellwert, ActivationFunctions fnk) {
        this.schwellwert = schwellwert;
        this.fnk = fnk;
    }

    public WorkingNeuron() {
        this.schwellwert = 0.0;
        this.fnk = ActivationFunctions.id;
    }

    /**
     * gibt den Wert, den das Neuron hat weiter, summiert dafür alle Eingänge mit den Gewichten multipliziert auf,
     * merkt sich den Wert, um nicht in jeder Iteration den Wert neu zu berechnen
     * @return Wert gemäß der Aktivierungsfunktion des Neurons
     * Identity: den unveränderten Input
     * Sigmoid: einen Wert zwischen 0 und 1
     * AktionsPotential: 1, wenn Input > Schwellwert, 0 sonst
     */
    @Override
    public double getValue() {
        if(!valueClean) {
            double sum = 0;
            for (Connection c : connections) {
                sum += c.getValue();
            }
            value = fnk.activation(sum,schwellwert);
            valueClean = true;
        }
        return value;
    }

    /**
     * Gibt die Verbindungen des Neurons an
     * @return Verbindungen des Neurons
     */
    public List<Connection> getConnection () {
        return connections;
    }

    /**
     * Fügt eine neue Verbindung zum Neuron hinzu
     * @param c Verbindung, die hinzugefügt wird
     */
    public void addConnection (Connection c) {
        connections.add(c);
    }

    /**
     * Resettet das smallDelta des Neurons
     */
    public void reset () {
        this.valueClean = false;
        this.smallDelta = 0;
    }

    /**
     * Berechnet das smallDelta
     * @param expect wert der erwartet wird
     */
    public void calculateOutputDelta (double expect) {
        smallDelta = expect - getValue();
    }

    /**
     * Deltalernregel
     * @param learnrate mit der die Gewichte angepasst werden
     */
    public void deltaLearning (double learnrate) {
        double bigDeltaFaktor = fnk.ableitung(getValue(),schwellwert) * learnrate * smallDelta;
        for (int i = 0; i < connections.size(); i++) {
            double bigDelta = bigDeltaFaktor * connections.get(i).getNeuron().getValue();
            connections.get(i).addWeight(bigDelta);
        }
    }

    /**
     * Reicht den smallDeltaWert nach hinten durch
     */
    public void backPropagate () {
        //reset();
        for (Connection c : connections) {
            Neuron n = c.getNeuron();
            if (n instanceof WorkingNeuron) {
                WorkingNeuron wn = (WorkingNeuron) n;
                wn.smallDelta += smallDelta * c.getWeight();
            }
        }
    }
}
