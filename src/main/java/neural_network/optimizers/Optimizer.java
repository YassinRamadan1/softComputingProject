package neural_network.optimizers;

public interface Optimizer {

    public double getNewWeight(double oldWeight, double gradient);
}
