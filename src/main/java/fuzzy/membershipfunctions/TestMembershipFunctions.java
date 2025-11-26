package fuzzy.membershipfunctions;

public class TestMembershipFunctions {
    public static void main(String[] args) {
        IMembershipFunction tri = new TriangularMF("Medium", 300, 500, 700);
        IMembershipFunction trap = new TrapezoidalMF("Low", 0, 100, 200, 300);

        double[] testValues = {0, 100, 50, 200, 300, 450, 500, 700, 900};

        for (double x : testValues) {
            System.out.printf("x=%.1f => Triangular=%.2f, Trapezoidal=%.2f%n",
                    x,
                    tri.getMembership(x),
                    trap.getMembership(x)
            );
        }
    }
}
