package fuzzy.defuzzification;

import fuzzy.linguistic.FuzzySet;
import fuzzy.linguistic.FuzzyVariable;
import fuzzy.membershipfunctions.IMembershipFunction;
import fuzzy.membershipfunctions.TriangularMF;
import fuzzy.util.StaticData;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestDeFuzzificationMethods {

    public static void main(String[] args) {

        FuzzyVariable outputVar = new FuzzyVariable(StaticData.OUTPUT, 0.0, 100.0);
        IMembershipFunction mfLow = new TriangularMF(StaticData.LOW, 0, 0, 50);
        IMembershipFunction mfMedium = new TriangularMF(StaticData.MEDIUM, 25, 50, 75);
        IMembershipFunction mfHigh = new TriangularMF(StaticData.HIGH, 50, 100, 100);
        FuzzySet lowSet = new FuzzySet(StaticData.LOW, mfLow);
        FuzzySet mediumSet = new FuzzySet(StaticData.MEDIUM, mfMedium);
        FuzzySet highSet = new FuzzySet(StaticData.HIGH, mfHigh);
        outputVar.addFuzzySet(lowSet);
        outputVar.addFuzzySet(mediumSet);
        outputVar.addFuzzySet(highSet);

        Map<String, Double> Memberships = new LinkedHashMap();
        Memberships.put(StaticData.LOW, 0.3);
        Memberships.put(StaticData.MEDIUM, 0.6);
        Memberships.put(StaticData.HIGH, 0.2);
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
