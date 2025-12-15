package neural_network.activations;

public interface Activation {
    double forward(double x);

    double derivative(double x);
    
    String getName();
} 