package fuzzy.rules.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fuzzy.rules.FuzzyRuleBase;
import fuzzy.rules.RuleAntecedent;
import fuzzy.rules.RuleBuilder;
import fuzzy.rules.RuleConnector;
import fuzzy.variables.FuzzyVariable;

import java.util.Map;

public class RuleDeserializer {
    private  final Gson gson;
    public RuleDeserializer() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public FuzzyRuleBase deserialize(String json, Map<String, FuzzyVariable> variableMap) {
        RuleDTO[] dtos = gson.fromJson(json, RuleDTO[].class);
        FuzzyRuleBase ruleBase = new FuzzyRuleBase();

        if(dtos==null) return ruleBase;

        for (RuleDTO dto : dtos) {
            RuleBuilder builder = RuleBuilder.named(dto.name);
            RuleConnector connector = RuleConnector.valueOf(dto.connector);

            boolean first = true;
            if (dto.antecedents != null) {
                for (RuleAntecedentDTO ruleAntecedentDTO : dto.antecedents) {
                    FuzzyVariable variable = variableMap.get(ruleAntecedentDTO.variableName);
                    if(variable==null) throw new IllegalStateException("Variable not found: " + ruleAntecedentDTO.variableName);

                    RuleAntecedent.Operator op = RuleAntecedent.Operator.valueOf(ruleAntecedentDTO.operator);
                    if (first) {
                        if (op == RuleAntecedent.Operator.IS) {
                            builder.when(variable, ruleAntecedentDTO.setName);
                        } else {
                            builder.whenNot(variable, ruleAntecedentDTO.setName);
                        }
                        first = false;
                    } else {
                        if (connector == RuleConnector.AND) {
                            builder.and(variable, ruleAntecedentDTO.setName);
                        } else {
                            builder.or(variable, ruleAntecedentDTO.setName);
                        }
                    }
                }
            }

            if (dto.consequent==null) throw new IllegalStateException("Consequent not found");

            FuzzyVariable outputVariable = variableMap.get(dto.consequent.variableName);
            if(outputVariable==null) throw new IllegalStateException("Variable not found: " + dto.consequent.variableName);

            builder.then(outputVariable, dto.consequent.setName);
            builder.enabled(dto.enabled);
            builder.weight(dto.weight);
            ruleBase.addRule(builder.build());
        }
        return ruleBase;
    }
}
