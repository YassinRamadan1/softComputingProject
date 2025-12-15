package neural_network.utils;

public final class ClassificationMetrics {

    private ClassificationMetrics() {}

    public static Result evaluate(double[][] predictions, double[][] targets, double threshold) {
        if (predictions.length != targets.length) {
            throw new IllegalArgumentException("Predictions and targets size mismatch");
        }

        int correct = 0;
        int tp = 0, tn = 0, fp = 0, fn = 0;

        for (int i = 0; i < predictions.length; i++) {
            int predicted = predictions[i][0] >= threshold ? 1 : 0;
            int actual = (int) targets[i][0];
            if (predicted == actual) correct++;
            if (predicted == 1 && actual == 1) tp++;
            else if (predicted == 0 && actual == 0) tn++;
            else if (predicted == 1 && actual == 0) fp++;
            else if (predicted == 0 && actual == 1) fn++;
        }

        double accuracy = (double) correct / predictions.length;
        return new Result(accuracy, tp, tn, fp, fn);
    }

    public record Result(double accuracy, int truePositive, int trueNegative, int falsePositive, int falseNegative) {}
}
