package fuzzy.config;

import fuzzy.util.SD;

public class FuzzyConfiguration {
    public enum InferenceEngineType {
        MAMDANI, SUGENO_ZERO, SUGENO_FIRST;

        public static InferenceEngineType fromString(String engine) {
            try {
                return InferenceEngineType.valueOf(engine.toUpperCase());
            } catch (IllegalArgumentException e) {
                return InferenceEngineType.MAMDANI;
            }
        }
    }
    public enum AndOperatorType{
        MIN, ALGEBRAIC_PRODUCT;

        public static AndOperatorType fromString(String type) {
            try {
                return AndOperatorType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                return MIN;
            }
        }
    }
    public enum OrOperatorType{
        MAX, ALGEBRAIC_SUM;

        public static OrOperatorType fromString(String type) {
            try {
                return OrOperatorType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                return MAX;
            }
        }
    }
    public enum ImplicationOperatorType{
        MIN, PRODUCT;

        public static ImplicationOperatorType fromString(String type) {
            try {
                return ImplicationOperatorType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                return MIN;
            }
        }
    }
    public enum AggregationOperatorType{
        MAX, SUM;

        public static AggregationOperatorType fromString(String type) {
            try {
                return AggregationOperatorType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                return MAX;
            }
        }
    }
    public enum DefuzzificationMethodType {
        MEAN_OF_MAX, WEIGHTED_AVERAGE;

        public static DefuzzificationMethodType fromString(String method) {
            try {
                return DefuzzificationMethodType.valueOf(method.toUpperCase());
            } catch (IllegalArgumentException e) {
                return MEAN_OF_MAX;
            }
        }
    }
    public enum ValidationStrategyType {
        CLAMP, STRICT;

        public static ValidationStrategyType fromString(String strategy) {
            try {
                return ValidationStrategyType.valueOf(strategy.toUpperCase());
            } catch (IllegalArgumentException e) {
                return CLAMP;
            }
        }
    }
    public enum MembershipFunctionType {
        TRIANGULAR, TRAPEZOIDAL;

        public static MembershipFunctionType fromString(String type) {
            try {
                return MembershipFunctionType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                return TRIANGULAR;
            }
        }
    }


    private InferenceEngineType inferenceEngineType;
    private AndOperatorType andOperatorType;
    private OrOperatorType orOperatorType;
    private ImplicationOperatorType implicationOperatorType;
    private AggregationOperatorType aggregationOperatorType;
    private DefuzzificationMethodType defuzzificationMethodType;
    private ValidationStrategyType validationStrategyType;
    private MembershipFunctionType defaultMembershipFunctionType;

    private double defaultInputMin;
    private double defaultInputMax;
    private double defaultOutputMin;
    private double defaultOutputMax;
    private int defaultNumberOfSets;

    private boolean windowBlindEnabled;
    private String windowBlindRuleFile;
    private String[] windowBlindInputVariables;
    private String windowBlindOutputVariable;

    public static FuzzyConfiguration getDefaultConfiguration() {
        FuzzyConfiguration config = new FuzzyConfiguration();

        config.inferenceEngineType = InferenceEngineType.MAMDANI;
        config.andOperatorType = AndOperatorType.MIN;
        config.orOperatorType = OrOperatorType.MAX;
        config.implicationOperatorType = ImplicationOperatorType.MIN;
        config.aggregationOperatorType = AggregationOperatorType.MAX;
        config.defuzzificationMethodType = DefuzzificationMethodType.MEAN_OF_MAX;
        config.validationStrategyType = ValidationStrategyType.CLAMP;
        config.defaultMembershipFunctionType = MembershipFunctionType.TRIANGULAR;

        config.defaultInputMin = 0.0;
        config.defaultInputMax = 100.0;
        config.defaultOutputMin = 0.0;
        config.defaultOutputMax = 100.0;
        config.defaultNumberOfSets = 3;

        config.windowBlindEnabled = false;
        config.windowBlindRuleFile = SD.DEFINED_RULES_PATH;
        config.windowBlindInputVariables = new String[] {SD.LIGHT, SD.TEMPERATURE};
        config.windowBlindOutputVariable = SD.BLIND_POSITION;

        return config;
    }

    public InferenceEngineType getInferenceEngineType() {
        return inferenceEngineType;
    }

    public void setInferenceEngineType(InferenceEngineType inferenceEngineType) {
        this.inferenceEngineType = inferenceEngineType;
    }

    public AndOperatorType getAndOperatorType() {
        return andOperatorType;
    }

    public void setAndOperatorType(AndOperatorType andOperatorType) {
        this.andOperatorType = andOperatorType;
    }

    public OrOperatorType getOrOperatorType() {
        return orOperatorType;
    }

    public void setOrOperatorType(OrOperatorType orOperatorType) {
        this.orOperatorType = orOperatorType;
    }

    public ImplicationOperatorType getImplicationOperatorType() {
        return implicationOperatorType;
    }

    public void setImplicationOperatorType(ImplicationOperatorType implicationOperatorType) {
        this.implicationOperatorType = implicationOperatorType;
    }

    public AggregationOperatorType getAggregationOperatorType() {
        return aggregationOperatorType;
    }

    public void setAggregationOperatorType(AggregationOperatorType aggregationOperatorType) {
        this.aggregationOperatorType = aggregationOperatorType;
    }

