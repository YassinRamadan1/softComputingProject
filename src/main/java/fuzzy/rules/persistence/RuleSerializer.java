package fuzzy.rules.persistence;

import fuzzy.rules.FuzzyRule;
import fuzzy.rules.FuzzyRuleBase;
import fuzzy.rules.RuleAntecedent;
import fuzzy.rules.RuleConsequent;
import fuzzy.variables.FuzzyVariable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

public class RuleSerializer {
    private final Gson gson;

    public RuleSerializer() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public String serialize(FuzzyRuleBase ruleBase) {
        List<RuleDTO> dtoList = new ArrayList<>();

        for (FuzzyRule rule : ruleBase.getRules()) {
            List<RuleAntecedentDTO> antecedents = new ArrayList<>();
            for (RuleAntecedent antecedent : rule.getAntecedents()) {
                FuzzyVariable var = antecedent.getVariable();
                antecedents.add(new RuleAntecedentDTO(
                        var.getName(),
                        antecedent.getSetName(),
                        antecedent.getOperator().toString()
                ));
            }

            RuleDTO dto = getRuleDTO(rule, antecedents);
            dtoList.add(dto);
        }
        return gson.toJson(dtoList);
    }

    private static RuleDTO getRuleDTO(FuzzyRule rule, List<RuleAntecedentDTO> antecedents) {
        RuleConsequent consequent = rule.getConsequent();
        RuleConsequentDTO consequentDTO = new RuleConsequentDTO(
                consequent.getVariable().getName(),
                consequent.getSetName()
        );

        String connector = rule.getConnector().name();

        return new RuleDTO(rule.getName(),
                rule.isEnabled(),
                rule.getWeight(),
                connector,
                antecedents,
                consequentDTO
        );
    }
}
