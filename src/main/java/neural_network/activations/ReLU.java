package neural_network.activations;

public class ReLU implements Activation {

    // x >  0 : f(x) = x
    // x <= 0 : f(x) = 0
    @Override
    public double apply(double x) {
        return Math.max(0,x);
    }

    @Override
    public double derivative(double x) {
        return x > 0 ? 1.0 : 0.0;
    }
}
