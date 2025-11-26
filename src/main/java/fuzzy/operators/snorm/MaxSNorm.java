package fuzzy.operators.snorm;

public class MaxSNorm implements SNorm {

    @Override
    public double apply(double a, double b) {
        return Math.max(a, b);
    }
}
