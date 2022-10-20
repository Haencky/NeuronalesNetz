package ActivationFunctions;

/**
 *Beschreibt die Identität, bzw. die lineare Aktivierungsfunktion
 * @author Tim Schulz
 * @version 15.10.2022
 */
public class Identity implements ActivationFunctions{
    /**
     * Lineare Funktion
     * @param input, Wert der übergeben wird
     * @return input, verändert nichts
     */
    @Override
    public double activation(double input, double schwellwert) {
        return input;
    }


    /**
     * Ableitungsfunktion der linearen Funktion
     * @param input, Wert der übergeben wird
     * @return 1
     */
    @Override
    public double ableitung(double input, double schwellwert) {
        return 1;
    }
}
