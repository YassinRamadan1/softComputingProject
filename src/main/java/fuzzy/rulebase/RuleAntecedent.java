package fuzzy.rulebase;
import fuzzy.linguistic.FuzzyVariable;

public class RuleAntecedent {
    public enum Operator {
        IS, IS_NOT
    }
    FuzzyVariable variable;
    Operator operator;
    String setName;

    public RuleAntecedent(FuzzyVariable variable, Operator operator, String setName) {
        this.variable = variable;
        this.operator = operator;
        this.setName = setName;
    }

    public FuzzyVariable getVariable() {
        return variable;
    }

    public void setVariable(FuzzyVariable variable) {
        this.variable = variable;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    @Override
    public String toString() {
        return variable.getName() + " " + operator.toString() + " " + setName;
    }
}
