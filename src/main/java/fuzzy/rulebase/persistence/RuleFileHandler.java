package fuzzy.rulebase.persistence;

import fuzzy.linguistic.FuzzyVariable;
import fuzzy.rulebase.FuzzyRuleBase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class RuleFileHandler {
    private final RuleSerializer serializer;
    private final RuleDeserializer deserializer;

    public RuleFileHandler() {
        this.serializer = new RuleSerializer();
        this.deserializer = new RuleDeserializer();
    }

    public void save(Path path, FuzzyRuleBase ruleBase) throws IOException {
        String json = serializer.serialize(ruleBase);
        Files.createDirectories(path.getParent());
        Files.writeString(path, json, StandardCharsets.UTF_8);
    }

    public FuzzyRuleBase load(Path path, Map<String, FuzzyVariable> variableMap) throws IOException {
        String json = Files.readString(path, StandardCharsets.UTF_8);
        return deserializer.deserialize(json, variableMap);
    }
}
