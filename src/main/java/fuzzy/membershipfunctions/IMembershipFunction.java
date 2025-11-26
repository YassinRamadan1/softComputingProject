package fuzzy.membershipfunctions;

import java.util.Vector;

public interface IMembershipFunction {
    // to get membership of x
    double getMembership(double x);
    double calculateCentroid();
    Vector<Double> getPoints();
    Vector<Double> getInverse(double membership);
    double getMin();
    double getMax();

    // get function name "low, medium, ..."
    String getName();
}