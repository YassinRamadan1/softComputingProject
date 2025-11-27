package fuzzy.rulebase.persistence;

public class RuleAntecedentDTO {
    public String variableName;
    public String setName;
    public String operator;

    public RuleAntecedentDTO(String variableName, String setName, String operator) {
        this.variableName = variableName;
        this.setName = setName;
        this.operator = operator;
    }
}

/*
{
  "variableName": "LightIntensity",
  "setName": "BRIGHT",
  "operator": "IS"
}
*/
