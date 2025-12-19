package neural_network.case_study;

import neural_network.activations.Activation;
import neural_network.case_study.factories.ActivationFactory;
import neural_network.case_study.factories.InitializerFactory;
import neural_network.case_study.factories.LossFactory;
import neural_network.case_study.factories.OptimizerFactory;
import neural_network.core.Layer;
import neural_network.core.ModelConfig;
import neural_network.core.NeuralNetwork;
import neural_network.training.Trainer;
import neural_network.utils.*;

public class BanknoteCaseStudy {

    public static void main(String[] args) {
        CaseStudyConfig cfg = ConfigLoader.load(SD.CONFIG_PATH);
        CSVLoader.Dataset dataset = CSVLoader.load(cfg.dataset.path, cfg.dataset.hasHeader);

        double[][] inputs = dataset.inputs();
        double[][] targets = dataset.targets();

        DatasetValidator.validate(inputs, targets);

        inputs = Normalization.minMaxNormalize(inputs);

        var split = TrainTestSplit.split(inputs, targets, cfg.dataset.trainRatio, cfg.dataset.shuffle);

        ModelConfig modelConfig = new ModelConfig.Builder()
                .learningRate(cfg.model.learningRate)
                .epochs(cfg.model.epochs)
                .batchSize(cfg.model.batchSize)
                .lossFunction(LossFactory.fromString(cfg.model.loss))
                .optimizer(OptimizerFactory.fromString(cfg.model.optimizer, cfg.model.learningRate))
                .initializer(InitializerFactory.fromString(cfg.model.initializer))
                .build();

        NeuralNetwork network = new NeuralNetwork(modelConfig);

        int previousSize = split.xTrain()[0].length;
        for (int i = 0; i < cfg.layers.size(); i++) {
            CaseStudyConfig.LayerConfig layerCfg = cfg.layers.get(i);
            Activation activation = ActivationFactory.fromString(layerCfg.activation);
            
            int nextSize;
            if (i < cfg.layers.size() - 1) {
                nextSize = cfg.layers.get(i + 1).neurons;
            } else {
                nextSize = layerCfg.neurons;
            }

            network.addLayer(new Layer(previousSize, layerCfg.neurons, nextSize, activation, modelConfig.getInitializer()));
            
            previousSize = layerCfg.neurons;
        }

        Trainer trainer = new Trainer(network, modelConfig);

        double[][] validatedInputs = InputValidator.validate(split.xTrain(), network.getInputSize(), 0.0);

        trainer.train(validatedInputs, split.yTrain());

        double[][] predictions = network.predict(split.xTest());

        var metrics = ClassificationMetrics.evaluate(predictions, split.yTest(), cfg.model.threshold);

        System.out.println("\n=== Evaluation Results ===");
        System.out.println("Accuracy: " + metrics.accuracy());
        System.out.println("TP: " + metrics.truePositive() + "  FP: " + metrics.falsePositive());
        System.out.println("TN: " + metrics.trueNegative() + "  FN: " + metrics.falseNegative());
    }
}
