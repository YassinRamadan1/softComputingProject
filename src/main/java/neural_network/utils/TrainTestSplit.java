package neural_network.utils;

import java.util.Random;

public final class TrainTestSplit {

    private TrainTestSplit() {}

    public static SplitResult split(double[][] inputs, double[][] targets, double trainRatio, boolean shuffle) {
        if (inputs.length != targets.length) {
            throw new IllegalArgumentException("Inputs and targets size mismatch");
        }

        int n = inputs.length;
        int trainSize = (int) (n * trainRatio);

        int[] indices = new int[n];
        for (int i = 0; i < n; i++) indices[i] = i;

        if (shuffle) shuffle(indices);

        double[][] xTrain = new double[trainSize][];
        double[][] yTrain = new double[trainSize][];
        double[][] xTest = new double[n - trainSize][];
        double[][] yTest = new double[n - trainSize][];

        for (int i = 0; i < trainSize; i++) {
            xTrain[i] = inputs[indices[i]];
            yTrain[i] = targets[indices[i]];
        }
        for (int i = trainSize; i < n; i++) {
            xTest[i - trainSize] = inputs[indices[i]];
            yTest[i - trainSize] = targets[indices[i]];
        }

        return new SplitResult(xTrain, yTrain, xTest, yTest);
    }

    private static void shuffle(int[] indices) {
        Random random = new Random();
        for (int i = indices.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = indices[i];
            indices[i] = indices[j];
            indices[j] = temp;
        }
    }

    public record SplitResult(double[][] xTrain, double[][] yTrain, double[][] xTest, double[][] yTest) {}
}
