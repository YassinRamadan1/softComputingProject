package membership;

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