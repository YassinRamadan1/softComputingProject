package fuzzy.inference.sugeno;

import fuzzy.inference.AntecedentEvaluator;
import fuzzy.inference.InferenceEngine;
import fuzzy.inference.InferenceResult;
import fuzzy.operators.snorm.SNorm;
import fuzzy.operators.tnorm.TNorm;
import fuzzy.rulebase.FuzzyRule;
import fuzzy.rulebase.FuzzyRuleBase;
import fuzzy.linguistic.FuzzyVariable;
import java.util.LinkedHashMap;
import java.util.Map;

public class SugenoInference implements InferenceEngine {
    private final TNorm andOperator;
    private final SNorm orOperator;
    private final Map<String, SugenoOutput> ruleOutputs;

    public SugenoInference(TNorm andOperator, SNorm orOperator, Map<String, SugenoOutput> ruleOutputs) {
        this.andOperator = andOperator;
        this.orOperator = orOperator;
        this.ruleOutputs = ruleOutputs;
    }

    @Override
    public InferenceResult evaluate(Map<String, Map<String, Double>> fuzzifiedInputs, FuzzyRuleBase ruleBase, FuzzyVariable outputVariable) {
        double numerator = 0.0;
        double denominator = 0.0;

        for (FuzzyRule rule : ruleBase.getRules()) {
            if (!rule.isEnabled()) continue;

            AntecedentEvaluator evaluator = new AntecedentEvaluator(andOperator, orOperator);
            double antecedentStrength = evaluator.evaluate(rule, fuzzifiedInputs);

            SugenoOutput output = ruleOutputs.get(rule.getName());
            if(output==null) throw new IllegalStateException("No Sugeno output defined for rule: " + rule.getName());

            double ruleOutputValue = output.getConstantValue();
            numerator+=antecedentStrength*ruleOutputValue;
            denominator+=antecedentStrength;
        }

        double crispOutput = 0.0;
        if (denominator>0) crispOutput=numerator/denominator;

        Map<String, Double> output = new LinkedHashMap<>();
        output.put("SugenoCrispOutput", crispOutput);

        return new InferenceResult(outputVariable, output);
    }
}
