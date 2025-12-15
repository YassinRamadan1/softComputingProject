package neural_network.case_study.factories;

import neural_network.loss.*;

public final class LossFactory {

    private LossFactory() {}

    public static Loss fromString(String name) {
        return switch (name.toLowerCase()) {
            case "mse" -> new MSE();
            case "cross_entropy", "binary_cross_entropy" -> new CrossEntropy();
            default -> throw new IllegalArgumentException("Unknown loss function: " + name);
        };
    }
}
