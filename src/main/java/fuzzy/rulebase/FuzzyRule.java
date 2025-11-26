package fuzzy.rulebase;

import java.util.List;

public class FuzzyRule {
    private final String name;
    private final List<RuleAntecedent> antecedents;
    private final RuleConnector connector;
    private final RuleConsequent consequent;
    private boolean enabled;

    public FuzzyRule(String name, List<RuleAntecedent> antecedents, RuleConnector connector, RuleConsequent consequent, boolean enabled) {
        this.name = name;
        this.antecedents = antecedents;
        this.connector = connector;
        this.consequent = consequent;
        this.enabled = enabled;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IF ");
        for(int i=0; i<antecedents.size(); ++i) {
            if(i>0) sb.append(" ").append(connector.name()).append(" ");
            sb.append(antecedents.get(i));
        }
        sb.append(" THEN ").append(consequent).append(" (enabled=").append(enabled).append(")");
        return sb.toString();
    }
}
