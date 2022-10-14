import ActivationFunctions.ActivationFunction;
import NeuronalNetwork.InputNeuron;
import NeuronalNetwork.NeuronalesNetz;
import NeuronalNetwork.WorkingNeuron;

public class Test1 {
    public static void main(String[] args) {

        NeuronalesNetz nnAND = new NeuronalesNetz();

        InputNeuron[] neurons = new InputNeuron[2];

        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = nnAND.createNewInput();
        }

        WorkingNeuron o1 = nnAND.createWorkingNeuron(ActivationFunction.ap);
        WorkingNeuron o2 = nnAND.createWorkingNeuron(ActivationFunction.ap);
        WorkingNeuron o3 = nnAND.createWorkingNeuron(ActivationFunction.ap);
        WorkingNeuron o4 = nnAND.createWorkingNeuron(ActivationFunction.ap);

        //nnAND.createHiddenlayers();

        nnAND.connectNeurons();

    neurons[0].setValue(1);
    neurons[1].setValue(1);



        int[] expect = {0,0,0,1};
        int trys = 0;
        nnAND.visualize();

        while (o1.getValue() != expect[0] && o2.getValue() != expect[1] && o3.getValue() != expect[2] && o4.getValue() != expect[3]) {
            nnAND.learnDelta(expect,0.3);
            trys++;
            //System.out.println(trys);
        }

        nnAND.visualize();
        System.out.println('\n' + "Versuche: " + trys);

    }
}

