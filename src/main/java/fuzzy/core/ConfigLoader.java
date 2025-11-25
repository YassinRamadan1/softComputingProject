package fuzzy.core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fuzzy.utilities.SD;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class ConfigLoader {
    private ConfigLoader(){}

    public static FuzzyConfiguration load() {
        String CONFIG_FILE_PATH = SD.CONFIG_PATH;
        return load(CONFIG_FILE_PATH);
    }

    public static FuzzyConfiguration load(String path) {
        try (Reader reader = openResource(path)) {
            if(reader==null)  return FuzzyConfiguration.getDefaultConfiguration();

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
        if(root==null)  return config;

        Gson gson = new Gson();
        if(root.has("defaults")) {
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

        if(root.has("variables")) {
            JsonObject variables = root.getAsJsonObject("variables");

            if(variables.has("defaultInputDomain")) {
                JsonObject defaultInputDomain = variables.getAsJsonObject("defaultInputDomain");
                config.setDefaultInputMin(getDouble(defaultInputDomain, "min", config.getDefaultInputMin()));
                config.setDefaultInputMax(getDouble(defaultInputDomain, "max", config.getDefaultInputMax()));
            }

            if(variables.has("defaultOutputDomain")) {
                JsonObject defaultOutputDomain = variables.getAsJsonObject("defaultOutputDomain");
                config.setDefaultOutputMin(getDouble(defaultOutputDomain, "min", config.getDefaultOutputMin()));
                config.setDefaultOutputMax(getDouble(defaultOutputDomain, "max", config.getDefaultOutputMax()));
            }

            config.setDefaultNumberOfSets(getInt(variables, config.getDefaultNumberOfSets()));

            config.setDefaultMembershipFunctionType(
                    FuzzyConfiguration.MembershipFunctionType.fromString(
                            getString(variables, "defaultMembershipFunctionType",
                                    config.getDefaultMembershipFunctionType().name())
                    )
            );

            config.setAutoGenerateSets(
                    getBoolean(variables, "autoGenerateSets", config.isAutoGenerateSets())
            );
        }

        if (root.has("caseStudy")) {
            JsonObject caseStudy = root.getAsJsonObject("caseStudy");
            if(caseStudy.has("windowBlind")) {
                JsonObject wb = caseStudy.getAsJsonObject("windowBlind");

                config.setWindowBlindEnabled(
                        getBoolean(wb, "enabled", config.isWindowBlindEnabled())
                );
                config.setWindowBlindRuleFile(
                        getString(wb, "ruleFile", config.getWindowBlindRuleFile())
                );

                if(wb.has("inputVariables") && wb.get("inputVariables").isJsonArray()) {
                    var array = wb.getAsJsonArray("inputVariables");
                    String[] inputVariables = new String[array.size()];

                    for (int i=0; i<array.size(); ++i) inputVariables[i] = array.get(i).getAsString();
                    config.setWindowBlindInputVariables(inputVariables);
                }

                if(wb.has("outputVariable")) config.setWindowBlindOutputVariable(wb.get("outputVariable").getAsString());
            }
        }
        return config;
    }

    private static String getString(JsonObject obj, String key, String defaultValue) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : defaultValue;
    }

    private static boolean getBoolean(JsonObject obj, String key, boolean defaultValue) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsBoolean() : defaultValue;
    }

    private static double getDouble(JsonObject obj, String key, double defaultValue) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsDouble() : defaultValue;
    }

    private static int getInt(JsonObject obj, int defaultValue) {
        return obj.has("defaultNumberOfSets") && !obj.get("defaultNumberOfSets").isJsonNull() ? obj.get("defaultNumberOfSets").getAsInt() : defaultValue;
    }
}
