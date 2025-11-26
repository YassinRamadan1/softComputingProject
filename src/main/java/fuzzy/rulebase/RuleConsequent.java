package fuzzy.rulebase;

import fuzzy.linguistic.FuzzyVariable;

public class RuleConsequent {
    FuzzyVariable variable;
    String setName;

    public RuleConsequent(FuzzyVariable variable, String setName) {
        this.variable = variable;
        this.setName = setName;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public FuzzyVariable getVariable() {
        return variable;
    }

    public void setVariable(FuzzyVariable variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return variable.getName() + " IS " + setName;
    }
}
