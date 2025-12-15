package neural_network.case_study;

import java.util.List;

public class CaseStudyConfig {

    public DatasetConfig dataset;
    public ModelConfig model;
    public List<LayerConfig> layers;

    public static class DatasetConfig {
        public String path;
        public boolean hasHeader;
        public double trainRatio;
        public boolean shuffle;
    }

    public static class ModelConfig {
        public double learningRate;
        public int epochs;
        public int batchSize;
        public double threshold;
    }

    public static class LayerConfig {
        public int neurons;
        public String activation;
    }
}
