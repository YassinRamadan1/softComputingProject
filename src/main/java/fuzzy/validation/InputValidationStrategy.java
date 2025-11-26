package fuzzy.validation;

import fuzzy.linguistic.FuzzyVariable;

public interface InputValidationStrategy {
    double validate(double value, FuzzyVariable variable) throws InvalidInputException;
}
