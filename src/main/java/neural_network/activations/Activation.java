package neural_network.activations;

public interface Activation {
    double apply(double x);

    double derivative(double x);

    default String getName() {
        return this.getClass().getSimpleName();
    }
}