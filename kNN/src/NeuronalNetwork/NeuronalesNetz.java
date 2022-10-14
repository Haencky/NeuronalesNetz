package NeuronalNetwork;


import ActivationFunctions.ActivationFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Tim Schulz
 * @version 07.10.2022; 08.10.2022
 * Diese Klasse beschreibt ein neuronales Netzwerk
 */
public class NeuronalesNetz {
    private List<InputNeuron> inputNeurons = new ArrayList<>();
    private List<WorkingNeuron> outputNeurons = new ArrayList<>();
    public List<ArrayList<WorkingNeuron>> hiddenLayers = new ArrayList<>();

    /**
     * Erzeugt hidden Layers
     * @param anzahlNeuronen array, was für jede Schicht die Anzahl der Neuronen angibt. Länge ist die Anzahl der Schichten
     * @param fnk Aktivierungsfunktion, die für das aktuelle gelten soll
     */
    public void createHiddenlayers (int[] anzahlNeuronen, ActivationFunction fnk) {
        for (int i = 0; i < anzahlNeuronen.length; i++) {
             hiddenLayers.add(createHiddenLayer(anzahlNeuronen[i],fnk));
        }
    }

    /**
     * Erzeugt ein Hiddenlayer
     * @param anzahlNeuronen anzahl der Neuronen im Hiddenlayer
     * @param fnk Aktivierungsfunktion der Neuronen
     */
    private ArrayList<WorkingNeuron> createHiddenLayer (int anzahlNeuronen, ActivationFunction fnk) {
        ArrayList<WorkingNeuron> tmp = new ArrayList<>();
        for (int i = 0; i < anzahlNeuronen; i++) {
            tmp.add(createHiddenNeuron(fnk));
        }
        return tmp;
    }

    /**
     * Erzeugt hidden Neurons und fügt sie der Liste hinzu
     * @param fnk Aktivierungsfunktion, die das Neuron verwenden soll
     */
    public WorkingNeuron createHiddenNeuron (ActivationFunction fnk) {
        WorkingNeuron hid = new WorkingNeuron(fnk);
        return hid;
    }
    /**
     * erzeugt ein neues InputNeuron und fügt es in die Liste inputNeurons ein
     * @return neues InputNeuron
     */
    public InputNeuron createNewInput() {
        InputNeuron in = new InputNeuron();
        inputNeurons.add(in);
        return in;
    }


    /**
     * erzeugt ein neues NeuronalNetwork.Working Neuron und fügt es in die Liste outputNeurons ein
     * @return neues WorkingNeuron
     */
    public WorkingNeuron createWorkingNeuron (ActivationFunction fnk) {
        WorkingNeuron wn = new WorkingNeuron(fnk);
        outputNeurons.add(wn);
        return wn;
    }

    /**
     *Vernetzt die Neuronen zu einem vollvermaschten Netz
     * @param weights initialisiert alle Gewichte gemäß des übergebenen Arrays
     */
    public void connectNeurons (double... weights) {
        //System.out.println(weights.length);
        if(hiddenLayers.isEmpty()) {                       //wenn es keine hidden Neuronen gibt
            int index = 0;
            for (WorkingNeuron wn:outputNeurons) {
                for (InputNeuron in : inputNeurons) {
                    wn.addConnection(new Connection(in, weights[index++]));
                }
            }
        } else {
            int index = 0;
            /**
             * verknüpft die input Schicht mit der ersten hidden Schicht
             */
            for (WorkingNeuron hidden : hiddenLayers.get(0)) {
                for (InputNeuron in : inputNeurons) {
                    hidden.addConnection(new Connection(in,weights[index++]));
                }
            }
            /**
             * verknüpft alle hidden Schichten miteinander
             */
            for (int i = 0; i < hiddenLayers.size() - 1; i++) {
                for (WorkingNeuron hidden2 : hiddenLayers.get(i+1)) {
                    for (WorkingNeuron hidden1 : hiddenLayers.get(i)) {
                        hidden2.addConnection(new Connection(hidden1,weights[index++]));
                    }
                }
            }

            /**
             * verknüpft die letzte hidden Schicht mit der Output Schicht
             */
            for (WorkingNeuron wn : outputNeurons) {
                for (WorkingNeuron hid : hiddenLayers.get(hiddenLayers.size()-1)) {
                    wn.addConnection(new Connection(hid,weights[index++]));
                }
            }
        }
    }


