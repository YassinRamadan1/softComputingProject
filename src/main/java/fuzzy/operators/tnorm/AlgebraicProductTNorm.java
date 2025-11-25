package fuzzy.operators.tnorm;

public class AlgebraicProductTNorm implements TNorm{

    @Override
    public double apply(double a, double b) {
        return a*b;
    }
}
