package fuzzy.validation;

import fuzzy.linguistic.FuzzyVariable;

public class StrictStrategy implements InputValidationStrategy {
    @Override
    public double validate(double value, FuzzyVariable variable) throws InvalidInputException {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new InvalidInputException(
                    variable.getName(),
                    value,
                    "Value is not a number or Infinity"
            );
        }
        double min = variable.getMin();
        double max = variable.getMax();
        if (value < min) {
            throw new InvalidInputException(
                    variable.getName(),
                    value,
                    String.format("Value %.2f is below minimum %.2f", value, min)
            );
        }
        if (value > max) {
            throw new InvalidInputException(
                    variable.getName(),
                    value,
                    String.format("Value %.2f is above maximum %.2f", value, max)
            );
        }
        return value;
    }

}