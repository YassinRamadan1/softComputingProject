package neural_network.optimizers;

public class RMSProp implements Optimizer {
    
    private double learningRate = 0.1;
    private double decayFactor = 0.9;
    private double averageOfSquaredGradients = 0;
    
    public RMSProp(double learningRate,  double decayFactor, double averageOfSquaredGradients)
    {
        this.learningRate = learningRate;
        this.decayFactor = decayFactor;
        this.averageOfSquaredGradients = averageOfSquaredGradients;
    }
    
    @Override
    public double getNewWeight(double oldWeight, double gradient)
    {
        averageOfSquaredGradients = decayFactor * averageOfSquaredGradients + (1 - decayFactor) * gradient * gradient;
        return oldWeight - learningRate * gradient / (Math.sqrt(averageOfSquaredGradients) + 1e-8);
    }
}
