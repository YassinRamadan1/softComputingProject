package neural_network.case_study.factories;

import neural_network.optimizers.*;

public final class OptimizerFactory {

    private OptimizerFactory() {}

    public static Optimizer fromString(String name, double learningRate) {
        return switch (name.toLowerCase()) {
            case "sgd" -> new SGD(learningRate);
            default -> throw new IllegalArgumentException("Unknown optimizer: " + name);
        };
    }
}
