package neural_network.initializers;

import java.util.Random;

public class RandomUniform implements Initializer {
    private final Random random = new Random();
    @Override
    public double[] initializeWeights(int fanIn, int fanOut) {
        double limit = 1.0 / Math.sqrt(fanIn);
        double[] weights = new double[fanIn];
        for (int i = 0; i < fanIn; i++) {
            weights[i] = -limit + (2 * limit * random.nextDouble());
        }
        return weights;
    }

    @Override
    public double initializeBias() {
        return 0.0;
    }
}
