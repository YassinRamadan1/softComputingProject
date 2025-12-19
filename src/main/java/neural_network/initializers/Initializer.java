package neural_network.initializers;

public interface Initializer {
    double[] initializeWeights(int inputSize);
    double initializeBias();
}