    public DefuzzificationMethodType getDefuzzificationMethodType() {
        return defuzzificationMethodType;
    }

    public void setDefuzzificationMethodType(DefuzzificationMethodType defuzzificationMethodType) {
        this.defuzzificationMethodType = defuzzificationMethodType;
    }

    public ValidationStrategyType getValidationStrategyType() {
        return validationStrategyType;
    }

    public void setValidationStrategyType(ValidationStrategyType validationStrategyType) {
        this.validationStrategyType = validationStrategyType;
    }

    public double getDefaultInputMin() {
        return defaultInputMin;
    }

    public void setDefaultInputMin(double defaultInputMin) {
        this.defaultInputMin = defaultInputMin;
    }

    public double getDefaultInputMax() {
        return defaultInputMax;
    }

    public void setDefaultInputMax(double defaultInputMax) {
        this.defaultInputMax = defaultInputMax;
    }

    public double getDefaultOutputMin() {
        return defaultOutputMin;
    }

    public void setDefaultOutputMin(double defaultOutputMin) {
        this.defaultOutputMin = defaultOutputMin;
    }

    public double getDefaultOutputMax() {
        return defaultOutputMax;
    }

    public void setDefaultOutputMax(double defaultOutputMax) {
        this.defaultOutputMax = defaultOutputMax;
    }

    public int getDefaultNumberOfSets() {
        return defaultNumberOfSets;
    }

    public void setDefaultNumberOfSets(int defaultNumberOfSets) {
        this.defaultNumberOfSets = defaultNumberOfSets;
    }

    public MembershipFunctionType getDefaultMembershipFunctionType() {
        return defaultMembershipFunctionType;
    }

    public void setDefaultMembershipFunctionType(MembershipFunctionType defaultMembershipFunctionType) {
        this.defaultMembershipFunctionType = defaultMembershipFunctionType;
    }

    public boolean isWindowBlindEnabled() {
        return windowBlindEnabled;
    }

    public void setWindowBlindEnabled(boolean windowBlindEnabled) {
        this.windowBlindEnabled = windowBlindEnabled;
    }

    public String getWindowBlindRuleFile() {
        return windowBlindRuleFile;
    }

    public void setWindowBlindRuleFile(String windowBlindRuleFile) {
        this.windowBlindRuleFile = windowBlindRuleFile;
    }

    public String[] getWindowBlindInputVariables() {
        return windowBlindInputVariables;
    }

    public void setWindowBlindInputVariables(String[] windowBlindInputVariables) {
        this.windowBlindInputVariables = windowBlindInputVariables;
    }

    public String getWindowBlindOutputVariable() {
        return windowBlindOutputVariable;
    }

    public void setWindowBlindOutputVariable(String windowBlindOutputVariable) {
        this.windowBlindOutputVariable = windowBlindOutputVariable;
    }

    public static class FuzzySetConfig {
        private String name;
        private MembershipFunctionType type;
        private double[] params;

        public FuzzySetConfig(String name, MembershipFunctionType type, double[] params) {
            this.name = name;
            this.type = type;
            this.params = params;
        }

        public String getName() { return name; }
        public MembershipFunctionType getType() { return type; }
        public double[] getParams() { return params; }
    }

    public static class VariableConfig {
        private String name;
        private double min;
        private double max;
        private FuzzySetConfig[] fuzzySets;

        public VariableConfig(String name, double min, double max, FuzzySetConfig[] fuzzySets) {
            this.name = name;
            this.min = min;
            this.max = max;
            this.fuzzySets = fuzzySets;
        }

        public String getName() { return name; }
        public double getMin() { return min; }
        public double getMax() { return max; }
        public FuzzySetConfig[] getFuzzySets() { return fuzzySets; }
    }

    public static class TestScenario {
        private String name;
        private double lightIntensity;
        private double roomTemperature;

        public TestScenario(String name, double lightIntensity, double roomTemperature) {
            this.name = name;
            this.lightIntensity = lightIntensity;
            this.roomTemperature = roomTemperature;
        }

        public String getName() { return name; }
        public double getLightIntensity() { return lightIntensity; }
        public double getRoomTemperature() { return roomTemperature; }
    }

    private String caseStudyName;
    private String caseStudyDescription;
    private String rulesFile;
    private VariableConfig[] inputVariables = new VariableConfig[0];
    private VariableConfig outputVariable;
    private TestScenario[] testScenarios = new TestScenario[0];

    public String getCaseStudyName() { return caseStudyName; }
    public void setCaseStudyName(String caseStudyName) { this.caseStudyName = caseStudyName; }

    public String getCaseStudyDescription() { return caseStudyDescription; }
    public void setCaseStudyDescription(String caseStudyDescription) { this.caseStudyDescription = caseStudyDescription; }

    public String getRulesFile() { return rulesFile; }
    public void setRulesFile(String rulesFile) { this.rulesFile = rulesFile; }

    public VariableConfig[] getInputVariables() { return inputVariables; }
    public void setInputVariables(VariableConfig[] inputVariables) { this.inputVariables = inputVariables; }

    public VariableConfig getOutputVariable() { return outputVariable; }
    public void setOutputVariable(VariableConfig outputVariable) { this.outputVariable = outputVariable; }

    public TestScenario[] getTestScenarios() { return testScenarios; }
    public void setTestScenarios(TestScenario[] testScenarios) { this.testScenarios = testScenarios; }
}
