package neural_network.initializers;

import java.util.Random;

public class Xavier implements Initializer {
    private final Random random = new Random();
    @Override
    public double[] initializeWeights(int fanIn, int fanOut) {
        double limit = Math.sqrt(6.0 / (fanIn + fanOut));
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
