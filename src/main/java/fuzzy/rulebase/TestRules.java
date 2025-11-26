package fuzzy.rulebase;

import fuzzy.linguistic.FuzzySet;
import fuzzy.linguistic.FuzzyVariable;
import fuzzy.membershipfunctions.IMembershipFunction;
import fuzzy.membershipfunctions.TriangularMF;
import fuzzy.rulebase.persistence.RuleDeserializer;
import fuzzy.rulebase.persistence.RuleFileHandler;
import fuzzy.rulebase.persistence.RuleSerializer;
import fuzzy.util.SD;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TestRules {

    public static void main(String[] args) throws Exception {

        FuzzyVariable light = new FuzzyVariable("Light", 0.0, 100.0);
        IMembershipFunction lightDarkMF = new TriangularMF("Dark", 0.0, 0.0, 50.0);
        IMembershipFunction lightBrightMF = new TriangularMF("Bright", 50.0, 100.0, 100.0);
        light.addFuzzySet(new FuzzySet("Dark", lightDarkMF));
        light.addFuzzySet(new FuzzySet("Bright", lightBrightMF));

        FuzzyVariable temperature = new FuzzyVariable("Temperature", 0.0, 40.0);
        IMembershipFunction tempColdMF = new TriangularMF("Cold", 0.0, 0.0, 20.0);
        IMembershipFunction tempHotMF = new TriangularMF("Hot", 20.0, 40.0, 40.0);
        temperature.addFuzzySet(new FuzzySet("Cold", tempColdMF));
        temperature.addFuzzySet(new FuzzySet("Hot", tempHotMF));

        FuzzyVariable blind = new FuzzyVariable("BlindOpening", 0.0, 100.0);
        IMembershipFunction blindClosedMF = new TriangularMF("Closed", 0.0, 0.0, 50.0);
        IMembershipFunction blindOpenMF = new TriangularMF("Open", 50.0, 100.0, 100.0);
        blind.addFuzzySet(new FuzzySet("Closed", blindClosedMF));
        blind.addFuzzySet(new FuzzySet("Open", blindOpenMF));


        FuzzyRuleBase ruleBase = new FuzzyRuleBase();

        FuzzyRule r1 = RuleBuilder.named("R1")
                .when(light, "Bright")
                .and(temperature, "Hot")
                .then(blind, "Closed")
                .enabled(true)
                .build();
        ruleBase.addRule(r1);

        FuzzyRule r2 = RuleBuilder.named("R2")
                .when(light, "Dark")
                .and(temperature, "Cold")
                .then(blind, "Open")
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

        Path savedFilePath = Path.of(SD.SAVED_RULES_PATH);
        Path definedFilePath = Path.of(SD.DEFINED_RULES_PATH);

        fileHandler.save(savedFilePath, ruleBase);
        System.out.println("Rules saved to: " + savedFilePath.toAbsolutePath());

        FuzzyRuleBase loadedFromFile = fileHandler.load(definedFilePath, variablesByName);
        System.out.println("=== Rule Base Loaded From File ===");
        System.out.println(loadedFromFile);
    }
}