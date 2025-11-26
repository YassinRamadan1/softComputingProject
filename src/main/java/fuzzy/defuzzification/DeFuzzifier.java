package fuzzy.defuzzification;

import fuzzy.linguistic.FuzzyVariable;

import java.util.LinkedHashMap;
import java.util.Map;

public class DeFuzzifier {
    private final Map<String, Double> Memberships = new LinkedHashMap();
    private final FuzzyVariable outputVariable;
    private final DeFuzzificationMethod method;

    public DeFuzzifier(FuzzyVariable outputVariable, Map<String, Double> memberships, DeFuzzificationMethod method) {
        this.outputVariable = outputVariable;
        this.Memberships.putAll(memberships);
        this.method = method;
    }

    public double getCrispOutput() {
        return method.getCrispOutput();
    }

    public String getCrispSet() {
        double v = method.getCrispOutput();
        double maxMembership = Double.NEGATIVE_INFINITY;
        String bestSet = "";
        for (Map.Entry<String, Double> entry : Memberships.entrySet()) {
            if (maxMembership < outputVariable.getSet(entry.getKey()).getMembership(v)) {
                maxMembership = outputVariable.getSet(entry.getKey()).getMembership(v);
                bestSet = entry.getKey();
            }
        }
        return bestSet;
    }
}
