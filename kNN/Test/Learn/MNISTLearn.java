package Learn;

import ActivationFunctions.ActivationFunction;
import NeuronalNetwork.InputNeuron;
import NeuronalNetwork.NeuronalesNetz;
import NeuronalNetwork.WorkingNeuron;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MNISTLearn {
    public static List<MNISTDecoder.Digit> digits;
    public static List<MNISTDecoder.Digit> digitTest;
    public static NeuronalesNetz nn = new NeuronalesNetz();
    public static InputNeuron[][] inputs = new InputNeuron[28][28];
    public static WorkingNeuron[] outputs = new WorkingNeuron[10];
    public static double perc = 0;

    public static void main(String[] args) {
        try {
            digits = MNISTDecoder.loadDataSet("/home/tim/Schreibtisch/Studium/S3/kNN/train-images.idx3-ubyte","/home/tim/Schreibtisch/Studium/S3/kNN/train-labels.idx1-ubyte");
            digitTest = MNISTDecoder.loadDataSet("/home/tim/Schreibtisch/Studium/S3/kNN/t10k-images.idx3-ubyte","/home/tim/Schreibtisch/Studium/S3/kNN/t10k-labels.idx1-ubyte");

            for (int i = 0; i < 28; i++) {
                for (int j = 0; j < 28; j++) {
                    inputs[i][j] = nn.createNewInput();
                }
            }
            for (int i = 0; i < 10; i++) {
                outputs[i] = nn.createWorkingNeuron(ActivationFunction.linear);
            }

            nn.connectNeurons();
            double epsilon = 0.01;


            while (perc < 0.6904) {
                test();
                for (int i = 0; i < digits.size(); i++) {
                    for (int j = 0; j < 28; j++) {
                        for (int k = 0; k < 28; k++) {
                            inputs[j][k].setValue(MNISTDecoder.toUnsignedByte(digits.get(i).data[j][k]) / 255f);
                        }
                    }
                    double [] shoulds = new double[10];
                    shoulds[digits.get(i).label] = 1;
                    nn.learn(shoulds,epsilon);
                }
                epsilon *= 0.9f;
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
    public static void test () {
        int correcrt = 0;
        int incorrect = 0;
        for (int i = 0; i < digitTest.size(); i++) {
            for (int j = 0; j < 28; j++) {
                for (int k = 0; k < 28; k++) {
                    inputs[j][k].setValue(MNISTDecoder.toUnsignedByte(digitTest.get(i).data[j][k]) / 255f);
                }
            }
            ProbabilityDigit[] probs = new ProbabilityDigit[10];
            for (int x = 0; x < probs.length; x++) {
                probs[x] = new ProbabilityDigit(x, outputs[x].getValue());
            }
            Arrays.sort(probs, Collections.reverseOrder());
            if (digitTest.get(i).label == probs[0].DIGIT) {
                correcrt++;
            } else {
                incorrect++;
            }
        }
        perc = (double)correcrt /(double)(correcrt + incorrect);
        System.out.println(perc);
    }
    public static class ProbabilityDigit implements  Comparable<ProbabilityDigit>{
        public final int DIGIT;
        public double propability;

        public ProbabilityDigit(int digit, double propability) {
            this.DIGIT = digit;
            this.propability = propability;
        }

        @Override
        public int compareTo(ProbabilityDigit o) {
            if(propability == o.propability) {
                return 0;
            } else if (propability > o.propability) {
                return 1;
            } else {
                return -1;
            }
        }
    }

}
