package neural_network.optimizers;

import neural_network.core.NeuralNetwork;

public interface Optimizer {
    void step(NeuralNetwork network);
}