    /**
     * erzeugt ein Vollvermaschtes Netz, erzeugt dafür ein Array mit "zufälligen" Werten zwischen -1 und 1 für die
     * Gewichte und ruft mit diesem Array die parametrisierte Funktion zum Verknüpfen der Neuronen auf
     */
    public void connectNeurons () {
        double[] weights;
        int size = 0;
        /**
         * falls es eine Hidden-Schicht gibt wird die Anzahl der Gewichte (Verbindungen) mit dieser Schleife bestimmt
         */
        if (!hiddenLayers.isEmpty()) {
            size += inputNeurons.size() * hiddenLayers.get(0).size();
            size += outputNeurons.size() * hiddenLayers.get(hiddenLayers.size() - 1).size();
            for (int i = 0; i < hiddenLayers.size() - 1; i++) {
                size += hiddenLayers.get(i).size() * hiddenLayers.get(i + 1).size();
            }
            size += hiddenLayers.get(hiddenLayers.size() - 1).size() * hiddenLayers.get(hiddenLayers.size() - 2).size();
            /**
             * sollte es keine hidden-Schichten geben ist die Anzahl der Verbindungen Anzahl(Input) * Anzahl(Output)
              */
        } else {
            size = inputNeurons.size() * outputNeurons.size();
        }
        weights = new double[size];

        /**
         * belegt die Gewichte mit Werten von 0 bis 1
         */
        Random rand = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble();
        }
        connectNeurons(weights);
    }

    /**
     * visualisiert die Knoten (Neuronen) des Neuronalen Netzes. Zeigt den aktuellen Wert des Neurons an
     */
    public void visualize () {
        for (int i = 0; i < inputNeurons.size(); i++) {
            System.out.print(inputNeurons.get(i).getValue() + "   ");
        }
        System.out.println( '\n' + "------------");


        /**
         * gibt die hidden Layer Werte aus
         */
        for (int i = 0; i < hiddenLayers.size(); i++) {
            System.out.println(" ");
            for (int j = 0; j < hiddenLayers.get(i).size(); j++) {
                System.out.print(hiddenLayers.get(i).get(j).getValue() + "   ");
            }
        }
        System.out.println(" " + '\n' + '\n' + "------------");
        for (int i = 0; i < outputNeurons.size(); i++) {
            System.out.print(outputNeurons.get(i).getValue() + "   ");
        }
        System.out.println(" ");

    }

    /**
     * Delta-Lernregel für das gesamte Neuronale Netzwerk (wenn Hidden-Schichten vorhanden sind nicht anwendbar)
     * @param expect Erwartete Werte (surperviesd learning) für jedes Outputneuron
     * @param learnrate Rate mit der gelernt wird. 1 wäre komplett überschreiben, 0 wäre nicht lernen
     */
    public void learn (double [] expect, double learnrate) {
        if (expect.length != outputNeurons.size()) {
            throw new IndexOutOfBoundsException();
        }
        reset();
        for (int i = 0; i < expect.length; i++) {
            outputNeurons.get(i).calculateSmallDelta(expect[i]);
        }
        if (hiddenLayers.size() > 0) {
            for (int i = 0; i < expect.length; i++) {
                outputNeurons.get(i).backpropagateSmallDelta();
            }
        }
        for (int i = 0; i < expect.length; i++) {
            outputNeurons.get(i).deltaLearning(learnrate);
        }
        for (int i = 0; i < hiddenLayers.size(); i++) {
            for (int j = 0; j < hiddenLayers.get(i).size(); j++) {
                hiddenLayers.get(i).get(j).deltaLearning(learnrate);
            }
        }

    }
    public void learnDelta (int [] expect, double learnrate) {
        if (expect.length != outputNeurons.size()) {
            throw new IndexOutOfBoundsException();
        }
        reset();
        for (int i = 0; i < expect.length; i++) {
            outputNeurons.get(i).calculateSmallDelta(expect[i]);
        }
        if (hiddenLayers.size() > 0) {
            for (int i = 0; i < expect.length; i++) {
                outputNeurons.get(i).backpropagateSmallDelta();
            }
        }
        for (int i = 0; i < expect.length; i++) {
            outputNeurons.get(i).deltaLearning(learnrate);
        }
        for (int i = 0; i < hiddenLayers.size(); i++) {
            for (int j = 0; j < hiddenLayers.get(i).size(); j++) {
                hiddenLayers.get(i).get(j).deltaLearning(learnrate);
            }
        }

    }

    /**
     * resettet alle smallDeltas aller WorkingNeurons
     */
    public void reset () {
        for (WorkingNeuron wn : outputNeurons) {
            wn.reset();
        }

        for (int i = 0; i < hiddenLayers.size(); i++) {
            for (WorkingNeuron hn : hiddenLayers.get(i)) {
                hn.reset();
            }
        }

    }

}
