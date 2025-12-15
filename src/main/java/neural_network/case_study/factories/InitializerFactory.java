package neural_network.case_study.factories;

import neural_network.initializers.*;

public final class InitializerFactory {

    private InitializerFactory() {}

    public static Initializer fromString(String name) {
        return switch (name.toLowerCase()) {
            case "xavier" -> new Xavier();
            default -> throw new IllegalArgumentException("Unknown initializer: " + name);
        };
    }
}
