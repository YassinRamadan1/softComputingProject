package fuzzy.rulebase.persistence;

public class RuleConsequentDTO {
    public String variableName;
    public String setName;

    RuleConsequentDTO(String variableName, String setName) {
        this.variableName = variableName;
        this.setName = setName;
    }
}

/*
{
  "variableName": "BlindOpening",
  "setName": "CLOSED"
}
*/