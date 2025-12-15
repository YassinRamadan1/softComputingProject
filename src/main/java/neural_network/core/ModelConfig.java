package neural_network.core;

import neural_network.initializers.Initializer;
import neural_network.loss.Loss;
import neural_network.optimizers.Optimizer;

public class ModelConfig {
    private final double learningRate;
    private final int epochs;
    private final int batchSize;
    private final boolean shuffle;

    private final Loss loss;
    private final Optimizer optimizer;
    private final Initializer initializer;

    private ModelConfig(Builder builder) {
        this.learningRate = builder.learningRate;
        this.epochs = builder.epochs;
        this.batchSize = builder.batchSize;
        this.loss = builder.lossFunction;
        this.optimizer = builder.optimizer;
        this.initializer = builder.initializer;
        this.shuffle = builder.shuffle;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public int getEpochs() {
        return epochs;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public Loss getLoss() {
        return loss;
    }

    public Optimizer getOptimizer() {
        return optimizer;
    }

    public Initializer getInitializer() {
        return initializer;
    }

    public boolean isShuffleEnabled() {
        return shuffle;
    }

    public static class Builder {
        private double learningRate = 0.01;
        private int epochs = 100;
        private int batchSize = 32;

        private Loss lossFunction;
        private Optimizer optimizer;
        private Initializer initializer;

        private boolean shuffle = true;

        public Builder learningRate(double learningRate) {
            this.learningRate = learningRate;
            return this;
        }

        public Builder epochs(int epochs) {
            this.epochs = epochs;
            return this;
        }

        public Builder batchSize(int batchSize) {
            this.batchSize = batchSize;
            return this;
        }

        public Builder lossFunction(Loss lossFunction) {
            this.lossFunction = lossFunction;
            return this;
        }

        public Builder optimizer(Optimizer optimizer) {
            this.optimizer = optimizer;
            return this;
        }

        public Builder initializer(Initializer initializer) {
            this.initializer = initializer;
            return this;
        }

        public Builder shuffle(boolean shuffle) {
            this.shuffle = shuffle;
            return this;
        }

        public ModelConfig build() {
            if (lossFunction == null)
                throw new IllegalStateException("Loss function must be set");

            if (optimizer == null)
                throw new IllegalStateException("Optimizer must be set");

            if (initializer == null)
                throw new IllegalStateException("Initializer must be set");

            return new ModelConfig(this);
        }
    }
}
