package fuzzy.defuzzification;

import fuzzy.linguistic.FuzzyVariable;

import java.util.LinkedHashMap;
import java.util.Map;

public class WeightedAverage implements DeFuzzificationMethod {
    private final FuzzyVariable outputVariable;
    private final Map<String, Double> Memberships = new LinkedHashMap<>();

    public WeightedAverage(FuzzyVariable outputVariable, Map<String, Double> memberships) {
        this.outputVariable = outputVariable;
        this.Memberships.putAll(memberships);
    }

    @Override
    public double getCrispOutput() {

        double numerator = 0.0;
        double denominator = 0.0;
        for (Map.Entry<String, Double> entry : Memberships.entrySet()) {
            numerator += outputVariable.getSet(entry.getKey()).calculateCentroid() * entry.getValue();
            denominator += entry.getValue();
        }
        if (denominator == 0){
            return 0.0;
        }
        return numerator / denominator;
    }
}
