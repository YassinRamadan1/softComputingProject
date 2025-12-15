package neural_network.core;

import java.util.ArrayList;

public class NeuralNetwork {
    private final ArrayList<Layer> layers = new ArrayList<>();
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

    public double backward(double[] predicted, double[] actual) {
        double lossValue = config.getLoss().computeLoss(actual, predicted);
        double[] dLoss_dOutput = config.getLoss().computeGradient(actual, predicted);

        for (int i = layers.size() - 1; i >= 0; i--) {
            dLoss_dOutput = layers.get(i).backward(dLoss_dOutput);
        }

        return lossValue;
    }

    public double train(double[] inputs, double[] targets) {
        double[] predictions = forward(inputs);
        return backward(predictions, targets);
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

    public ArrayList<Layer> getLayers() {
        return layers;
    }
}