package fuzzy.linguistic;

import fuzzy.membershipfunctions.IMembershipFunction;
import fuzzy.membershipfunctions.TrapezoidalMF;
import fuzzy.membershipfunctions.TriangularMF;
import fuzzy.util.StaticData;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestFuzzifier {
    public static void main(String[] args) {

        // 1. Create Membership Functions
        IMembershipFunction low = new TrapezoidalMF(StaticData.LOW, 0, 0, 100, 300);
        IMembershipFunction medium = new TriangularMF(StaticData.MEDIUM, 200, 500, 800);
        IMembershipFunction high = new TrapezoidalMF(StaticData.HIGH, 700, 900, 1000, 1000);

        IMembershipFunction soft = new TrapezoidalMF(StaticData.SOFT, 0, 0, 20, 40);
        IMembershipFunction ordinary = new TrapezoidalMF(StaticData.ORDINARY, 20, 40, 60, 80);
        IMembershipFunction stiff = new TrapezoidalMF(StaticData.STIFF, 60, 80, 100, 100);

        // 2. Create Fuzzy Variable
        FuzzyVariable light = new FuzzyVariable(StaticData.LIGHT_INTENSITY, 0, 1000);
        light.addFuzzySet(new FuzzySet(StaticData.LOW, low));
        light.addFuzzySet(new FuzzySet(StaticData.MEDIUM, medium));
        light.addFuzzySet(new FuzzySet(StaticData.HIGH, high));

        FuzzyVariable fabric = new FuzzyVariable(StaticData.FABRIC, 0, 100);
        fabric.addFuzzySet(new FuzzySet(StaticData.SOFT, soft));
        fabric.addFuzzySet(new FuzzySet(StaticData.ORDINARY, ordinary));
        fabric.addFuzzySet(new FuzzySet(StaticData.STIFF, stiff));

        // 3. Create Fuzzifier
        Fuzzifier fuzzifier1 = new Fuzzifier();
        fuzzifier1.addVariable(light);

        Fuzzifier fuzzifier2 = new Fuzzifier();
        fuzzifier2.addVariable(fabric);

        // 4. Create CrispValue
        Map<String, Double> inputs1 = new LinkedHashMap<>();
        inputs1.put(StaticData.LIGHT_INTENSITY, 450.0);

        Map<String, Double> inputs2 = new LinkedHashMap<>();
        inputs2.put(StaticData.FABRIC, 25.0);

        // 5. Call fuzzify to make fuzzification
        Map<String, Map<String, Double>> result1 = fuzzifier1.fuzzify(inputs1);
        Map<String, Map<String, Double>> result2 = fuzzifier2.fuzzify(inputs2);

        // 6. Display Results
        System.out.println("Fuzzification1 Result:");
        for (String var : result1.keySet()) {
            System.out.println(var + " => " + result1.get(var));
        }

        System.out.println("Fuzzification2 Result:");
        for (String var : result2.keySet()) {
            System.out.println(var + " => " + result2.get(var));
        }
    }
}