package fuzzy.rulebase.persistence;

import java.util.List;

public class RuleDTO {
    public String name;
    public boolean enabled;
    public String connector;
    public List<RuleAntecedentDTO> antecedents;
    public RuleConsequentDTO consequent;

    public RuleDTO(String name, boolean enabled, String connector, List<RuleAntecedentDTO> antecedents, RuleConsequentDTO consequent) {
        this.name = name;
        this.enabled = enabled;
        this.connector = connector;
        this.antecedents = antecedents;
        this.consequent = consequent;
    }
}
