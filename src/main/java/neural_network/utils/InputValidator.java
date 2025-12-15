package neural_network.utils;

public final class InputValidator {

    private InputValidator() {}

    public static double[] validate(double[] input, int expectedSize, double defaultValue) {
        if (input == null) {
            throw new IllegalArgumentException("Input vector is null");
        }
        if (input.length != expectedSize) {
            throw new IllegalArgumentException("Expected " + expectedSize + " features, got " + input.length);
        }

        double[] clean = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            double v = input[i];
            clean[i] = Double.isNaN(v) || Double.isInfinite(v) ? defaultValue : v;
        }

        return clean;
    }

    public static double[][] validate(double[][] inputs, int expectedFeatureSize, double defaultValue) {
        if (inputs == null || inputs.length == 0) {
            throw new IllegalArgumentException("Input dataset is null or empty");
        }

        double[][] clean = new double[inputs.length][];
        for (int i = 0; i < inputs.length; i++) {
            clean[i] = validate(inputs[i], expectedFeatureSize, defaultValue);
        }

        return clean;
    }
}
