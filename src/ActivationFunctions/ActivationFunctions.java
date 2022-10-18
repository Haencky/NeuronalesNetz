package ActivationFunctions;

/**
 * Interface für die Aktivierungsfunktionen der Hidden/ Output Neuronen
 * @author Tim Schulz
 * @version 15.10.2022
 */
public interface ActivationFunctions {
    double activation (double input, double schwellwert);
    double ableitung (double input, double schwellwert);

    /**
     * Statische initialisierte Klassen, um nicht immer neue Objekte zu erzeugen, dient zudem der einfacheren Anwendung
     */
    public static Sigmoid sigmoid = new Sigmoid();
    public static AktionsPotenzial ap = new AktionsPotenzial();
    public static Identity id = new Identity();

}
