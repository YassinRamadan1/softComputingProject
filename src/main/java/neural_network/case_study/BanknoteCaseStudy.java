package neural_network.case_study;

import neural_network.activations.Activation;
import neural_network.core.Layer;
import neural_network.core.ModelConfig;
import neural_network.core.NeuralNetwork;
import neural_network.initializers.Xavier;
import neural_network.loss.CrossEntropy;
import neural_network.optimizers.SGD;
import neural_network.training.Trainer;
import neural_network.utils.ClassificationMetrics;
import neural_network.utils.Normalization;
import neural_network.utils.SD;
import neural_network.utils.TrainTestSplit;

public class BanknoteCaseStudy {

    public static void main(String[] args) {
        CaseStudyConfig cfg = ConfigLoader.load(SD.CONFIG_PATH);
        CSVLoader.Dataset dataset = CSVLoader.load(cfg.dataset.path, cfg.dataset.hasHeader);

        double[][] inputs = dataset.inputs();
        double[][] targets = dataset.targets();

        inputs = Normalization.minMaxNormalize(inputs);

        var split = TrainTestSplit.split(inputs, targets, cfg.dataset.trainRatio, cfg.dataset.shuffle);

        ModelConfig modelConfig = new ModelConfig.Builder()
                .learningRate(cfg.model.learningRate)
                .epochs(cfg.model.epochs)
                .batchSize(cfg.model.batchSize)
                .lossFunction(new CrossEntropy())
                .optimizer(new SGD(cfg.model.learningRate))
                .initializer(new Xavier())
                .build();

        NeuralNetwork network = new NeuralNetwork(modelConfig);

        int previousSize = split.xTrain()[0].length;
        for (CaseStudyConfig.LayerConfig layerCfg : cfg.layers) {
            Activation activation = ActivationFactory.fromString(layerCfg.activation);
            network.addLayer(new Layer(previousSize, layerCfg.neurons, activation, modelConfig.getInitializer()));
            previousSize = layerCfg.neurons;
        }

        Trainer trainer = new Trainer(network, modelConfig);
        trainer.train(split.xTrain(), split.yTrain());

        double[][] predictions = network.predict(split.xTest());

        var metrics = ClassificationMetrics.evaluate(predictions, split.yTest(), cfg.model.threshold);

        System.out.println("\n=== Evaluation Results ===");
        System.out.println("Accuracy: " + metrics.accuracy());
        System.out.println("TP: " + metrics.truePositive() +
                "  FP: " + metrics.falsePositive());
        System.out.println("TN: " + metrics.trueNegative() +
                "  FN: " + metrics.falseNegative());
    }
}
