package fuzzy.linguistic;

import java.util.Vector;

import fuzzy.membershipfunctions.IMembershipFunction;

public class FuzzySet {
    private final String name;
    private final IMembershipFunction membershipFunction;

    public FuzzySet(String name, IMembershipFunction membershipFunction) {
        this.name = name;
        this.membershipFunction = membershipFunction;
    }

    public String getName() {
        return name;
    }

    public IMembershipFunction getMembershipFunction() {
        return membershipFunction;
    }

    public double calculateCentroid() {
        return membershipFunction.calculateCentroid();
    }

    public Vector<Double> getPoints()
    {
        return membershipFunction.getPoints();
    }

    public Vector<Double> getInverse(double membership)
    {
        return membershipFunction.getInverse(membership);
    }

    public double getMembership(double x) {
        return membershipFunction.getMembership(x);
    }

    @Override
    public String toString() {
        return "FuzzySet{" + name;
    }
}