import NeuronalesNetz.NeuronalNetwork;

public class Ampel2 {
    public static Double[] getData () {
        double[] werteInput1 = {1, 0.8, 0.99, 1.1, 1, 0.99, 1.1, 1, 0, 0.1, 0, 0.1, 0, 0.1, 0.01, 0};
        double[] werteInput2 = {0, 0, 0.1, 0, 1, 1.1, 0.9, 1, 0, 0.1, 0.1, 0,1, 1.1, 1.1, 99};
       double[] werteInput3 = {0,0.1,0,0.1,0,0,0,0.1,1,1,1.1,1,0,0,0.1,-0.11};
       double[] werteInputBias = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

       double[] expect1 = {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0};
       double[] expect2 = {0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0};
       double[] expect3 = {0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0};
       double[] expect4 = {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1};
        //System.out.println(expect1.length);


        /*double[][] weights = {
                {1.5,2.5,3.5,4.5},
                {-0.008, 0.06, 0.04, -0.08},
                {0.01, -0.06, 0.06, 0.06},
                {0.01, -0.027, 0.08, 0.09},
                {0.0029, -0.01, 0.08, -0.001}};

        double[][] weightsToInput = {{-0.081, 0.06, -0.01, 0.08},
                {0.08, 0.02, 0.003, -0.09},
                {-0.04, -0.003, -0.09, -0.05}};*/

        double[][] weights = {
                {1.2,1.65,9.5,0.002},
                {-0.8, 0.076, 0.024, -0.2},
                {0.0981, -0.06, 0.5, 0.06},
                {-0.03, -0.27, 0.08, 0.9},
                {0.0429, -0.1, 0.018, -0.01}};

        double[][] weightsToInput = {{-0.85685, 0.3, -0.001, 0.762},
                {0.0578, -0.01, -0.32003, -0.09},
                {-0.034, -0.03, -0.09, -0.05}};

        NeuronalNetwork nn = new NeuronalNetwork();
        int[] biases = {1,1,1,1,1};
        nn.createOutput(4);
        nn.createHiddenLayers(3,3,3,3);
        nn.createInput(werteInput1[0],werteInput2[0],werteInput3[0],werteInputBias[0]);
        //nn.connectInput(weightsToInput);
        //nn.connectOthers(weights,biases);
        nn.connect(biases);
        double loss = 0;
        double loss1 = 0;
        double loss2 = 0;
        double loss3 = 0;
        double loss4 = 0;
        double average = 0;
        double averageEpoche = 0;
        Double[] lost = new Double[50];
        for (int i = 0; i < 50; i++) {
            averageEpoche = 0;
           for (int j = 1; j < 16; j++) {
               //average = 0;
               nn.reset();
               nn.createInput(werteInput1[j],werteInput2[j],werteInput3[j],werteInputBias[j]);
               nn.learn(0.03,expect1[j],expect2[j],expect3[j],expect4[j]);

               loss1 = expect1[j] - nn.getOutputValues()[0];
               loss2 = expect2[j] - nn.getOutputValues()[1];
               loss3 = expect3[j] - nn.getOutputValues()[2];
               loss4 = expect4[j] - nn.getOutputValues()[3];
               average = (loss1 + loss2 + loss3 + loss4) / 4;       //fuer jeden der 16
               averageEpoche += average;                            //fuer jeden der 16
           }
            lost[i] = Math.abs(averageEpoche / 16);
        }
        /*for (int i = 0; i < 1000000; i++) {
            System.out.println(lost[i]);
        }*/
        return lost;
    }
}
