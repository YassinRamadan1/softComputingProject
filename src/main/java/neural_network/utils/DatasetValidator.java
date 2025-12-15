package neural_network.utils;

public final class DatasetValidator {

    private DatasetValidator() {}

    public static void validate(double[][] inputs, double[][] targets) {
        if (inputs == null || targets == null) {
            throw new IllegalArgumentException("Dataset is null");
        }

        if (inputs.length != targets.length) {
            throw new IllegalArgumentException("Inputs and targets size mismatch");
        }

        if (inputs.length == 0) {
            throw new IllegalArgumentException("Empty dataset");
        }
    }
}
