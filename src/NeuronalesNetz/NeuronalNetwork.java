package NeuronalesNetz;

import ActivationFunctions.ActivationFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Diese Klasse beschreibt ein neuronales Netzwerk
 * @author Tim Schulz
 * @version 15.10.2022
 */
public class NeuronalNetwork {
    private List<InputNeuron> input = new ArrayList<>();
    private List<List<WorkingNeuron>> hiddenNeurons = new ArrayList<>();
    private List<WorkingNeuron> output = new ArrayList<>();


    /**
     * Erzeugt InputNeuronen und fügt diese den InputNeuronen des neuronalen Netzwerks hinzu
     * @param inputs, Array das am Index i den Wert des iten InputNeurons hat
     */
    public void createInput(double... inputs) {
        input = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            input.add(i, new InputNeuron(inputs[i]));
        }
    }

    public void createInput(double[] werte1, double[] werte2, double[] werte3) {
        for (int i = 0; i < werte1.length; i++) {
            input.add(new InputNeuron(werte1[i]));
            input.add(new InputNeuron(werte2[i]));
            input.add(new InputNeuron(werte3[i]));
        }
    }

    /**
     * Erzeugt HiddenLayers und fügt diese dem neuronalen Netzwerk hinzu
     * @param fnks, Array für die Aktivierungsfunktionen jedes eigenen Neurons in den Hidden Layern
     * @param schwellwerte, Array mit den Schwellwerten für jedes Neuron in den Hidden Layern
     * @param anzahl, Array mit der Anzahl der Neuronen pro Hidden Layer
     */
    public void createHiddenLayers (ActivationFunctions[][] fnks, double[][] schwellwerte, int... anzahl) {
        for (int i = 0; i < anzahl.length; i++) {
            hiddenNeurons.add(createHiddenLayer(fnks[i],anzahl[i], schwellwerte[i]));
        }
    }

    /**
     * Erzeugt HiddenLayers, bei denen die Neuronen keinen Schwellwert, oder Aktivierungsfunktionen brauchen
     * @param anzahl Anzahl der Neuronen pro Layer
     */
    public void createHiddenLayers (int... anzahl) {
        for (int i = 0; i < anzahl.length; i++) {
            hiddenNeurons.add(i,createHiddenLayer(anzahl[i]));
        }
    }

    /**
     * erzeugt eine HiddenLayer
     * @param anzahlNeuronen, Anzahl der Neuronen die das HiddenLayer haben soll
     * @param fnk, Aktivierungsfunktion für jedes einzelne Neuron
     * @param schwellwert, Wert, der bei der binären Aktivierungsfunktion überschritten werden muss
     * @return ein HiddenLayer als ArrayList
     */
    private List<WorkingNeuron> createHiddenLayer (ActivationFunctions[] fnk, int anzahlNeuronen, double[] schwellwert) {
        List<WorkingNeuron> ret = new ArrayList<>();
        for (int i = 0; i < anzahlNeuronen; i++) {
            ret.add(i,new WorkingNeuron(schwellwert[i], fnk[i]));
        }
        return ret;
    }

    /**
     * Erzeugt Neuronen, die keinen Schwellwert benötigen
     * @param anzahlNeuronen Anzahl der Neuronen im HiddenLayer
     * @return HiddenLayer als ArrayList
     */
    private List<WorkingNeuron> createHiddenLayer (int anzahlNeuronen) {
        List<WorkingNeuron> tmp = new ArrayList<>();
        for (int i = 0; i < anzahlNeuronen; i++) {
            tmp.add(i,new WorkingNeuron(0,ActivationFunctions.sigmoid));
        }
        return tmp;
    }

    /**
     * Erzeugt OutputNeuronen und fügt diese dem Netz hinzu
     * @param anzahl Anzahl der OutputNeuronen
     * @param schwellwerte Array mit Schwellwerten für jedes Neuron
     * @param fnks Array mit Aktivierungsfunktionen für jedes Neuron
     */
    public void createOutput(int anzahl, double[] schwellwerte, ActivationFunctions[] fnks) {
        for (int i = 0; i < anzahl; i++) {
            output.add(i, new WorkingNeuron(schwellwerte[i], fnks[i]));
        }
    }

    /**
     * Erzeugt Outputneuronen ohne Schwellwert und mit der Aktivierungsfunktion id
     * @param anzahl Anzahl der Outputneuronen
     */
    public void createOutput(int anzahl) {
        for (int i = 0; i < anzahl; i++) {
            output.add(new WorkingNeuron());
        }
    }

    public double[] getOutputValues () {
        double[] ret = new double[output.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = output.get(i).getValue();
        }
        return ret;
    }

    /**
     * Verbindet den Input mit den OutputNeuronen bzw. den ersten HiddenNeuronen
     * @param weights Zweidmensionales Array für die Gewichte,
     *[neuron, dass verbunden wird][gewicht zum neuron zudem verbunden wird]
     */
    public void connectInput(double[][] weights) {
        int neuronOut = 0;
        int neuronIn = 0;
        if(hiddenNeurons.isEmpty()) {       //falls es keine HiddenNeurons gibt verbinde den Output direkt mit dem Input
            for(WorkingNeuron o : output) {
                neuronOut++;
                neuronIn = 0;
                for(InputNeuron in : input) {
                    o.addConnection(new Connection(in,weights[neuronOut][neuronIn++]));
                }
            }
        } else {
            List<WorkingNeuron> layer1 = hiddenNeurons.get(0);
            for(WorkingNeuron h1 : layer1) {
                neuronIn++;
                neuronOut = 0;
                for(InputNeuron in : input) {
                    h1.addConnection(new Connection(in,weights[neuronIn-1][neuronOut++]));
                }
            }
        }
    }

    /**
     * Verbindet alle anderen Neuronen
     * @param weights Zweidmensionales Array für die Gewichte,
     *[neuron, dass verbunden wird][gewicht zum neuron zudem verbunden wird]
     * @param biases Index gibt an, ob ein Bias vorhanden sein soll, kleiner 1: kein Bias, Bias sonst
     */
    public void connectOthers(double[][] weights, int[] biases) {
        if(hiddenNeurons.isEmpty()) {   //falls es keine HiddenNeurons gibt, wird Automatisch der Input mit dem Output
            connectInput(weights);      //verknüpft, um Fehler zu tolerieren
        } else {
            int neuronIn = 0;       //gibt das Neuron an, dass Verbindungen herstellt
            int neuronOut = 0;      //gibt das Neuron an, zudem verbunden wird
            InputNeuron bias = new InputNeuron(1);      //Bias Neuron, liegt irgendwo

            //Trouble-Shooting falls man Biases nicht mit übergibbt um, keine Probleme zu bekommen
            int laenge = biases.length;
            if (biases.length != hiddenNeurons.size()) {
                int[] biasesNew = new int[hiddenNeurons.size()];
                for (int i = 0; i < biasesNew.length; i++) {
                    if (i < laenge) {
                        biasesNew[i] = biases[i];
                    } else {
                        biasesNew[i] = 0;
                    }
                }
                biases = biasesNew;
            }
            //ende vom Trouble-Shooting

            //Verknüpft alle HiddenNeuronen miteinander
            int i = 0;
            neuronOut = 0;
            for (i = i; i < hiddenNeurons.size() - 1; i++) {
                for (WorkingNeuron h2 : hiddenNeurons.get(i + 1)) {
                    neuronIn++;
                    neuronOut = 0;
                    for (WorkingNeuron h1 : hiddenNeurons.get(i)) {
                        h2.addConnection(new Connection(h1, weights[neuronIn - 1][neuronOut++]));
                    }
                    if (biases[i] > 0) {
                        h2.addConnection(new Connection(bias, weights[neuronIn-1][neuronOut++]));
                    }
                }
            }

            //verknüpft die Last-Layer mit den OutputNeuronen
            neuronOut = 0;
            List<WorkingNeuron> lastLayer = hiddenNeurons.get(hiddenNeurons.size()-1);
            for(WorkingNeuron o : output) {
                neuronOut = 0;
                neuronIn++;
                for(WorkingNeuron h : lastLayer) {
                    o.addConnection(new Connection(h,weights[neuronIn-1][neuronOut++]));
                }
                if(biases[i] > 0) {
                    o.addConnection(new Connection(bias,weights[neuronIn-1][neuronOut++]));
                }
            }
        }
    }

    /**
     * Erzeugt zufällige Gewichte und ruft damit die andere Methode auf
     * @param biases
     */
    public void connect(int... biases) {
        if(!hiddenNeurons.isEmpty()) {
            List<WorkingNeuron> largest = hiddenNeurons.get(0);
            for (int i = 1; i < hiddenNeurons.size(); i++) {
                if(hiddenNeurons.get(i).size() > largest.size()) {
                    largest = hiddenNeurons.get(i);
                } else {
                    continue;
                }
            }
            Random rand = new Random();
            double[][] weights = new double[hiddenNeurons.size() * largest.size()* output.size()][largest.size()+ output.size()];
            for (int i = 0; i < weights.length; i++) {
                for (int j = 0; j < weights[i].length; j++) {
                    weights[i][j] = rand.nextDouble();
                }
            }
            double[][] weightsToInput = new double[largest.size() + output.size()][input.size()];
            for (int i = 0; i < weightsToInput.length; i++) {
                for (int j = 0; j < weightsToInput[0].length; j++) {
                    weightsToInput[i][j] = rand.nextDouble();
                }
            }
            connectInput(weightsToInput);
            connectOthers(weights, biases);
        } else {
            Random rand = new Random();
            double[][] useless = new double[1][1];
            double[][] weigthsToInput = new double[output.size()+1][input.size()];
            for (int i = 0; i < weigthsToInput.length; i++) {
                for(int j = 0; j < weigthsToInput[0].length; j++) {
                    weigthsToInput[i][j] = rand.nextDouble();
                }
            }
            connectInput(weigthsToInput);
        }
    }

    /**
     * Visualisiert entweder die Neuronen oder die Gewichte
     * @param gewichte boolscher Wert, ob Gewichte angezeigt werden sollen
     */
    public void visualize (boolean gewichte) {
        if(!gewichte) {
            for(InputNeuron in : input) {
                System.out.print(in.getValue() + "   ");
            }

            System.out.println('\n' + "------------------------");

            for (int i = 0; i < hiddenNeurons.size(); i++) {
                for (WorkingNeuron h : hiddenNeurons.get(i)) {
                    System.out.print(h.getValue() + "   ");
                }
                System.out.println(" ");
            }
            System.out.println("------------------------");
            for (WorkingNeuron o : output) {
                System.out.print(o.getValue() + "   ");
            }
        } else {
            if (hiddenNeurons.isEmpty()) {
                int i = 0;
                for (WorkingNeuron o : output) {
                    for(Connection c : o.getConnection()) {
                        System.out.print(c.getWeight() + "   ");
                    }
                }
                System.out.println(" " + '\n');
            } else {
                System.out.println('\n');

                for (int j = 0; j < hiddenNeurons.size(); j++) {
                    for (WorkingNeuron hn : hiddenNeurons.get(j)) {
                        System.out.print("/  ");
                        for(Connection c : hn.getConnection()) {
                            System.out.print(c.getWeight() + "   ");
                        }
                    }
                    System.out.println(" ");
                }
                for (WorkingNeuron o : output) {
                    System.out.print("/  ");
                    for (Connection c : o.getConnection()) {
                        System.out.print(c.getWeight() + "   ");
                    }
                }
                System.out.println(" " + '\n');
            }
        }
    }

    /**
     * resettet die smallDeltas aller Neuronen
     */
    public void reset () {
        for (WorkingNeuron o : output) {
            o.reset();
        }
        for (int i = 0; i < hiddenNeurons.size(); i++) {
            for(WorkingNeuron hn : hiddenNeurons.get(i)) {
                hn.reset();
            }
        }
    }

    public void learn (double learnrate, double... expect) {
        //reset();

        for(int i = 0; i < output.size(); i++) {
            output.get(i).calculateOutputDelta(expect[i]);
        }
        
        if(!hiddenNeurons.isEmpty()) {
            for (int i = 0; i < expect.length; i++) {
                output.get(i).backPropagate();
            }
        }
        for (int i = 0; i < expect.length; i++) {
            output.get(i).deltaLearning(learnrate);
        }
        for (int i = 0; i < hiddenNeurons.size(); i++) {
            for (int j = 0; j < hiddenNeurons.get(i).size(); j++) {
                hiddenNeurons.get(i).get(j).deltaLearning(learnrate);
            }
        }
    }

}

