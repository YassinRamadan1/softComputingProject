package fuzzy.operators.aggregation;

public interface Aggregation {
    double apply(double existing, double newValue);
}
