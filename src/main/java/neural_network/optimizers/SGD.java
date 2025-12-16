package neural_network.optimizers;

import neural_network.core.NeuralNetwork;
import neural_network.core.Layer;
import neural_network.core.Neuron;

public class SGD implements Optimizer {

    private final double learningRate;

    public SGD(double learningRate) {
        this.learningRate = learningRate;
    }

    @Override
    public void step(NeuralNetwork network) {
        for (Layer layer : network.getLayers()) {
            for (Neuron neuron : layer.getNeurons()) {
                double[] weights = neuron.getWeights();
                double[] grads = neuron.getWeightGradients();

                for (int i = 0; i < weights.length; i++) {
                    weights[i] -= learningRate * grads[i];
                }
                neuron.setBias(neuron.getBias() - learningRate * neuron.getBiasGradient());
            }
        }
    }
}
