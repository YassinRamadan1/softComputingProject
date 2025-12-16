package neural_network.optimizers;

import neural_network.core.Layer;
import neural_network.core.NeuralNetwork;
import neural_network.core.Neuron;

import java.util.HashMap;
import java.util.Map;

public class SGDWithMomentum implements Optimizer {

    private final double learningRate;
    private final double momentum;

    private final Map<Neuron, double[]> velocityMap = new HashMap<>();

    public SGDWithMomentum(double learningRate, double momentum) {
        this.learningRate = learningRate;
        this.momentum = momentum;
    }

    @Override
    public void step(NeuralNetwork network) {
        for (Layer layer : network.getLayers()) {
            for (Neuron neuron : layer.getNeurons()) {
                double[] velocity = velocityMap.computeIfAbsent(neuron, n -> new double[n.getWeights().length]);
                double[] weights = neuron.getWeights();
                double[] grads = neuron.getWeightGradients();

                for (int i = 0; i < weights.length; i++) {
                    velocity[i] = momentum * velocity[i] - learningRate * grads[i];
                    weights[i] += velocity[i];
                }
                neuron.setBias(neuron.getBias() - learningRate * neuron.getBiasGradient());
            }
        }
    }
}
