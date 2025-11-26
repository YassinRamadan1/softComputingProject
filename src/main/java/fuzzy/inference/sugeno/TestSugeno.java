package fuzzy.inference.sugeno;

import fuzzy.config.FuzzyConfiguration;
import fuzzy.config.OperatorFactory;
import fuzzy.inference.InferenceResult;
import fuzzy.linguistic.FuzzySet;
import fuzzy.linguistic.FuzzyVariable;
import fuzzy.membershipfunctions.TriangularMF;
import fuzzy.rulebase.FuzzyRuleBase;
import fuzzy.rulebase.RuleBuilder;
import fuzzy.util.StaticData;

import java.util.HashMap;
import java.util.Map;

public class TestSugeno {

    public static void main(String[] args) {
        FuzzyVariable light = new FuzzyVariable(StaticData.LIGHT_INTENSITY, 0.0, 100.0);
        light.addFuzzySet(new FuzzySet(StaticData.DARK, new TriangularMF(StaticData.DARK, 0, 0, 50)));
        light.addFuzzySet(new FuzzySet(StaticData.BRIGHT, new TriangularMF(StaticData.BRIGHT, 50, 100, 100)));

        FuzzyVariable temp = new FuzzyVariable(StaticData.ROOM_TEMPERATURE, 0.0, 40.0);
        temp.addFuzzySet(new FuzzySet(StaticData.COLD, new TriangularMF(StaticData.COLD, 0, 0, 20)));
        temp.addFuzzySet(new FuzzySet(StaticData.HOT, new TriangularMF(StaticData.HOT, 20, 40, 40)));

        FuzzyVariable blind = new FuzzyVariable(StaticData.BLIND_OPENING, 0.0, 100.0);

        FuzzyRuleBase ruleBase = new FuzzyRuleBase();

        ruleBase.addRule(RuleBuilder.named("R1")
                .when(light, StaticData.BRIGHT)
                .and(temp, StaticData.HOT)
                .then(blind,StaticData.CLOSED)
                .build());

        ruleBase.addRule(RuleBuilder.named("R2")
                .when(light, StaticData.DARK)
                .and(temp, StaticData.COLD)
                .then(blind, StaticData.OPENED)
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
        fuzzifiedInputs.put(StaticData.LIGHT_INTENSITY, light.fuzzify(75.0));
        fuzzifiedInputs.put(StaticData.ROOM_TEMPERATURE, temp.fuzzify(30.0));

        System.out.println("=== Fuzzified Inputs ===");
        System.out.println(StaticData.LIGHT_INTENSITY + ": " + fuzzifiedInputs.get(StaticData.LIGHT_INTENSITY));
        System.out.println(StaticData.ROOM_TEMPERATURE + ": " + fuzzifiedInputs.get(StaticData.ROOM_TEMPERATURE));

        InferenceResult result = engine.evaluate(fuzzifiedInputs, ruleBase, blind);

        System.out.println("\n=== Inference Result ===");
        System.out.println(result);

        double crispOutput = result.getAggregatedOutputMemberships().get("SugenoCrispOutput");

        System.out.println("\n=== Final Crisp Output ===");
        System.out.println("Blind Opening: " + crispOutput + "%");
    }
}