package fuzzy.operators.aggregation;

public class SumAggregation implements Aggregation {
    @Override
    public double apply(double existing, double newValue) {
        return existing + newValue;
    }
}
