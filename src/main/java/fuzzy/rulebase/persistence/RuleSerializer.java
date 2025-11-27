package fuzzy.rulebase.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fuzzy.linguistic.FuzzyVariable;
import fuzzy.rulebase.FuzzyRule;
import fuzzy.rulebase.FuzzyRuleBase;
import fuzzy.rulebase.RuleAntecedent;
import fuzzy.rulebase.RuleConsequent;

import java.util.ArrayList;
import java.util.List;

public class RuleSerializer {
    private final Gson gson;

    public RuleSerializer() {
        gson = new GsonBuilder().setPrettyPrinting().create();
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
                connector,
                antecedents,
                consequentDTO
        );
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
}


/*
result of the code:
[
  {
    "name": "R1",
    "enabled": true,
    "connector": "AND",
    "antecedents": [
      { "variableName": "Light", "setName": "BRIGHT", "operator": "IS" },
      { "variableName": "Temperature", "setName": "HOT", "operator": "IS" }
    ],
    "consequent": { "variableName": "Blind", "setName": "CLOSED" }
  },
  {
    "name": "R2",
    "enabled": true,
    "connector": "AND",
    "antecedents": [
      { "variableName": "Light", "setName": "DARK", "operator": "IS" },
      { "variableName": "Temperature", "setName": "COLD", "operator": "IS" }
    ],
    "consequent": { "variableName": "Blind", "setName": "OPENED" }
  }
]
*/
