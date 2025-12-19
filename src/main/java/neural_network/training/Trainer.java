package neural_network.training;

import neural_network.core.Layer;
import neural_network.core.ModelConfig;
import neural_network.core.NeuralNetwork;
import neural_network.optimizers.Optimizer;

public class Trainer {
    private final NeuralNetwork neuralNetwork;
    private final ModelConfig config;
    private final Metrics metrics;
    private final Optimizer optimizer;

    public Trainer(NeuralNetwork neuralNetwork, ModelConfig config) {
        this.neuralNetwork = neuralNetwork;
        this.config = config;
        this.metrics = new Metrics();
        this.optimizer = config.getOptimizer();
    }

    public void train(double[][] inputs, double[][] targets) {
        if (inputs.length != targets.length) {
            throw new IllegalArgumentException("Inputs and targets must have the same number of samples");
        }

        int numSamples = inputs.length;
        int batchSize = config.getBatchSize();
        int epochs = config.getEpochs();
        int[] indices = new int[numSamples];
        for (int i = 0; i < numSamples; i++) {
            indices[i] = i;
        }
        for (int epoch = 0; epoch < epochs; epoch++) {
            if (config.isShuffleEnabled()) {
                shuffleArray(indices);
            }

            double epochLoss = 0.0;
            int batchCount = 0;
            for (int start = 0; start < numSamples; start += batchSize) {
                int end = Math.min(start + batchSize, numSamples);

                for (Layer layer : neuralNetwork.getLayers()) {
                    layer.zeroGradients();
                }
                
                double batchLoss = 0.0;

                for (int i = start; i < end; i++) {
                    int idx = indices[i];
                    double[] predictions = neuralNetwork.forward(inputs[idx]);
                    double loss = config.getLoss().computeLoss(targets[idx], predictions);
                    double[] lossGrad = config.getLoss().computeGradient(targets[idx], predictions);
                    neuralNetwork.backward(lossGrad);
                    batchLoss += loss;
                }
                
                optimizer.step(neuralNetwork);
                
                batchLoss /= (end - start);
                epochLoss += batchLoss;
                batchCount++;
            }
            epochLoss /= batchCount;
            metrics.addLoss(epochLoss);

            // calculating and saving accuracy for the graphs
            int correct = 0;
            for (int i = 0; i < numSamples; i++) {
                double[] prediction = neuralNetwork.forward(inputs[i]);
                int predicted = prediction[0] >= 0.5 ? 1 : 0;
                int actual = targets[i][0] >= 0.5 ? 1 : 0;
                if (predicted == actual) correct++;
            }
            double epochAccuracy = (double) correct / numSamples;

            metrics.addAccuracy(epochAccuracy);
            System.out.println("Epoch " + (epoch + 1) + "/" + epochs + " - Loss: " + epochLoss);
        }
        metrics.saveToCSV("training_metrics.csv");

    }

    private void shuffleArray(int[] indices) {
        for (int i = indices.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = indices[i];
            indices[i] = indices[j];
            indices[j] = temp;
        }
    }
}
