package neural_network.activations;

public class Sigmoid implements Activation {

    @Override
    public double apply(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    @Override
    public double derivative(double x) {
        double sigmoid = apply(x);
        return sigmoid * (1 - sigmoid);
    }
}
