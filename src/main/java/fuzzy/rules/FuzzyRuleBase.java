package fuzzy.rules;

import java.util.*;

public class FuzzyRuleBase {
    private final Map<String, FuzzyRule> rules = new HashMap<>();

    public void addRule(FuzzyRule rule) {
        Objects.requireNonNull(rule, "rule cannot be null");
        rules.put(rule.getName(), rule);
    }
    public FuzzyRule getRule(String name) {
        return rules.get(name);
    }
    public Collection <FuzzyRule> getRules() {
        return Collections.unmodifiableCollection(rules.values());
    }
    public void setRuleWeight(String name, double weight) {
        FuzzyRule rule = rules.get(name);
        if(rule!=null)
            rule.setWeight(weight);
        else System.out.println("Rule not found");
    }
    public void enableRule(String name) {
        FuzzyRule rule = rules.get(name);
        if(rule!=null)
            rule.setEnabled(true);
        else System.out.println("Rule not found");
    }
    public void disableRule(String name) {
        FuzzyRule rule = rules.get(name);
        if(rule!=null)
            rule.setEnabled(false);
        else System.out.println("Rule not found");
    }
    public void clearRules() {
        rules.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Fuzzy Rule Base:\n");
        for(FuzzyRule rule : rules.values()) {
            sb.append(rule.toString()).append("\n");
        }
        return sb.toString();
    }
}
