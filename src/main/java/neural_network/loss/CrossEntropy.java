package neural_network.loss;

public class CrossEntropy implements Loss {

    private static final double EPS = 1e-15;
    @Override
    public double computeLoss(double[] yTarget, double[] yPred) {
        double loss = 0.0;

        for (int i = 0; i < yTarget.length; i++) {
            // to ensure it's between 0 and 1 (avoid log(0))
            double p = Math.clamp(yPred[i], EPS, 1 - EPS);
            loss += yTarget[i] * Math.log(p) + (1 - yTarget[i]) * Math.log(1 - p);
        }
        return -loss / yTarget.length;
    }

    @Override
    public double[] computeGradient(double[] yTarget, double[] yPred) {
        double[] gradient = new double[yTarget.length];

        for (int i = 0; i < yTarget.length; i++) {
            // to ensure it's between 0 and 1 (avoid division by 0)
            double p = Math.clamp(yPred[i], EPS, 1 - EPS);
            gradient[i] = (p - yTarget[i]) / (p * (1 - p));
        }
        return gradient;
    }
}
