package fuzzy.inference.mamdani;

import fuzzy.config.FuzzyConfiguration;
import fuzzy.config.OperatorFactory;
import fuzzy.defuzzification.DeFuzzifier;
import fuzzy.defuzzification.MeanOfMax;
import fuzzy.inference.InferenceResult;
import fuzzy.membershipfunctions.TriangularMF;
import fuzzy.rulebase.FuzzyRuleBase;
import fuzzy.rulebase.RuleBuilder;
import fuzzy.linguistic.FuzzySet;
import fuzzy.linguistic.FuzzyVariable;
import java.util.HashMap;
import java.util.Map;

public class TestMamdani {

    public static void main(String[] args) {
        FuzzyVariable light = new FuzzyVariable("Light", 0.0, 100.0);
        light.addFuzzySet(new FuzzySet("Dark", new TriangularMF("Dark", 0, 0, 50)));
        light.addFuzzySet(new FuzzySet("Bright", new TriangularMF("Bright", 50, 100, 100)));

        FuzzyVariable temp = new FuzzyVariable("Temperature", 0.0, 40.0);
        temp.addFuzzySet(new FuzzySet("Cold", new TriangularMF("Cold", 0, 0, 20)));
        temp.addFuzzySet(new FuzzySet("Hot", new TriangularMF("Hot", 20, 40, 40)));

        FuzzyVariable blind = new FuzzyVariable("BlindOpening", 0.0, 100.0);
        blind.addFuzzySet(new FuzzySet("Closed", new TriangularMF("Closed", 0, 0, 50)));
        blind.addFuzzySet(new FuzzySet("Open", new TriangularMF("Open", 50, 100, 100)));

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

        FuzzyConfiguration config = FuzzyConfiguration.getDefaultConfiguration();

        MamdaniInference engine = new MamdaniInference(
                OperatorFactory.createTNorm(config.getAndOperatorType()),
                OperatorFactory.createSNorm(config.getOrOperatorType()),
                OperatorFactory.createImplication(config.getImplicationOperatorType()),
                OperatorFactory.createAggregation(config.getAggregationOperatorType())
        );

        Map<String, Map<String, Double>> fuzzifiedInputs = new HashMap<>();
        fuzzifiedInputs.put("Light", light.fuzzify(75.0));
        fuzzifiedInputs.put("Temperature", temp.fuzzify(30.0));

        System.out.println("=== Fuzzified Inputs ===");
        System.out.println("Light: " + fuzzifiedInputs.get("Light"));
        System.out.println("Temperature: " + fuzzifiedInputs.get("Temperature"));

        InferenceResult result = engine.evaluate(fuzzifiedInputs, ruleBase, blind);

        System.out.println("\n=== Inference Result (Fuzzy Output) ===");
        System.out.println(result);

        DeFuzzifier defuzzifier = new DeFuzzifier(
                blind,
                result.getAggregatedOutputMemberships(),
                new MeanOfMax(result.getOutputVariable(), result.getAggregatedOutputMemberships())
        );

        double crispOutput = defuzzifier.getCrispOutput();
        String crispSet = defuzzifier.getCrispSet();

        System.out.println("\n=== Defuzzified Output ===");
        System.out.println("Crisp Blind Opening: " + crispOutput + "%, Crips Blind Set: " + crispSet);
    }
}