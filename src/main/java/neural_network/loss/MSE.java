package neural_network.loss;

public class MSE implements Loss {

    @Override
    public double forward(double[] yTarget, double[] yPred) {
        double sum = 0.0;
        for (int i = 0; i < yTarget.length; i++) {
            double diff = yTarget[i] - yPred[i];
            sum += diff * diff;
        }
        return sum / yTarget.length;
    }

    @Override
    public double[] backward(double[] yTarget, double[] yPred) {
        double[] gradient = new double[yTarget.length];
        for (int i = 0; i < yTarget.length; i++) {
            gradient[i] = 2 * (yPred[i] - yTarget[i]) / yTarget.length;
        }
        return gradient;
    }
}
