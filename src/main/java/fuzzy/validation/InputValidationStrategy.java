package fuzzy.validation;

import fuzzy.variables.FuzzyVariable;

public interface InputValidationStrategy {
    double validate(double value, FuzzyVariable variable) throws InvalidInputException;
    String getStrategyName();
}
