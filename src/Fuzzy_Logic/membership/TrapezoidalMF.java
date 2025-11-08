package membership;

public class TrapezoidalMF implements IMembershipFunction {
    private final double a, b, c, d;
    private final String name;

    public TrapezoidalMF(String name, double a, double b, double c, double d) {
        this.name = name;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double getMembership(double x) {
        if (x <= a || x >= d) 
            return 0.0;
        else if (x >= b && x <= c) 
            return 1.0;
        else if (x > a && x < b) 
            return (x - a) / (b - a);
        else 
            return (d - x) / (d - c);
    }

    @Override
    public double getMin() { 
        return a; 
    }

    @Override
    public double getMax() { 
        return d; 
    }

    @Override
    public String getName() { 
        return name; 
    }
}