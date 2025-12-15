package neural_network.case_study;

import com.google.gson.Gson;
import java.io.FileReader;

public class ConfigLoader {

    public static CaseStudyConfig load(String path) {
        try (FileReader reader = new FileReader(path)) {
            return new Gson().fromJson(reader, CaseStudyConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config file: " + path, e);
        }
    }
}
