package neural_network.core;

import neural_network.activations.Activation;
import neural_network.initializers.Initializer;

import java.util.ArrayList;

public class Layer {
    private final ArrayList<Neuron> neurons;
    private final Activation activation;

    public Layer(int inputSize, int neuronCount, Activation activation, Initializer initializer) {
        if (inputSize <= 0 || neuronCount <= 0) {
            throw new IllegalArgumentException("Input size and neuron count must be > 0");
        }

        this.activation = activation;
        this.neurons = new ArrayList<>();
        for (int i = 0; i < neuronCount; i++) {
            double[] weights = initializer.initializeWeights(inputSize);
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

        double[] dLoss_dInputs = null;
        for (int i = 0; i < neurons.size(); i++) {
            double[] neuronInputGradients = neurons.get(i).backward(dLoss_dOutputs[i]);
            if (dLoss_dInputs == null) {
                dLoss_dInputs = neuronInputGradients;
            }
            else {
                for (int j = 0; j < neuronInputGradients.length; j++) {
                    dLoss_dInputs[j] += neuronInputGradients[j];
                }
            }
        }
        return dLoss_dInputs;
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public Activation getActivation() {
        return activation;
    }
}
