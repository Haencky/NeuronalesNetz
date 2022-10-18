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
        for (int i = 0; i < inputs.length; i++) {
            input.add(i, new InputNeuron(inputs[i]));
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
            tmp.add(new WorkingNeuron());
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
     * verbindet alle Neuronen miteinander
     * @param weights Zweidimensionales Array für die Gewichte zwischen den Neuronen
     * @param biases Array, was angibt, wann ein Bias-Neuron in einer Hidden-Schicht vorkommt
     */
    // TODO: 16.10.22 Für alle Neuronen eigene Gewichte mitgeben können
    public void connect (int[] biases, double[][] weights, double[] weightsToInput) {
        if (hiddenNeurons.isEmpty()) {
            int i = 0;
            for (WorkingNeuron o : output) {
               for (InputNeuron in : input) {
                   o.addConnection(new Connection(in,weightsToInput[i++]));
               }
            }
        } else {
            int indexLayer = 0;
            int indexNeuron = 0;

            //Verbindet die InputNeuronen mit den HiddenNeuronen
            for (WorkingNeuron h1 : hiddenNeurons.get(0)) {
                indexNeuron = 0;
                for (InputNeuron in : input) {
                    h1.addConnection(new Connection(in,weightsToInput[indexNeuron++]));
                }
            }
            //Verbindet alle HiddenLayers miteinander
            for (indexLayer = indexLayer; indexLayer < hiddenNeurons.size()-1; indexLayer++) {
                indexNeuron = 0;
                if (biases[indexLayer] < 1) {       //sollte kein Bias in der Schicht vorhanden sein
                    for (WorkingNeuron h2 : hiddenNeurons.get(indexLayer+1)) {
                        indexNeuron = 0;
                        for (WorkingNeuron h1 : hiddenNeurons.get(indexLayer)) {
                            h2.addConnection(new Connection(h1,weights[indexLayer][indexNeuron++]));
                        }
                    }
                } else {
                    InputNeuron bias = new InputNeuron(1.0);  //Bias Neuron
                    for (WorkingNeuron h2 : hiddenNeurons.get(indexLayer+1)) {
                        indexNeuron = 0;
                        h2.addConnection(new Connection(bias,weights[indexLayer][indexNeuron++]));
                        for (WorkingNeuron h1 : hiddenNeurons.get(indexLayer)) {
                            h2.addConnection(new Connection(h1,weights[indexLayer][indexNeuron++]));
                        }
                    }
                }
            }
            List<WorkingNeuron> lastLayer = hiddenNeurons.get(hiddenNeurons.size()-1);
            indexNeuron = 0;
            if (biases[indexLayer] > 0) {
                InputNeuron bias = new InputNeuron(1);
                for (WorkingNeuron o : output) {
                    o.addConnection(new Connection(bias,weights[indexLayer][indexNeuron++]));
                    indexNeuron = 0;
                    for (WorkingNeuron h : lastLayer) {
                        o.addConnection(new Connection(h,weights[indexLayer][indexNeuron++]));
                    }
                }
            } else {
                for (WorkingNeuron o : output) {
                    indexNeuron = 0;
                    for (WorkingNeuron h : lastLayer) {
                        o.addConnection(new Connection(h,weights[indexLayer][indexNeuron++]));
                    }
                }
            }

        }
    }

    /**
     * Erzeugt zufällige Gewichte und ruft damit die andere Methode auf
     * @param biases
     */
    public void connect(int[] biases) {
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
            double[][] weights = new double[hiddenNeurons.size()][largest.size()+1];
            for (int i = 0; i < weights.length; i++) {
                for (int j = 0; j < weights[i].length; j++) {
                    weights[i][j] = rand.nextDouble();
                }
            }
            double[] weightToInput = new double[input.size()];
            for (int i = 0; i < weightToInput.length; i++) {
                weightToInput[i] = rand.nextDouble();
            }
            connect(biases,weights,weightToInput);
        } else {
            Random rand = new Random();
            double[][] useless = new double[1][1];
            double[] weigthsToInput = new double[input.size() * output.size()];
            for (int i = 0; i < weigthsToInput.length; i++) {
                weigthsToInput[i] = rand.nextDouble();
            }
            connect(biases,useless,weigthsToInput);
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

