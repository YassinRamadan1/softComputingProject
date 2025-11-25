package fuzzy.variables;

import java.util.Vector;

import fuzzy.membership.IMembershipFunction;

public class FuzzySet {
    private final String name;
    private final IMembershipFunction membershipFunction;
    private double weight = 1.0;

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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = Math.max(0, Math.min(1, weight));
    }

    @Override
    public String toString() {
        return "FuzzySet{" + name + ", weight=" + weight + "}";
    }
}