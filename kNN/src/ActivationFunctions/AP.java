package ActivationFunctions;

public class AP implements  ActivationFunction{

    @Override
    public double activation(double input) {
        if(input < 0) {
            return 0.0;
        } else {
            return 1.0;
        }
    }

    @Override
    public double ableitung(double input) {
        return 1;
    }
}
