package fuzzy.inference;

import fuzzy.variables.FuzzyVariable;
import java.util.LinkedHashMap;
import java.util.Map;

public class InferenceResult {
    private final FuzzyVariable outputVariable;
    private final Map<String, Double> aggregatedOutputMemberships;

    public InferenceResult(FuzzyVariable outputVariable, Map<String, Double> aggregatedOutputMemberships) {
        this.outputVariable = outputVariable;
        this.aggregatedOutputMemberships = new LinkedHashMap<>(aggregatedOutputMemberships);
    }

    public FuzzyVariable getOutputVariable() {
        return outputVariable;
    }
    public Map<String, Double> getAggregatedOutputMemberships() {
        return aggregatedOutputMemberships;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InferenceResult {\n");
        sb.append("  Output Variable: ").append(outputVariable.getName()).append("\n");
        sb.append("  Aggregated Memberships (fuzzy output):\n");
        for (Map.Entry<String, Double> entry : aggregatedOutputMemberships.entrySet()) {
            sb.append("    ").append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
