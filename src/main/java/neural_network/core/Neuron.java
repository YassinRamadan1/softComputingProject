package neural_network.core;

import neural_network.activations.Activation;

import java.util.Arrays;

public class Neuron {
    private final double[] weights;
    private double bias;
    private final Activation activation;

    private double[] lastInputs;
    private double lastZ;

    private double[] weightGradients;
    private double biasGradient;

    Neuron(double[] weights, double bias, Activation activation) {
        this.weights = Arrays.copyOf(weights, weights.length);
        this.bias = bias;
        this.activation = activation;
        this.weightGradients = new double[weights.length];
        this.biasGradient = 0.0;
    }

    public double forward(double[] inputs) {
        if (inputs.length != weights.length) {
            throw new IllegalArgumentException("Input size does not match weight size");
        }

        this.lastInputs = Arrays.copyOf(inputs, inputs.length);
        lastZ = bias;
        for (int i = 0; i < weights.length; ++i) {
            lastZ += weights[i] * inputs[i];
        }

        return activation.apply(lastZ);
    }

    public double[] backward(double dLoss_dOutput) {
        double dOutput_dZ = activation.derivative(lastZ);
        double dLoss_dZ = dLoss_dOutput * dOutput_dZ;

        double[] inputGradients = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            weightGradients[i] += dLoss_dZ * lastInputs[i];
            inputGradients[i] = dLoss_dZ * weights[i];
        }

        biasGradient += dLoss_dZ;
        return inputGradients;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getBias() {
        return bias;
    }

    public double[] getWeightGradients() {
        return weightGradients;
    }

    public double getBiasGradient() {
        return biasGradient;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void zeroGradients() {
        if (weightGradients != null) {
            Arrays.fill(weightGradients, 0.0);
        }
        biasGradient = 0.0;
    }
}
