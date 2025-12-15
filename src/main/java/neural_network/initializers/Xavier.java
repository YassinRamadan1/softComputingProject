package neural_network.initializers;

import java.util.Random;

public class Xavier implements Initializer {
    private final Random random = new Random();
    @Override
    public double[] initializeWeights(int inputSize) {
        double limit = Math.sqrt(6.0 / (inputSize + 1));
        double[] weights = new double[inputSize];
        for (int i = 0; i < inputSize; i++) {
            weights[i] = -limit + (2 * limit * random.nextDouble());
        }
        return weights;
    }

    @Override
    public double initializeBias() {
        return 0.0;
    }
}
