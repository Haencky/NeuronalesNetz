/**
 * @author Tim Schulz
 * @version 02.10.2022
 * Diese Klasse beschreibt ein Neutron in einem neuronalen Netzwerk
 */
public class Neuron {
    //Attribute
    public final double schwellwert = 1.5;          //der Schwellwert, der Überschritten werden muss, um eine Ausgabe zu erzeugen
    public double[] gewichte;                       //gewichte von den Vorgängerneuronen
    double bias;
    double lernRate = 0.05;


    /**
     * erzeugt ein Neuron
     * @param anzahlVorgaenger, Menge der Neuronen, die im vorherigen Layer sind
     */
    public Neuron(int anzahlVorgaenger) {
        //Erzeugung der Felder
        this.gewichte = new double[anzahlVorgaenger];   //größe des gewicht Arrays ist Anzahl der Neuronen im vorherigen Layer
        //initiales Füllen der Felder
        for (int i = 0; i < anzahlVorgaenger; i++) {
            gewichte[i] = 0.5;
        }
    }

    /**
     *Bestimmt, ob das Neuron das signal weiterleitet
     * @param input, vorgehende Eingaben (andere Neuronen oder "Außenwelt")
     * @return 1, falls Schwellwert überschritten | 0 sonst
     */
    public int activation (double[] input) {
        double sum = 0;
        for (int i = 0; i < gewichte.length; i++) {                 //summiert die Werte auf
            sum += gewichte[i] * input[i];
        }
        sum += bias;
        if(sum > schwellwert) {                                     //entscheidet, ob AP oder nicht
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * "Langzeitpotenzierung" bestimmt mit der Zeit die Gewichte neu, beschreibt das Lernen
     *
     */
    public void learn (int result, double...  input) {
        //System.out.println(gewichte[0]);
        //System.out.println(gewichte[1]);
        //System.out.println("");
        int step = activation(input);
        if(result - step < 0.1) {
            return;
        }
    }

    public void gewichteAnpassen(double error, int[] input) {
        for (int i = 0; i < gewichte.length; i++) {
            gewichte[i] = lernRate * error *input[i] + gewichte[i];
        }
    }

}
