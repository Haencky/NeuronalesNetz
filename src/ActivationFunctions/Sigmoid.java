package ActivationFunctions;

/**
 * Diese Klasse beschreibt die Sigmoid'sche Aktivierungsfunktion
 * @author Tim Schulz
 * @version 15.10.2022
 */
public class Sigmoid implements ActivationFunctions{
    /**
     * Bestimmt den Wert, der bei einem Input gemäß der Aktivierungsfunktion rauskommt
     * @param input, Wert der übergeben wird
     * @return Wert der gemäß der Funktion herauskommt
     */
    @Override
    public double activation(double input, double schwellwert) {
        return (double) 1 / (1 + Math.pow(Math.E,-input));

    }

    /**
     * Ableitungsfunktion, wird beim Delta-Lernen und der Backpropagation genutzt
     * @param input, Wert der übergeben wird
     * @return, Wert der Ableitungsfunktion bei gegebenem Input
     */
    @Override
    public double ableitung(double input, double schwellwert) {
        double sigmoid = activation(input, schwellwert);
        return sigmoid * (1 - sigmoid);
    }
}
