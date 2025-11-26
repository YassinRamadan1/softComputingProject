package fuzzy.rulebase;
import fuzzy.linguistic.FuzzyVariable;

import java.util.List;

public class RuleBuilder {
    private final String name;
    private final List<RuleAntecedent> antecedents = new java.util.ArrayList<>();
    private RuleConnector connector = RuleConnector.AND;
    private RuleConsequent consequent;
    private boolean enabled=true;

    private RuleBuilder (String name) { this.name = name; }

    public static RuleBuilder named(String name) { return new RuleBuilder(name); }

    public RuleBuilder when(FuzzyVariable variable, String setName){
        antecedents.add(new RuleAntecedent(
                variable,
                RuleAntecedent.Operator.IS,
                setName
        ));
        return this;
    }

    public RuleBuilder whenNot(FuzzyVariable variable, String setName){
        antecedents.add(new RuleAntecedent(
                variable,
                RuleAntecedent.Operator.IS_NOT,
                setName
        ));
        return this;
    }

    public RuleBuilder and(FuzzyVariable variable, String setName){
        this.connector=RuleConnector.AND;
        return when(variable, setName);
    }

    public RuleBuilder or(FuzzyVariable variable, String setName){
        this.connector=RuleConnector.OR;
        return when(variable, setName);
    }

    public RuleBuilder then(FuzzyVariable variable, String setName){
        consequent = new RuleConsequent(variable, setName);
        return this;
    }

    public RuleBuilder enabled(boolean enabled){
        this.enabled=enabled;
        return this;
    }

    public FuzzyRule build(){
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("Rule name must not be empty");
        }
        if (antecedents.isEmpty()) {
            throw new IllegalStateException("Rule must have at least one antecedent");
        }
        if (consequent == null) {
            throw new IllegalStateException("Rule must have a consequent");
        }
        return new FuzzyRule(name, antecedents, connector, consequent, enabled);
    }
}
