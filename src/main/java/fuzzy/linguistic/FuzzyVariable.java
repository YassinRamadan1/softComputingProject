package fuzzy.linguistic;

import java.util.*;

public class FuzzyVariable {
    // Temperature 0->40
    private final String name;
    private final double min;
    private final double max;
    // Temperature Variable {low, medium, high} for example
    private final Map<String, FuzzySet> sets = new LinkedHashMap<>();

    public FuzzyVariable(String name, double min, double max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public void addFuzzySet(FuzzySet set) {
        sets.put(set.getName(), set);
    }

    public FuzzySet getSet(String name) {
        return sets.get(name);
    }

    public Collection<FuzzySet> getSets() {
        return sets.values();
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public String getName() {
        return name;
    }

    public Map<String, Double> fuzzify(double crispValue) {
        // to make sure crisp is between the range
        crispValue = Math.max(min, Math.min(max, crispValue));
        Map<String, Double> memberships = new LinkedHashMap<>();

        for (FuzzySet set : sets.values()) {
            double degree = set.getMembership(crispValue);
            memberships.put(set.getName(), degree);
        }
        return memberships; // like {Low=0.2, Medium=0.7, High=0.1}
    }

    @Override
    public String toString() {
        return "FuzzyVariable{" + name + ", sets=" + sets.keySet() + "}";
    }
}