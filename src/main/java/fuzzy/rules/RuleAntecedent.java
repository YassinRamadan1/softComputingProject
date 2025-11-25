package fuzzy.rules;
import fuzzy.variables.FuzzyVariable;

public record RuleAntecedent(FuzzyVariable variable, Operator operator, String setName) {
    public enum Operator {
        IS, IS_NOT
    }

    @Override
    public String toString() {
        return variable.getName() + " " + operator.toString() + " " + setName;
    }
}
