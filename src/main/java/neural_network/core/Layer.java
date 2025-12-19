package neural_network.core;

import neural_network.activations.Activation;
import neural_network.initializers.Initializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Layer {

    private final ArrayList<Neuron> neurons;
    private final Activation activation;

    public Layer(int inputSize, int neuronCount, Activation activation, Initializer initializer) {
        if (inputSize <= 0 || neuronCount <= 0) {
            throw new IllegalArgumentException("Input size and neuron count must be > 0");
        }

        this.activation = activation;
        this.neurons = new ArrayList<>(neuronCount);

        for (int i = 0; i < neuronCount; i++) {
            double[] weights = initializer.initializeWeights(inputSize, neuronCount);
            double bias = initializer.initializeBias();
            neurons.add(new Neuron(weights, bias, activation));
        }
    }

    public double[] forward(double[] inputs) {
        double[] outputs = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            outputs[i] = neurons.get(i).forward(inputs);
        }
        return outputs;
    }

    public double[] backward(double[] dLoss_dOutputs) {
        if (dLoss_dOutputs.length != neurons.size()) {
            throw new IllegalArgumentException("Gradient size does not match number of neurons");
        }

        int inputSize = getInputSize();
        double[] dLoss_dInputs = new double[inputSize];

        for (int i = 0; i < neurons.size(); i++) {
            double[] neuronGrad = neurons.get(i).backward(dLoss_dOutputs[i]);
            for (int j = 0; j < inputSize; j++) {
                dLoss_dInputs[j] += neuronGrad[j];
            }
        }
        return dLoss_dInputs;
    }

    public List<Neuron> getNeurons() {
        return Collections.unmodifiableList(neurons);
    }

    public Activation getActivation() {
        return activation;
    }

    public int getInputSize() {
        if (neurons.isEmpty()) {
            throw new IllegalStateException("Layer has no neurons");
        }
        return neurons.get(0).getWeights().length;
    }

    public void zeroGradients() {
        for (Neuron neuron : neurons) {
            neuron.zeroGradients();
        }
    }
}
