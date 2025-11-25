package fuzzy.inference;

import fuzzy.operators.snorm.SNorm;
import fuzzy.operators.tnorm.TNorm;
import fuzzy.rules.FuzzyRule;
import fuzzy.rules.RuleAntecedent;
import fuzzy.rules.RuleConnector;
import java.util.Map;

public class AntecedentEvaluator {
    private final TNorm andOperator;
    private final SNorm orOperator;

    public AntecedentEvaluator(TNorm andOperator, SNorm orOperator) {
        this.andOperator = andOperator;
        this.orOperator = orOperator;
    }

    public double evaluate(FuzzyRule rule, Map<String, Map<String, Double>> fuzzifiedInputs) {
        RuleConnector connector = rule.getConnector();
        double antecedentStrength = (connector == RuleConnector.AND) ? 1.0 : 0.0;

        for (RuleAntecedent antecedent : rule.getAntecedents()) {
            String varName = antecedent.getVariable().getName();
            String setName = antecedent.getSetName();

            Map<String, Double> fuzzifiedValues = fuzzifiedInputs.get(varName);
            if(fuzzifiedValues == null) {
                throw new IllegalStateException("Fuzzified values not found for variable: " + varName);
            }

            Double membership = fuzzifiedValues.get(setName);
            if(membership==null) membership=0.0;

            if(antecedent.getOperator()==RuleAntecedent.Operator.IS_NOT) {
                membership=1.0-membership;
            }

            if(connector==RuleConnector.AND) {
                antecedentStrength = andOperator.apply(antecedentStrength, membership);
            } else {
                antecedentStrength = orOperator.apply(antecedentStrength, membership);
            }
        }
        return antecedentStrength;
    }
}
