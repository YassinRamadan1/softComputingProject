package neural_network.optimizers;

public class SGD implements Optimizer{

    double learningRate = 0.1;
    
    public SGD(double learningRate)
    {
        this.learningRate = learningRate;
    }

    @Override
    public double getNewWeight(double oldWeight, double gradient)
    {
        return oldWeight - learningRate * gradient;
    }
}
