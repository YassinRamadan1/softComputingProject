package fuzzy.inference;

import fuzzy.linguistic.FuzzyVariable;
import fuzzy.rulebase.FuzzyRuleBase;

import java.util.Map;

public interface InferenceEngine {
    InferenceResult evaluate(Map<String, Map<String, Double>> fuzzifiedInputs, FuzzyRuleBase ruleBase, FuzzyVariable outputVariable);
}
