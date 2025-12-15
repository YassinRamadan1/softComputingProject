package neural_network.optimizers;

public class SGDWithMomentum implements Optimizer {

    private double learningRate = 0.1;
    private double momentumCoefficient = 0.9; 
    private double velocity = 0.0;
    
    public SGDWithMomentum(double learningRate,  double momentumCoefficient, double velocity)
    {
        this.learningRate = learningRate;
        this.momentumCoefficient = momentumCoefficient;
        this.velocity = velocity;
    }

    @Override
    public double getNewWeight(double oldWeight, double gradient)
    {
        velocity = momentumCoefficient * velocity - learningRate * gradient;
        return oldWeight + velocity;
    }
}
