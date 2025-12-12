package fuzzy.operators.aggregation;

public interface Aggregation {
    double apply(double existing, double newValue);
}

/*
if we have two rules affect the same consequent:
     we choose how to deal with it (sum, max)
*/