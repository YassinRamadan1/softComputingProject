package fuzzy.validation;

import fuzzy.linguistic.FuzzyVariable;

import java.util.HashMap;
import java.util.Map;

public class InputValidator {
    private final InputValidationStrategy inputValidationStrategy;
    private final Map<String, InputValidationStrategy> variableStrategies;

    public InputValidator(InputValidationStrategy inputValidationStrategy) {
        this.inputValidationStrategy = inputValidationStrategy;
        this.variableStrategies = new HashMap<>();
    }

    public void setStrategyForVariable(String variableName, InputValidationStrategy strategy) {
        variableStrategies.put(variableName, strategy);
    }

    public double validateInput(double value, FuzzyVariable variable) throws InvalidInputException {
        InputValidationStrategy strategy = variableStrategies.getOrDefault(
                variable.getName(),
                inputValidationStrategy
        );
        return strategy.validate(value, variable);
    }

    public Map<String, Double> validateInputs(Map<String, Double> inputs, Map<String, FuzzyVariable> variables) throws InvalidInputException {
        Map<String, Double> validatedInputs = new HashMap<>();

        for (Map.Entry<String, FuzzyVariable> entry : variables.entrySet()) {
            String varName = entry.getKey();
            FuzzyVariable variable = entry.getValue();

            if (!inputs.containsKey(varName)) {
                throw new InvalidInputException(
                        varName,
                        Double.NaN,
                        "Missing input value"
                );
            }

            double rawValue = inputs.get(varName);
            double validatedValue = validateInput(rawValue, variable);
            validatedInputs.put(varName, validatedValue);
        }

        return validatedInputs;
    }
}