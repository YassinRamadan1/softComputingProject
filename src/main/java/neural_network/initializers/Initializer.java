package neural_network.initializers;

public interface Initializer {
    double[] initializeWeights(int fanIn, int fanOut);
    double initializeBias();
}
