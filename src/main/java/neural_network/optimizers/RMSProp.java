package neural_network.optimizers;

import neural_network.core.Layer;
import neural_network.core.NeuralNetwork;
import neural_network.core.Neuron;

import java.util.HashMap;
import java.util.Map;

public class RMSProp implements Optimizer {

    private final double learningRate;
    private final double decayFactor;
    private static final double EPS = 1e-8;

    // Per-neuron accumulators
    private final Map<Neuron, double[]> squaredGradients = new HashMap<>();
    private final Map<Neuron, Double> biasSquaredGradients = new HashMap<>();

    public RMSProp(double learningRate, double decayFactor) {
        this.learningRate = learningRate;
        this.decayFactor = decayFactor;
    }

    @Override
    public void step(NeuralNetwork network) {
        for (Layer layer : network.getLayers()) {
            for (Neuron neuron : layer.getNeurons()) {
                double[] weights = neuron.getWeights();
                double[] grads = neuron.getWeightGradients();
                double[] avgSqGrad = squaredGradients.computeIfAbsent(neuron, n -> new double[weights.length]);

                for (int i = 0; i < weights.length; i++) {
                    avgSqGrad[i] = decayFactor * avgSqGrad[i] + (1 - decayFactor) * grads[i] * grads[i];
                    weights[i] -= learningRate * grads[i] / (Math.sqrt(avgSqGrad[i]) + EPS);
                }

                double biasGrad = neuron.getBiasGradient();
                double biasAvgSqGrad = biasSquaredGradients.getOrDefault(neuron, 0.0);
                biasAvgSqGrad = decayFactor * biasAvgSqGrad + (1 - decayFactor) * biasGrad * biasGrad;
                neuron.setBias(neuron.getBias() - learningRate * biasGrad / (Math.sqrt(biasAvgSqGrad) + EPS));

                biasSquaredGradients.put(neuron, biasAvgSqGrad);
            }
        }
    }
}
