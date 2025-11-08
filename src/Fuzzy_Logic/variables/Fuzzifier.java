package variables;

import java.util.*;

public class Fuzzifier {
    private final Map<String, FuzzyVariable> variables = new LinkedHashMap<>();

    public void addVariable(FuzzyVariable var) {
        variables.put(var.getName(), var);
    }

    public FuzzyVariable getVariable(String name) {
        return variables.get(name);
    }

    // like "Temperature" = 37.0, "Light" = 600.0
    public Map<String, Map<String, Double>> fuzzify(Map<String, Double> crispInputs) {
        Map<String, Map<String, Double>> result = new LinkedHashMap<>();

        for (String varName : crispInputs.keySet()) {
            FuzzyVariable var = variables.get(varName);
            if (var != null) {
                result.put(varName, var.fuzzify(crispInputs.get(varName)));
                /*
                    {
                    "Temperature" = { "Low"=0.1, "Medium"=0.7, "High"=0.2 },
                    "Speed" = { "Slow"=0.0, "Normal"=0.5, "Fast"=0.5 }
                    }
                 */
            } else {
                System.err.println("[Warning] Variable " + varName + " not found in Fuzzifier!");
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "Fuzzifier with variables: " + variables.keySet();
    }
}