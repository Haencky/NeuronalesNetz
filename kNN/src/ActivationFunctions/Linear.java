package ActivationFunctions;

public class Linear implements  ActivationFunction{

    @Override
    public double activation(double input) {
        return input;
    }

    @Override
    public double ableitung(double input) {
        return 1;
    }
}
