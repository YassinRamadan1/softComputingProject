package fuzzy.inference.sugeno;

import java.util.Map;

public class SugenoOutput {
    private final double constantValue;

    public SugenoOutput(double constantValue) {
        this.constantValue = constantValue;
    }
    public double getConstantValue() {return constantValue;}
    @Override
    public String toString() {
        return String.format("%.2f", constantValue);
    }
}
