package fuzzy.operators.implication;

public class ProductImplication implements Implication {

    @Override
    public double apply(double degree, double consequentMFValue) {
        return degree * consequentMFValue;
    }
}
