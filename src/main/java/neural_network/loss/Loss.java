package neural_network.loss;

public interface Loss {
    double forward(double[] yTarget, double[] yPred);
    double[] backward(double[] yTarget, double[] yPred);
}
