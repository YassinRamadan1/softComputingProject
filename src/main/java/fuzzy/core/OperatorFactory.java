package fuzzy.core;

import fuzzy.operators.aggregation.Aggregation;
import fuzzy.operators.aggregation.MaxAggregation;
import fuzzy.operators.aggregation.SumAggregation;
import fuzzy.operators.implication.Implication;
import fuzzy.operators.implication.MinImplication;
import fuzzy.operators.implication.ProductImplication;
import fuzzy.operators.snorm.AlgebraicSumSNorm;
import fuzzy.operators.snorm.MaxSNorm;
import fuzzy.operators.snorm.SNorm;
import fuzzy.operators.tnorm.AlgebraicProductTNorm;
import fuzzy.operators.tnorm.MinTNorm;
import fuzzy.operators.tnorm.TNorm;

public class OperatorFactory {
    public static TNorm createTNorm(FuzzyConfiguration.AndOperatorType type) {
        return switch (type) {
            case MIN -> new MinTNorm();
            case ALGEBRAIC_PRODUCT -> new AlgebraicProductTNorm();
        };
    }
    public static SNorm createSNorm(FuzzyConfiguration.OrOperatorType type) {
        return switch (type) {
            case MAX -> new MaxSNorm();
            case ALGEBRAIC_SUM -> new AlgebraicSumSNorm();
        };
    }
    public static Implication createImplication(FuzzyConfiguration.ImplicationOperatorType type) {
        return switch (type) {
            case MIN -> new MinImplication();
            case PRODUCT -> new ProductImplication();
        };
    }
    public static Aggregation createAggregation(FuzzyConfiguration.AggregationOperatorType type) {
        return switch (type) {
            case MAX -> new MaxAggregation();
            case SUM -> new SumAggregation();
        };
    }
}