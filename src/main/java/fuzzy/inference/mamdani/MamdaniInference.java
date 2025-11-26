package fuzzy.inference.mamdani;

import fuzzy.inference.AntecedentEvaluator;
import fuzzy.inference.InferenceEngine;
import fuzzy.inference.InferenceResult;
import fuzzy.operators.aggregation.Aggregation;
import fuzzy.operators.implication.Implication;
import fuzzy.operators.snorm.SNorm;
import fuzzy.operators.tnorm.TNorm;
import fuzzy.rulebase.FuzzyRule;
import fuzzy.rulebase.FuzzyRuleBase;
import fuzzy.linguistic.FuzzySet;
import fuzzy.linguistic.FuzzyVariable;
import java.util.LinkedHashMap;
import java.util.Map;

public class MamdaniInference implements InferenceEngine {
    private final TNorm andOperator;
    private final SNorm orOperator;
    private final Implication impliesOperator;
    private final Aggregation aggregationOperator;

    public MamdaniInference(TNorm andOperator, SNorm orOperator, Implication impliesOperator, Aggregation aggregationOperator) {
        this.andOperator = andOperator;
        this.orOperator = orOperator;
        this.impliesOperator = impliesOperator;
        this.aggregationOperator = aggregationOperator;
    }

    @Override
    public InferenceResult evaluate(Map<String, Map<String, Double>> fuzzifiedInputs, FuzzyRuleBase rules, FuzzyVariable outputVariable) {
        Map<String, Double> aggregatedMemberships = new LinkedHashMap<>();

        for (FuzzySet set : outputVariable.getSets()) {
            aggregatedMemberships.put(set.getName(), 0.0);
        }

        for (FuzzyRule rule : rules.getRules()) {
            if (!rule.isEnabled()) continue;

            AntecedentEvaluator evaluator = new AntecedentEvaluator(andOperator, orOperator);
            double antecedentStrength = evaluator.evaluate(rule, fuzzifiedInputs);

            String consequentSetName = rule.getConsequent().getSetName();
            double currentMembership = aggregatedMemberships.get(consequentSetName);
            double impliedMembership = impliesOperator.apply(antecedentStrength, 1.0);
            double newMembership = aggregationOperator.apply(currentMembership, impliedMembership);

            aggregatedMemberships.put(consequentSetName, newMembership);
        }
        return new InferenceResult(outputVariable, aggregatedMemberships);
    }
}
