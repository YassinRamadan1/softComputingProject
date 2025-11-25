package fuzzy.validation;

import fuzzy.variables.FuzzyVariable;

public class ClampingStrategy implements InputValidationStrategy{
    private final boolean logWarnings;

    public ClampingStrategy(boolean logWarnings) {this.logWarnings = logWarnings;}
    public ClampingStrategy() {this(false);}

    @Override
    public double validate(double value, FuzzyVariable variable) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw  new InvalidInputException(
                    variable.getName(),
                    value,
                    "Value is not a number or Infinity"
            );
        }
        double min = variable.getMin();
        double max = variable.getMax();
        double clampedValue = Math.max(min, Math.min(max, value));

        if (logWarnings && clampedValue != value) {
            System.err.printf("[WARNING Clamped '%s' from %.2f to %.2f (range: [%.2f, %.2f]%n",
                    variable.getName(), value, clampedValue, min, max);
        }
        return clampedValue;
    }

    @Override
    public String getStrategyName() {
        return "Clamping";
    }
}
