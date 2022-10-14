package ActivationFunctions;

/**
 * @author Tim Schulz
 * @version 07.10.2022
 */
public interface ActivationFunction {
    /**
     * statische Ausprägungen der Funktionen für einfache Benutzung
     */
    public static AP ap = new AP();
    public static Linear linear = new Linear();
    public static Sigmoid sigmoid = new Sigmoid();

    /**
     * Aktivierungsfunktion
     * @param input
     * @return
     */
    double activation (double input);
    double ableitung (double input);

}
