package neural_network.activations;

public class Linear implements Activation {
    @Override
    public double forward(double x){
        return x;
    }

    @Override
    public double derivative(double x){
        return 1;
    }
    @Override
    public String getName(){
        return "Linear";
    }
}
