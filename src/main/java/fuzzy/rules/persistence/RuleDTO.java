package fuzzy.rules.persistence;

import java.util.List;

public class RuleDTO {
    public String name;
    public  boolean enabled;
    public double weight;
    public String connector;
    public List<RuleAntecedentDTO> antecedents;
    public RuleConsequentDTO consequent;

    public RuleDTO(String name, boolean enabled, double weight, String connector, List<RuleAntecedentDTO> antecedents, RuleConsequentDTO consequent) {
        this.name = name;
        this.enabled = enabled;
        this.weight = weight;
        this.connector = connector;
        this.antecedents = antecedents;
        this.consequent = consequent;
    }
}
