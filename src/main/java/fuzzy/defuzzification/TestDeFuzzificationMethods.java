package fuzzy.defuzzification;

import fuzzy.linguistic.FuzzySet;
import fuzzy.linguistic.FuzzyVariable;
import fuzzy.membershipfunctions.IMembershipFunction;
import fuzzy.membershipfunctions.TriangularMF;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestDeFuzzificationMethods {

    public static void main(String[] args) {

        FuzzyVariable outputVar = new FuzzyVariable("Output", 0.0, 100.0);
        IMembershipFunction mfLow = new TriangularMF("Low", 0, 0, 50);
        IMembershipFunction mfMedium = new TriangularMF("Medium", 25, 50, 75);
        IMembershipFunction mfHigh = new TriangularMF("High", 50, 100, 100);
        FuzzySet lowSet = new FuzzySet("Low", mfLow);
        FuzzySet mediumSet = new FuzzySet("Medium", mfMedium);
        FuzzySet highSet = new FuzzySet("High", mfHigh);
        outputVar.addFuzzySet(lowSet);
        outputVar.addFuzzySet(mediumSet);
        outputVar.addFuzzySet(highSet);

        Map<String, Double> Memberships = new LinkedHashMap();
        Memberships.put("Low", 0.3);
        Memberships.put("Medium", 0.6);
        Memberships.put("High", 0.2);
        DeFuzzificationMethod mom = new MeanOfMax(outputVar, Memberships);
        DeFuzzificationMethod wa = new WeightedAverage(outputVar, Memberships);
        DeFuzzifier momDeFuzzifier = new DeFuzzifier(outputVar, Memberships, mom);
        System.out.println("Mean of Max DeFuzzified Output: " + momDeFuzzifier.getCrispOutput());
        System.out.println("Mean of Max Crisp Set: " + momDeFuzzifier.getCrispSet());
        DeFuzzifier waDeFuzzifier = new DeFuzzifier(outputVar, Memberships, wa);
        System.out.println("Weighted Average DeFuzzified Output: " + waDeFuzzifier.getCrispOutput());
        System.out.println("Weighted Average Crisp Set: " + waDeFuzzifier.getCrispSet());
    }
}
