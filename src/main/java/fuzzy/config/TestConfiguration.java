package fuzzy.config;

public class TestConfiguration {

    public static void main(String[] args) {
        FuzzyConfiguration config = ConfigLoader.load();

        System.out.println("=== Loaded Fuzzy Configuration ===");

        System.out.println("Inference engine      : " + config.getInferenceEngineType());
        System.out.println("AND operator          : " + config.getAndOperatorType());
        System.out.println("OR operator           : " + config.getOrOperatorType());
        System.out.println("Implication operator  : " + config.getImplicationOperatorType());
        System.out.println("Aggregation operator  : " + config.getAggregationOperatorType());
        System.out.println("Defuzzification method: " + config.getDefuzzificationMethodType());
        System.out.println("Validation strategy   : " + config.getValidationStrategyType());

        System.out.println();
        System.out.println("Default input domain  : [" +
                config.getDefaultInputMin() + ", " + config.getDefaultInputMax() + "]");
        System.out.println("Default output domain : [" +
                config.getDefaultOutputMin() + ", " + config.getDefaultOutputMax() + "]");
        System.out.println("Default # of sets     : " + config.getDefaultNumberOfSets());
        System.out.println("Default MF type       : " + config.getDefaultMembershipFunctionType());

        System.out.println();
        System.out.println("Window blind rule file: " + config.getWindowBlindRuleFile());

        String[] inputs = config.getWindowBlindInputVariables();
        System.out.print("Window blind inputs   : ");
        if (inputs != null) {
            for (int i = 0; i < inputs.length; i++) {
                System.out.print(inputs[i]);
                if (i < inputs.length - 1) System.out.print(", ");
            }
        } else {
            System.out.print("(none)");
        }
        System.out.println();

        System.out.println("Window blind output   : " + config.getWindowBlindOutputVariable());
    }
}