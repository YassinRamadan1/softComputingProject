package neural_network.case_study.factories;

import neural_network.activations.*;

public final class ActivationFactory {

    private ActivationFactory() {}

    public static Activation fromString(String name) {
        return switch (name.toLowerCase()) {
            case "relu" -> new ReLU();
            case "sigmoid" -> new Sigmoid();
            case "tanh" -> new Tanh();
            case "linear" -> new Linear();
            default -> throw new IllegalArgumentException(
                    "Unknown activation: " + name
            );
        };
    }
}
