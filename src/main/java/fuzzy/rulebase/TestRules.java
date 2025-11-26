package fuzzy.rulebase;

import fuzzy.linguistic.FuzzySet;
import fuzzy.linguistic.FuzzyVariable;
import fuzzy.membershipfunctions.IMembershipFunction;
import fuzzy.membershipfunctions.TriangularMF;
import fuzzy.rulebase.persistence.RuleDeserializer;
import fuzzy.rulebase.persistence.RuleFileHandler;
import fuzzy.rulebase.persistence.RuleSerializer;
import fuzzy.util.StaticData;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TestRules {

    public static void main(String[] args) throws Exception {

        FuzzyVariable light = new FuzzyVariable(StaticData.LIGHT_INTENSITY, 0.0, 100.0);
        IMembershipFunction lightDarkMF = new TriangularMF(StaticData.DARK, 0.0, 0.0, 50.0);
        IMembershipFunction lightBrightMF = new TriangularMF(StaticData.BRIGHT, 50.0, 100.0, 100.0);
        light.addFuzzySet(new FuzzySet(StaticData.DARK, lightDarkMF));
        light.addFuzzySet(new FuzzySet(StaticData.BRIGHT, lightBrightMF));

        FuzzyVariable temperature = new FuzzyVariable(StaticData.ROOM_TEMPERATURE, 0.0, 40.0);
        IMembershipFunction tempColdMF = new TriangularMF(StaticData.COLD, 0.0, 0.0, 20.0);
        IMembershipFunction tempHotMF = new TriangularMF(StaticData.HOT, 20.0, 40.0, 40.0);
        temperature.addFuzzySet(new FuzzySet(StaticData.COLD, tempColdMF));
        temperature.addFuzzySet(new FuzzySet(StaticData.HOT, tempHotMF));

        FuzzyVariable blind = new FuzzyVariable(StaticData.BLIND_OPENING, 0.0, 100.0);
        IMembershipFunction blindClosedMF = new TriangularMF(StaticData.CLOSED, 0.0, 0.0, 50.0);
        IMembershipFunction blindOpenMF = new TriangularMF(StaticData.OPENED, 50.0, 100.0, 100.0);
        blind.addFuzzySet(new FuzzySet(StaticData.CLOSED, blindClosedMF));
        blind.addFuzzySet(new FuzzySet(StaticData.OPENED, blindOpenMF));


        FuzzyRuleBase ruleBase = new FuzzyRuleBase();

        FuzzyRule r1 = RuleBuilder.named("R1")
                .when(light, StaticData.BRIGHT)
                .and(temperature, StaticData.HOT)
                .then(blind, StaticData.CLOSED)
                .enabled(true)
                .build();
        ruleBase.addRule(r1);

        FuzzyRule r2 = RuleBuilder.named("R2")
                .when(light, StaticData.DARK)
                .and(temperature, StaticData.COLD)
                .then(blind, StaticData.OPENED)
                .enabled(true)
                .build();
        ruleBase.addRule(r2);

        System.out.println("=== Original Rule Base ===");
        System.out.println(ruleBase);


        RuleSerializer serializer = new RuleSerializer();
        String json = serializer.serialize(ruleBase);
        System.out.println("=== JSON Representation ===");
        System.out.println(json);


        Map<String, FuzzyVariable> variablesByName = new HashMap<>();
        variablesByName.put(light.getName(), light);
        variablesByName.put(temperature.getName(), temperature);
        variablesByName.put(blind.getName(), blind);

        RuleDeserializer deserializer = new RuleDeserializer();
        FuzzyRuleBase loadedFromJson = deserializer.deserialize(json, variablesByName);

        System.out.println("=== Rule Base Loaded From JSON (in-memory) ===");
        System.out.println(loadedFromJson);

        RuleFileHandler fileHandler = new RuleFileHandler();

        Path savedFilePath = Path.of(StaticData.SAVED_RULES_PATH);
        Path definedFilePath = Path.of(StaticData.DEFINED_RULES_PATH);

        fileHandler.save(savedFilePath, ruleBase);
        System.out.println("Rules saved to: " + savedFilePath.toAbsolutePath());

        FuzzyRuleBase loadedFromFile = fileHandler.load(definedFilePath, variablesByName);
        System.out.println("=== Rule Base Loaded From File ===");
        System.out.println(loadedFromFile);
    }
}