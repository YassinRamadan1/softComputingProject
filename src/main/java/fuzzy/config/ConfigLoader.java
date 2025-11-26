package fuzzy.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fuzzy.util.SD;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class ConfigLoader {
    private ConfigLoader() {
    }

    public static FuzzyConfiguration load() {
        String CONFIG_FILE_PATH = SD.CONFIG_PATH;
        return load(CONFIG_FILE_PATH);
    }

    public static FuzzyConfiguration load(String path) {
        try (Reader reader = openResource(path)) {
            if (reader == null) return FuzzyConfiguration.getDefaultConfiguration();

            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            return parseConfiguration(root);
        } catch (IOException exception) {
            return FuzzyConfiguration.getDefaultConfiguration();
        }
    }

    private static Reader openResource(String path) throws IOException {
        var resource = ConfigLoader.class.getResourceAsStream(path);
        if (resource == null) return null;
        return new InputStreamReader(resource, StandardCharsets.UTF_8);
    }

    private static FuzzyConfiguration parseConfiguration(JsonObject root) {
        FuzzyConfiguration config = FuzzyConfiguration.getDefaultConfiguration();
        if (root == null) return config;

        Gson gson = new Gson();
        if (root.has("defaults")) {
            JsonObject defaults = root.getAsJsonObject("defaults");

            config.setInferenceEngineType(
                    FuzzyConfiguration.InferenceEngineType.fromString(
                            getString(defaults, "inferenceEngine", config.getInferenceEngineType().name())
                    )
            );
            config.setAndOperatorType(
                    FuzzyConfiguration.AndOperatorType.fromString(
                            getString(defaults, "andOperator", config.getAndOperatorType().name())
                    )
            );
            config.setOrOperatorType(
                    FuzzyConfiguration.OrOperatorType.fromString(
                            getString(defaults, "orOperator", config.getOrOperatorType().name())
                    )
            );
            config.setImplicationOperatorType(
                    FuzzyConfiguration.ImplicationOperatorType.fromString(
                            getString(defaults, "implicationOperator", config.getImplicationOperatorType().name())
                    )
            );
            config.setAggregationOperatorType(
                    FuzzyConfiguration.AggregationOperatorType.fromString(
                            getString(defaults, "aggregationOperator", config.getAggregationOperatorType().name())
                    )
            );
            config.setDefuzzificationMethodType(
                    FuzzyConfiguration.DefuzzificationMethodType.fromString(
                            getString(defaults, "defuzzificationMethod", config.getDefuzzificationMethodType().name())
                    )
            );
            config.setValidationStrategyType(
                    FuzzyConfiguration.ValidationStrategyType.fromString(
                            getString(defaults, "validationStrategy", config.getValidationStrategyType().name())
                    )
            );
        }
        if (root.has("caseStudy")) {
            JsonObject caseStudy = root.getAsJsonObject("caseStudy");

            config.setCaseStudyName(getString(caseStudy, "name", ""));
            config.setCaseStudyDescription(getString(caseStudy, "description", ""));

            if (caseStudy.has("rulesFile")) {
                config.setRulesFile(caseStudy.get("rulesFile").getAsString());
            }

            if (caseStudy.has("inputs") && caseStudy.get("inputs").isJsonArray()) {
                var inputsArray = caseStudy.getAsJsonArray("inputs");
                FuzzyConfiguration.VariableConfig[] inputs = new FuzzyConfiguration.VariableConfig[inputsArray.size()];

                for (int i = 0; i < inputsArray.size(); i++) {
                    inputs[i] = parseVariableConfig(inputsArray.get(i).getAsJsonObject());
                }
                config.setInputVariables(inputs);
            }

            if (caseStudy.has("output")) {
                config.setOutputVariable(parseVariableConfig(caseStudy.getAsJsonObject("output")));
            }

            if (caseStudy.has("testScenarios") && caseStudy.get("testScenarios").isJsonArray()) {
                var array = caseStudy.getAsJsonArray("testScenarios");
                FuzzyConfiguration.TestScenario[] scenarios = new FuzzyConfiguration.TestScenario[array.size()];

                for (int i = 0; i < array.size(); i++) {
                    JsonObject scenario = array.get(i).getAsJsonObject();
                    String name = getString(scenario, "name", "Scenario " + (i + 1));
                    double light = getDouble(scenario, "lightIntensity", 0);
                    double temp = getDouble(scenario, "roomTemperature", 0);
                    scenarios[i] = new FuzzyConfiguration.TestScenario(name, light, temp);
                }
                config.setTestScenarios(scenarios);
            }
        }

        return config;
    }

    private static FuzzyConfiguration.VariableConfig parseVariableConfig(JsonObject varObj) {
        String name = getString(varObj, "name", "");
        double min = 0, max = 100;

        if (varObj.has("domain")) {
            JsonObject domain = varObj.getAsJsonObject("domain");
            min = getDouble(domain, "min", 0);
            max = getDouble(domain, "max", 100);
        }

        FuzzyConfiguration.FuzzySetConfig[] fuzzySets = new FuzzyConfiguration.FuzzySetConfig[0];
        if (varObj.has("fuzzySets") && varObj.get("fuzzySets").isJsonArray()) {
            var setsArray = varObj.getAsJsonArray("fuzzySets");
            fuzzySets = new FuzzyConfiguration.FuzzySetConfig[setsArray.size()];

            for (int j = 0; j < setsArray.size(); j++) {
                JsonObject setObj = setsArray.get(j).getAsJsonObject();
                String setName = getString(setObj, "name", "Set" + j);
                FuzzyConfiguration.MembershipFunctionType type = FuzzyConfiguration.MembershipFunctionType.fromString(
                        getString(setObj, "type", "TRIANGULAR")
                );

                double[] params = new double[0];
                if (setObj.has("params") && setObj.get("params").isJsonArray()) {
                    var paramsArray = setObj.getAsJsonArray("params");
                    params = new double[paramsArray.size()];
                    for (int k = 0; k < paramsArray.size(); k++) {
                        params[k] = paramsArray.get(k).getAsDouble();
                    }
                }
                fuzzySets[j] = new FuzzyConfiguration.FuzzySetConfig(setName, type, params);
            }
        }

        return new FuzzyConfiguration.VariableConfig(name, min, max, fuzzySets);
    }

    private static String getString(JsonObject obj, String key, String defaultValue) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : defaultValue;
    }

    private static double getDouble(JsonObject obj, String key, double defaultValue) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsDouble() : defaultValue;
    }
}
