package fuzzy.operators.implication;

public class MinImplication implements Implication {
    @Override
    public double apply(double degree, double consequentMFValue) {
        return Math.min(degree, consequentMFValue);
    }
}
