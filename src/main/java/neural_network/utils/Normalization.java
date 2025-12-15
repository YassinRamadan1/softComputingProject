package neural_network.utils;

public final class Normalization {

    private Normalization() {}

    public static double[][] minMaxNormalize(double[][] data) {
        int rows = data.length;
        int cols = data[0].length;

        double[] min = new double[cols];
        double[] max = new double[cols];

        for (int j = 0; j < cols; j++) {
            min[j] = Double.POSITIVE_INFINITY;
            max[j] = Double.NEGATIVE_INFINITY;
        }
        for (double[] row : data) {
            for (int j = 0; j < cols; j++) {
                min[j] = Math.min(min[j], row[j]);
                max[j] = Math.max(max[j], row[j]);
            }
        }

        double[][] normalized = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (max[j] == min[j]) {
                    normalized[i][j] = 0.0;
                } else {
                    normalized[i][j] = (data[i][j] - min[j]) / (max[j] - min[j]);
                }
            }
        }

        return normalized;
    }
}
