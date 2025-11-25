package fuzzy.operators.aggregation;

public class MaxAggregation implements Aggregation{

    @Override
    public double apply(double existing, double newValue) {
        return Math.max(existing, newValue);
    }
}
