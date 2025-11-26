package fuzzy.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fuzzy.util.StaticData;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class ConfigLoader {
    private ConfigLoader() {
    }

    public static FuzzyConfiguration load() {
        String CONFIG_FILE_PATH = StaticData.CONFIG_PATH;
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

        if (root.has(StaticData.DEFAULTS)) {
            JsonObject defaults = root.getAsJsonObject(StaticData.DEFAULTS);

            config.setInferenceEngineType(
                    FuzzyConfiguration.InferenceEngineType.fromString(
                            getString(defaults, StaticData.INFERENCE_ENGINE, config.getInferenceEngineType().name())
                    )
            );
            config.setAndOperatorType(
                    FuzzyConfiguration.AndOperatorType.fromString(
                            getString(defaults, StaticData.AND_OPERATOR, config.getAndOperatorType().name())
                    )
            );
            config.setOrOperatorType(
                    FuzzyConfiguration.OrOperatorType.fromString(
                            getString(defaults, StaticData.OR_OPERATOR, config.getOrOperatorType().name())
                    )
            );
            config.setImplicationOperatorType(
                    FuzzyConfiguration.ImplicationOperatorType.fromString(
                            getString(defaults, StaticData.IMPLICATION_OPERATOR, config.getImplicationOperatorType().name())
                    )
            );
            config.setAggregationOperatorType(
                    FuzzyConfiguration.AggregationOperatorType.fromString(
                            getString(defaults, StaticData.AGGREGATION_OPERATOR, config.getAggregationOperatorType().name())
                    )
            );
            config.setDefuzzificationMethodType(
                    FuzzyConfiguration.DefuzzificationMethodType.fromString(
                            getString(defaults, StaticData.DEFUZZIFICATION_METHOD, config.getDefuzzificationMethodType().name())
                    )
            );
            config.setValidationStrategyType(
                    FuzzyConfiguration.ValidationStrategyType.fromString(
                            getString(defaults, StaticData.VALIDATION_STRATEGY, config.getValidationStrategyType().name())
                    )
            );
        }
        if (root.has(StaticData.CASE_STUDY)) {
            JsonObject caseStudy = root.getAsJsonObject(StaticData.CASE_STUDY);

            config.setCaseStudyName(getString(caseStudy, StaticData.NAME, ""));
            config.setCaseStudyDescription(getString(caseStudy, StaticData.DESCRIPTION, ""));

            if (caseStudy.has(StaticData.RULES_FILE)) {
                config.setRulesFile(caseStudy.get(StaticData.RULES_FILE).getAsString());
            }

            if (caseStudy.has(StaticData.INPUTS) && caseStudy.get(StaticData.INPUTS).isJsonArray()) {
                var inputsArray = caseStudy.getAsJsonArray(StaticData.INPUTS);
                FuzzyConfiguration.VariableConfig[] inputs = new FuzzyConfiguration.VariableConfig[inputsArray.size()];

                for (int i = 0; i < inputsArray.size(); i++) {
                    inputs[i] = parseVariableConfig(inputsArray.get(i).getAsJsonObject());
                }
                config.setInputVariables(inputs);
            }

            if (caseStudy.has(StaticData.OUTPUT) && caseStudy.get(StaticData.OUTPUT).isJsonObject()) {
                config.setOutputVariable(parseVariableConfig(caseStudy.getAsJsonObject(StaticData.OUTPUT)));
            }

            if (caseStudy.has(StaticData.TEST_SCENARIOS) && caseStudy.get(StaticData.TEST_SCENARIOS).isJsonArray()) {
                var array = caseStudy.getAsJsonArray(StaticData.TEST_SCENARIOS);
                FuzzyConfiguration.TestScenario[] scenarios = new FuzzyConfiguration.TestScenario[array.size()];

                for (int i = 0; i < array.size(); i++) {
                    JsonObject scenario = array.get(i).getAsJsonObject();
                    String name = getString(scenario, StaticData.NAME, StaticData.SCENARIO + (i + 1));
                    double light = getDouble(scenario, StaticData.LIGHT_INTENSITY_SMALL_L, 0);
                    double temp = getDouble(scenario, StaticData.ROOM_TEMPERATURE_SMALL_R, 0);
                    scenarios[i] = new FuzzyConfiguration.TestScenario(name, light, temp);
                }
                config.setTestScenarios(scenarios);
            }
        }

        return config;
    }

    private static FuzzyConfiguration.VariableConfig parseVariableConfig(JsonObject varObj) {
        String name = getString(varObj, StaticData.NAME, "");
        double min = 0, max = 100;

        if (varObj.has(StaticData.DOMAIN)) {
            JsonObject domain = varObj.getAsJsonObject(StaticData.DOMAIN);
            min = getDouble(domain, StaticData.MIN, 0);
            max = getDouble(domain, StaticData.MAX, 100);
        }

        FuzzyConfiguration.FuzzySetConfig[] fuzzySets = new FuzzyConfiguration.FuzzySetConfig[0];
        if (varObj.has(StaticData.FUZZY_SETS) && varObj.get(StaticData.FUZZY_SETS).isJsonArray()) {
            var setsArray = varObj.getAsJsonArray(StaticData.FUZZY_SETS);
            fuzzySets = new FuzzyConfiguration.FuzzySetConfig[setsArray.size()];

            for (int j = 0; j < setsArray.size(); j++) {
                JsonObject setObj = setsArray.get(j).getAsJsonObject();
                String setName = getString(setObj, StaticData.NAME, StaticData.SET + j);
                FuzzyConfiguration.MembershipFunctionType type = FuzzyConfiguration.MembershipFunctionType.fromString(
                        getString(setObj, StaticData.TYPE, StaticData.TRIANGULAR)
                );

                double[] params = new double[0];
                if (setObj.has(StaticData.PARAMS) && setObj.get(StaticData.PARAMS).isJsonArray()) {
                    var paramsArray = setObj.getAsJsonArray(StaticData.PARAMS);
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
