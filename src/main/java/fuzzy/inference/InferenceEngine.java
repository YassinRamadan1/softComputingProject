package fuzzy.inference;

import fuzzy.rulebase.FuzzyRuleBase;
import fuzzy.linguistic.FuzzyVariable;
import java.util.Map;

public interface InferenceEngine {
    InferenceResult evaluate(Map<String, Map<String, Double>> fuzzifiedInputs, FuzzyRuleBase ruleBase, FuzzyVariable outputVariable);
}
