package neural_network.loss;

public interface Loss {
    double computeLoss(double[] yTarget, double[] yPred);
    double[] computeGradient(double[] yTarget, double[] yPred);
}
