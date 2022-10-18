package ActivationFunctions;

/**
 * Beschreibt die binäre Aktivierungsfunktion
 * @author Tim Schulz
 * @version 15.10.2022
 */
public class AktionsPotenzial implements ActivationFunctions {

    /**
     * Aktivierungsfunktion
     * @param input Wert, der beim Addieren der Inputs kommt
     * @param schwellwert Wert, der Überschritten werden muss, um den Wert "1" auszugeben
     * @return 1,falls Input > Schwellwert, 0 sonst
     */
    @Override
    public double activation(double input, double schwellwert) {
        if (input > schwellwert) {
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public double ableitung(double input, double schwellwert) {
        return 1.0;
    }
}
