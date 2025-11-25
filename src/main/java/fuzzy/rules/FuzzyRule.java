package fuzzy.rules;

import java.util.List;

public class FuzzyRule {
    private final String name;
    private final List<RuleAntecedent> antecedents;
    private final RuleConnector connector;
    private final RuleConsequent consequent;
    private boolean enabled;
    private double weight;

    public FuzzyRule(String name, List<RuleAntecedent> antecedents, RuleConnector connector, RuleConsequent consequent, boolean enabled, double weight) {
        this.name = name;
        this.antecedents = antecedents;
        this.connector = connector;
        this.consequent = consequent;
        this.enabled = enabled;
        setWeight(weight);
    }

    public String getName() {
        return name;
    }

    public List<RuleAntecedent> getAntecedents() {
        return antecedents;
    }

    public RuleConnector getConnector() {
        return connector;
    }

    public RuleConsequent getConsequent() {
        return consequent;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if(Double.isNaN(weight) || Double.isInfinite(weight))
            this.weight = 1.0;
        else this.weight = Math.max(0, Math.min(1, weight));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IF ");
        for(int i=0; i<antecedents.size(); ++i) {
            if(i>0) sb.append(" ").append(connector.name()).append(" ");
            sb.append(antecedents.get(i));
        }
        sb.append(" THEN ").append(consequent).append(" (enabled=").append(enabled).append(", weight=").append(weight).append(")");
        return sb.toString();
    }
}
