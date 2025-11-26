package fuzzy.validation;

import fuzzy.config.FuzzyConfiguration;

public class ValidationStrategyFactory {
    public static InputValidationStrategy createStrategy(FuzzyConfiguration.ValidationStrategyType type) {
        return createStrategy(type, false);
    }

    public static InputValidationStrategy createStrategy(FuzzyConfiguration.ValidationStrategyType type, boolean logWarnings) {
        return switch (type) {
            case CLAMP -> new ClampingStrategy(logWarnings);
            case STRICT -> new StrictStrategy();
        };
    }
}