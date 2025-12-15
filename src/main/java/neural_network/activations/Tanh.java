package neural_network.activations;

public class Tanh implements Activation {

    @Override
    public double forward(double x) {
       return Math.tanh(x);
    }

    @Override
    public double derivative(double x) {
        double tanh = Math.tanh(x);
        return 1 - tanh * tanh;
    }

    @Override
    public String getName() {
        return "Tanh";
    }
}
