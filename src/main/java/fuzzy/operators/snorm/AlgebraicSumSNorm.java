package fuzzy.operators.snorm;

public class AlgebraicSumSNorm implements SNorm {
    @Override
    public double apply(double a, double b) {
        return ((a + b) - (a * b));
    }
}
