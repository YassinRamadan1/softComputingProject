package neural_network.core;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private final List<Layer> layers = new ArrayList<>();
    private final ModelConfig config;

    public NeuralNetwork(ModelConfig config) {
        this.config = config;
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public double[] forward(double[] inputs) {
        double[] outputs = inputs;
        for (Layer layer : layers) {
            outputs = layer.forward(outputs);
        }
        return outputs;
    }

    public void backward(double[] dLoss_dOutput) {
        for (int i = layers.size() - 1; i >= 0; i--) {
            dLoss_dOutput = layers.get(i).backward(dLoss_dOutput);
        }
    }

    public double[] predict(double[] inputs) {
        return forward(inputs);
    }

    public double[][] predict(double[][] inputsBatch) {
        double[][] predictions = new double[inputsBatch.length][];
        for (int i = 0; i < inputsBatch.length; i++) {
            predictions[i] = predict(inputsBatch[i]);
        }
        return predictions;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public int getInputSize() {
        if (layers.isEmpty()) {
            throw new IllegalStateException("Network has no layers");
        }
        return layers.get(0).getInputSize();
    }
}
