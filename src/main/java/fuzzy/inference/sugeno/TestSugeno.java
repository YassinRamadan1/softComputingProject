package fuzzy.inference.sugeno;

import fuzzy.config.FuzzyConfiguration;
import fuzzy.config.OperatorFactory;
import fuzzy.inference.InferenceResult;
import fuzzy.linguistic.FuzzySet;
import fuzzy.linguistic.FuzzyVariable;
import fuzzy.membershipfunctions.TriangularMF;
import fuzzy.rulebase.FuzzyRuleBase;
import fuzzy.rulebase.RuleBuilder;

import java.util.HashMap;
import java.util.Map;

public class TestSugeno {

    public static void main(String[] args) {
        FuzzyVariable light = new FuzzyVariable("Light", 0.0, 100.0);
        light.addFuzzySet(new FuzzySet("Dark", new TriangularMF("Dark", 0, 0, 50)));
        light.addFuzzySet(new FuzzySet("Bright", new TriangularMF("Bright", 50, 100, 100)));

        FuzzyVariable temp = new FuzzyVariable("Temperature", 0.0, 40.0);
        temp.addFuzzySet(new FuzzySet("Cold", new TriangularMF("Cold", 0, 0, 20)));
        temp.addFuzzySet(new FuzzySet("Hot", new TriangularMF("Hot", 20, 40, 40)));

        FuzzyVariable blind = new FuzzyVariable("BlindOpening", 0.0, 100.0);

        FuzzyRuleBase ruleBase = new FuzzyRuleBase();

        ruleBase.addRule(RuleBuilder.named("R1")
                .when(light, "Bright")
                .and(temp, "Hot")
                .then(blind, "Closed")
                .build());

        ruleBase.addRule(RuleBuilder.named("R2")
                .when(light, "Dark")
                .and(temp, "Cold")
                .then(blind, "Open")
                .build());

        Map<String, SugenoOutput> sugenoOutputs = new HashMap<>();
        sugenoOutputs.put("R1", new SugenoOutput(10.0));
        sugenoOutputs.put("R2", new SugenoOutput(90.0));

        FuzzyConfiguration config = FuzzyConfiguration.getDefaultConfiguration();

        SugenoInference engine = new SugenoInference(
                OperatorFactory.createTNorm(config.getAndOperatorType()),
                OperatorFactory.createSNorm(config.getOrOperatorType()),
                sugenoOutputs
        );

        Map<String, Map<String, Double>> fuzzifiedInputs = new HashMap<>();
        fuzzifiedInputs.put("Light", light.fuzzify(75.0));
        fuzzifiedInputs.put("Temperature", temp.fuzzify(30.0));

        System.out.println("=== Fuzzified Inputs ===");
        System.out.println("Light: " + fuzzifiedInputs.get("Light"));
        System.out.println("Temperature: " + fuzzifiedInputs.get("Temperature"));

        InferenceResult result = engine.evaluate(fuzzifiedInputs, ruleBase, blind);

        System.out.println("\n=== Inference Result ===");
        System.out.println(result);

        double crispOutput = result.getAggregatedOutputMemberships().get("SugenoCrispOutput");

        System.out.println("\n=== Final Crisp Output ===");
        System.out.println("Blind Opening: " + crispOutput + "%");
    }
}