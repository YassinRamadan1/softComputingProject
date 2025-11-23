package membership;

import java.util.Vector;

public class TriangularMF implements IMembershipFunction {
    private final double a, b, c;
    private final String name;

    public TriangularMF(String name, double a, double b, double c) {
        this.name = name;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculateCentroid() {
        return (a + b + c) / 3.0;
    }

    @Override
    public Vector<Double> getPoints()
    {
        Vector<Double> points = new Vector<>();
        points.add(a);
        points.add(b);
        points.add(c);
        return points;
    }

    @Override
    public Vector<Double> getInverse(double membership)
    {
        Vector<Double> xValues = new Vector<>();
        xValues.add((b - a) * membership + a);
        xValues.add((b - c) * membership + c);

        return xValues;
    }

    @Override
    public double getMembership(double x) {
        if (x <= a || x >= c) 
            return 0.0;
        else if (x == b) 
            return 1.0;
        else if (x > a && x < b) 
            return (x - a) / (b - a); // from line equation left line of triangular
        else 
            return (c - x) / (c - b); // from line equation right line of triangular
    }

    @Override
    public double getMin() { 
        return a; 
    }

    @Override
    public double getMax() { 
        return c; 
    }

    @Override
    public String getName() { 
        return name; 
    }
}