package fuzzy.examples;

import fuzzy.config.ConfigLoader;
import fuzzy.config.FuzzyConfiguration;
import fuzzy.config.OperatorFactory;
import fuzzy.defuzzification.DeFuzzificationMethod;
import fuzzy.defuzzification.DeFuzzifier;
import fuzzy.defuzzification.MeanOfMax;
import fuzzy.defuzzification.WeightedAverage;
import fuzzy.inference.InferenceResult;
import fuzzy.inference.mamdani.MamdaniInference;
import fuzzy.linguistic.Fuzzifier;
import fuzzy.linguistic.FuzzySet;
import fuzzy.linguistic.FuzzyVariable;
import fuzzy.membershipfunctions.IMembershipFunction;
import fuzzy.membershipfunctions.TrapezoidalMF;
import fuzzy.membershipfunctions.TriangularMF;
import fuzzy.operators.aggregation.Aggregation;
import fuzzy.operators.implication.Implication;
import fuzzy.operators.snorm.SNorm;
import fuzzy.operators.tnorm.TNorm;
import fuzzy.rulebase.FuzzyRuleBase;
import fuzzy.rulebase.RuleBuilder;
import fuzzy.rulebase.persistence.RuleFileHandler;
import fuzzy.util.SD;
import fuzzy.validation.InputValidator;
import fuzzy.validation.InputValidationStrategy;
import fuzzy.validation.InvalidInputException;
import fuzzy.validation.ValidationStrategyFactory;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SmartBlindControl {
    private FuzzyVariable lightIntensity;
    private FuzzyVariable roomTemperature;
    private FuzzyVariable blindOpening;
    private FuzzyRuleBase ruleBase;
    private MamdaniInference inference;
    private FuzzyConfiguration config;
    private InputValidator inputValidator;
    private final boolean loadFromFiles;

    public SmartBlindControl(boolean loadFromFiles) {
        this.loadFromFiles = loadFromFiles;
        initialize();
    }

    private void initialize() {
        if (loadFromFiles) {
            config = ConfigLoader.load();

            System.out.println("Configuration loaded from: " + SD.CONFIG_PATH);
            lightIntensity = createVariableFromConfig(config.getInputVariables()[0]);
            roomTemperature = createVariableFromConfig(config.getInputVariables()[1]);
            blindOpening = createVariableFromConfig(config.getOutputVariable());

            ruleBase = loadRulesFromFile();
        } else {
            config = createManualConfiguration();
            System.out.println("Using manually defined configuration.");

            lightIntensity = createLightIntensityVariable();
            roomTemperature = createRoomTemperatureVariable();
            blindOpening = createBlindOpeningVariable();

            ruleBase = createManualRules();
            System.out.println("Using manually defined rules. Total: " + ruleBase.getRules().size());
        }

        TNorm tNorm = OperatorFactory.createTNorm(config.getAndOperatorType());
        SNorm sNorm = OperatorFactory.createSNorm(config.getOrOperatorType());
        Implication implication = OperatorFactory.createImplication(config.getImplicationOperatorType());
        Aggregation aggregation = OperatorFactory.createAggregation(config.getAggregationOperatorType());
        inference = new MamdaniInference(tNorm, sNorm, implication, aggregation);

        // Initialize input validator using config strategy
        InputValidationStrategy strategy = ValidationStrategyFactory.createStrategy(config.getValidationStrategyType(), true);
        inputValidator = new InputValidator(strategy);
    }

    private FuzzyVariable createVariableFromConfig(FuzzyConfiguration.VariableConfig varConfig) {
        FuzzyVariable variable = new FuzzyVariable(varConfig.getName(), varConfig.getMin(), varConfig.getMax());
        
        for (FuzzyConfiguration.FuzzySetConfig setConfig : varConfig.getFuzzySets()) {
            IMembershipFunction mf = createMembershipFunction(setConfig);
            variable.addFuzzySet(new FuzzySet(setConfig.getName(), mf));
        }
        
        return variable;
    }

    private IMembershipFunction createMembershipFunction(FuzzyConfiguration.FuzzySetConfig setConfig) {
        double[] params = setConfig.getParams();
        return switch (setConfig.getType()) {
            case TRIANGULAR -> new TriangularMF(setConfig.getName(), params[0], params[1], params[2]);
            case TRAPEZOIDAL -> new TrapezoidalMF(setConfig.getName(), params[0], params[1], params[2], params[3]);
        };
    }

    private FuzzyConfiguration createManualConfiguration() {
        FuzzyConfiguration manualConfig = FuzzyConfiguration.getDefaultConfiguration();
        manualConfig.setAndOperatorType(FuzzyConfiguration.AndOperatorType.MIN);
        manualConfig.setOrOperatorType(FuzzyConfiguration.OrOperatorType.MAX);
        manualConfig.setImplicationOperatorType(FuzzyConfiguration.ImplicationOperatorType.MIN);
        manualConfig.setAggregationOperatorType(FuzzyConfiguration.AggregationOperatorType.MAX);
        manualConfig.setDefuzzificationMethodType(FuzzyConfiguration.DefuzzificationMethodType.MEAN_OF_MAX);
        return manualConfig;
    }

    private FuzzyRuleBase loadRulesFromFile() {
        Map<String, FuzzyVariable> variableMap = new HashMap<>();
        variableMap.put(lightIntensity.getName(), lightIntensity);
        variableMap.put(roomTemperature.getName(), roomTemperature);
        variableMap.put(blindOpening.getName(), blindOpening);

        try {
            RuleFileHandler fileHandler = new RuleFileHandler();
            String rulesPath = config.getRulesFile();
            if (rulesPath == null || rulesPath.isEmpty()) {
                rulesPath = SD.DEFINED_RULES_PATH;
            }
            System.out.println("Loading rules from: " + rulesPath);
            FuzzyRuleBase loaded = fileHandler.load(Path.of(rulesPath), variableMap);
            System.out.println("Rules loaded successfully. Total: " + loaded.getRules().size());
            return loaded;
        } catch (Exception e) {
            System.out.println("Could not load rules from file: " + e.getMessage());
            System.out.println("Falling back to manual rules.");
            return createManualRules();
        }
    }

    private FuzzyRuleBase createManualRules() {
        FuzzyRuleBase rules = new FuzzyRuleBase();
        rules.addRule(RuleBuilder.named("R1").when(lightIntensity, "VeryHigh").and(roomTemperature, "Hot").then(blindOpening, "Closed").build());
        rules.addRule(RuleBuilder.named("R2").when(lightIntensity, "VeryHigh").and(roomTemperature, "Warm").then(blindOpening, "Closed").build());
        rules.addRule(RuleBuilder.named("R3").when(lightIntensity, "VeryHigh").and(roomTemperature, "Comfortable").then(blindOpening, "SlightlyOpen").build());
        rules.addRule(RuleBuilder.named("R4").when(lightIntensity, "VeryHigh").and(roomTemperature, "Cold").then(blindOpening, "HalfOpen").build());
        rules.addRule(RuleBuilder.named("R5").when(lightIntensity, "VeryHigh").and(roomTemperature, "VeryCold").then(blindOpening, "MostlyOpen").build());
        rules.addRule(RuleBuilder.named("R6").when(lightIntensity, "High").and(roomTemperature, "Hot").then(blindOpening, "Closed").build());
        rules.addRule(RuleBuilder.named("R7").when(lightIntensity, "High").and(roomTemperature, "Warm").then(blindOpening, "SlightlyOpen").build());
        rules.addRule(RuleBuilder.named("R8").when(lightIntensity, "High").and(roomTemperature, "Comfortable").then(blindOpening, "HalfOpen").build());
        rules.addRule(RuleBuilder.named("R9").when(lightIntensity, "High").and(roomTemperature, "Cold").then(blindOpening, "MostlyOpen").build());
        rules.addRule(RuleBuilder.named("R10").when(lightIntensity, "High").and(roomTemperature, "VeryCold").then(blindOpening, "FullyOpen").build());
        rules.addRule(RuleBuilder.named("R11").when(lightIntensity, "Medium").and(roomTemperature, "Hot").then(blindOpening, "SlightlyOpen").build());
        rules.addRule(RuleBuilder.named("R12").when(lightIntensity, "Medium").and(roomTemperature, "Warm").then(blindOpening, "HalfOpen").build());
        rules.addRule(RuleBuilder.named("R13").when(lightIntensity, "Medium").and(roomTemperature, "Comfortable").then(blindOpening, "HalfOpen").build());
        rules.addRule(RuleBuilder.named("R14").when(lightIntensity, "Medium").and(roomTemperature, "Cold").then(blindOpening, "MostlyOpen").build());
        rules.addRule(RuleBuilder.named("R15").when(lightIntensity, "Medium").and(roomTemperature, "VeryCold").then(blindOpening, "FullyOpen").build());
        rules.addRule(RuleBuilder.named("R16").when(lightIntensity, "Low").and(roomTemperature, "Hot").then(blindOpening, "HalfOpen").build());
        rules.addRule(RuleBuilder.named("R17").when(lightIntensity, "Low").and(roomTemperature, "Warm").then(blindOpening, "MostlyOpen").build());
        rules.addRule(RuleBuilder.named("R18").when(lightIntensity, "Low").and(roomTemperature, "Comfortable").then(blindOpening, "MostlyOpen").build());
        rules.addRule(RuleBuilder.named("R19").when(lightIntensity, "Low").and(roomTemperature, "Cold").then(blindOpening, "FullyOpen").build());
        rules.addRule(RuleBuilder.named("R20").when(lightIntensity, "Low").and(roomTemperature, "VeryCold").then(blindOpening, "FullyOpen").build());
        rules.addRule(RuleBuilder.named("R21").when(lightIntensity, "VeryLow").and(roomTemperature, "Hot").then(blindOpening, "MostlyOpen").build());
        rules.addRule(RuleBuilder.named("R22").when(lightIntensity, "VeryLow").and(roomTemperature, "Warm").then(blindOpening, "FullyOpen").build());
        rules.addRule(RuleBuilder.named("R23").when(lightIntensity, "VeryLow").and(roomTemperature, "Comfortable").then(blindOpening, "FullyOpen").build());
        rules.addRule(RuleBuilder.named("R24").when(lightIntensity, "VeryLow").and(roomTemperature, "Cold").then(blindOpening, "FullyOpen").build());
        rules.addRule(RuleBuilder.named("R25").when(lightIntensity, "VeryLow").and(roomTemperature, "VeryCold").then(blindOpening, "FullyOpen").build());
        return rules;
    }

    private FuzzyVariable createLightIntensityVariable() {
        FuzzyVariable light = new FuzzyVariable("LightIntensity", 0.0, 1000.0);
        light.addFuzzySet(new FuzzySet("VeryLow", new TrapezoidalMF("VeryLow", 0, 0, 30, 100)));
        light.addFuzzySet(new FuzzySet("Low", new TrapezoidalMF("Low", 80, 150, 250, 350)));
        light.addFuzzySet(new FuzzySet("Medium", new TriangularMF("Medium", 300, 500, 700)));
        light.addFuzzySet(new FuzzySet("High", new TrapezoidalMF("High", 650, 750, 850, 920)));
        light.addFuzzySet(new FuzzySet("VeryHigh", new TrapezoidalMF("VeryHigh", 900, 950, 1000, 1000)));
        return light;
    }

    private FuzzyVariable createRoomTemperatureVariable() {
        FuzzyVariable temp = new FuzzyVariable("RoomTemperature", 0.0, 40.0);
        temp.addFuzzySet(new FuzzySet("VeryCold", new TriangularMF("VeryCold", 0, 0, 8)));
        temp.addFuzzySet(new FuzzySet("Cold", new TriangularMF("Cold", 5, 12, 17)));
        temp.addFuzzySet(new FuzzySet("Comfortable", new TriangularMF("Comfortable", 15, 22, 27)));
        temp.addFuzzySet(new FuzzySet("Warm", new TriangularMF("Warm", 25, 30, 34)));
        temp.addFuzzySet(new FuzzySet("Hot", new TriangularMF("Hot", 32, 40, 40)));
        return temp;
    }

    private FuzzyVariable createBlindOpeningVariable() {
        FuzzyVariable blind = new FuzzyVariable("BlindOpening", 0.0, 100.0);
        blind.addFuzzySet(new FuzzySet("Closed", new TriangularMF("Closed", 0, 0, 10)));
        blind.addFuzzySet(new FuzzySet("SlightlyOpen", new TriangularMF("SlightlyOpen", 5, 20, 40)));
        blind.addFuzzySet(new FuzzySet("HalfOpen", new TriangularMF("HalfOpen", 30, 50, 70)));
        blind.addFuzzySet(new FuzzySet("MostlyOpen", new TriangularMF("MostlyOpen", 60, 75, 90)));
        blind.addFuzzySet(new FuzzySet("FullyOpen", new TriangularMF("FullyOpen", 85, 100, 100)));
        return blind;
    }

    private DeFuzzificationMethod createDefuzzificationMethod(Map<String, Double> memberships) {
        return switch (config.getDefuzzificationMethodType()) {
            case WEIGHTED_AVERAGE -> new WeightedAverage(blindOpening, memberships);
            case MEAN_OF_MAX -> new MeanOfMax(blindOpening, memberships);
        };
    }

    public double evaluate(double light, double temperature) {
        try {
            light = inputValidator.validateInput(light, lightIntensity);
            temperature = inputValidator.validateInput(temperature, roomTemperature);
        } catch (InvalidInputException e) {
            System.out.println("Validation error: " + e.getMessage());
            return -1;
        }

        Fuzzifier fuzzifier = new Fuzzifier();
        Map<String, Double> lightMemberships = fuzzifier.fuzzify(lightIntensity, light);
        Map<String, Double> tempMemberships = fuzzifier.fuzzify(roomTemperature, temperature);

        Map<String, Map<String, Double>> fuzzifiedInputs = new HashMap<>();
        fuzzifiedInputs.put(lightIntensity.getName(), lightMemberships);
        fuzzifiedInputs.put(roomTemperature.getName(), tempMemberships);

        InferenceResult result = inference.evaluate(fuzzifiedInputs, ruleBase, blindOpening);

        Map<String, Double> outputMemberships = result.getAggregatedOutputMemberships();
        DeFuzzificationMethod method = createDefuzzificationMethod(outputMemberships);
        DeFuzzifier defuzzifier = new DeFuzzifier(blindOpening, outputMemberships, method);

        double crispOutput = defuzzifier.getCrispOutput();
        String crispSet = defuzzifier.getCrispSet();

        System.out.println("Light: " + light + ", Temp: " + temperature + " => Blind Opening: (" + String.format("%.2f", crispOutput) + "%), The Crisp Set: {'" + crispSet + "'}");

        return crispOutput;
    }

    public void runTestScenariosFromConfig() {
        FuzzyConfiguration.TestScenario[] scenarios = config.getTestScenarios();
        if (scenarios == null || scenarios.length == 0) {
            System.out.println("No test scenarios found in config. Using hardcoded scenarios.");
            runHardcodedTestScenarios();
            return;
        }
        for (FuzzyConfiguration.TestScenario scenario : scenarios) {
            System.out.println("\nScenario: " + scenario.getName());
            evaluate(scenario.getLightIntensity(), scenario.getRoomTemperature());
        }
    }

    public void runHardcodedTestScenarios() {
        evaluate(950, 35);
        evaluate(500, 22);
        evaluate(50, 5);
        evaluate(800, 28);
        evaluate(200, 15);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("   SMART BLIND CONTROL - CASE STUDY");
        System.out.println("========================================\n");

        System.out.println("Select mode:");
        System.out.println("1. Load from files (config.json + definedRules.json)");
        System.out.println("2. Use manual configuration");
        System.out.print("Enter choice (1 or 2): ");
        int modeChoice = scanner.nextInt();
        boolean loadFromFiles = (modeChoice == 1);

        System.out.println("\n--- Initializing System ---");
        SmartBlindControl controller = new SmartBlindControl(loadFromFiles);

        System.out.println("\n========================================");
        System.out.println("          EVALUATION MODE");
        System.out.println("========================================\n");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Evaluate with custom inputs");
            System.out.println("2. Run test scenarios");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter Light Intensity (0-1000 lux): ");
                double light = scanner.nextDouble();
                System.out.print("Enter Room Temperature (0-40 °C): ");
                double temp = scanner.nextDouble();
                controller.evaluate(light, temp);
            } else if (choice == 2) {
                if (loadFromFiles) {
                    controller.runTestScenariosFromConfig();
                } else {
                    controller.runHardcodedTestScenarios();
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}
