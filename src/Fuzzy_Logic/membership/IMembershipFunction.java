package membership;

public interface IMembershipFunction {
    // to get membership of x
    double getMembership(double x);

    double getMin();
    double getMax();

    // get function name "low, medium, ..."
    String getName();
}