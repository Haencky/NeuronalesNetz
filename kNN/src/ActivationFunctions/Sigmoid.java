package ActivationFunctions;

/**
 * @author Tim Schulz
 * @version 07.10.2022
 * Beschreibt die Sigmoid'sche Aktivierungsfunktion bei neuronalen Netzen
 */
public class Sigmoid implements  ActivationFunction{

    @Override
    public double activation(double input) {
        return (double) 1 / (1 + Math.pow(Math.E,-input));
    }

    @Override
    public double ableitung(double input) {
        double sigmoid = activation(input);
        return sigmoid * (1 - sigmoid);
    }
}
