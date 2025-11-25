package fuzzy.rules;
import fuzzy.variables.FuzzyVariable;

public record RuleConsequent(FuzzyVariable variable, String setName) {

    @Override
    public String toString() {
        return variable.getName() + " IS " + setName;
    }
}
