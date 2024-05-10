package bots.banoffeepie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trainer {
    public static int EPOCHS = 100;
    public static int ITERS  = 10;

    public static void main(String[] args) throws IOException {
        String trainingPath = args[0];
        String output = args[1];

        NeuralNetwork nn = BuilderHelper.createNetwork();

        trainNetwork(nn, new FileReader(trainingPath));

        // export it
        String jsonedNetwork = nn.exportNetwork();
        // write
        FileWriter fileWriter = new FileWriter(output);
        fileWriter.write(jsonedNetwork);
        fileWriter.close();
    }

    private static void trainNetwork(NeuralNetwork nn, FileReader fileReader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        Double bestEpochLoss = null;
        Random random = new Random();

        // Read line by line (csv)
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            List<Double> parsed = parseCsvLine(line);

            // Last value is the expected
            Double expected = parsed.get(parsed.size() - 1);
            parsed.remove(parsed.size() - 1);

            NNContext ctx = new NNContext(parsed);

            for (int epoch = 0; epoch < EPOCHS; epoch++) {
                // get one of the neurons
                int neuronIdToMutate = random.nextInt(1, nn.getNeurons().size() - 1);
                Neuron neuronToMutate = nn.getNeurons().get(neuronIdToMutate);
                neuronToMutate.mutate(random, nn);

                List<Double> expectedList = new ArrayList<>();
                List<Double> computedList = new ArrayList<>();
                for (int i = 0; i < ITERS; i++) {
                    double computed = nn.compute(ctx);
                    computedList.add(computed);
                    expectedList.add(expected * 10);
                }
                Double thisEpochLoss = Utils.meanSquareLoss(expectedList, computedList);

                if (epoch % 10 == 0)
                    System.out.printf(
                            "  Epoch: %s | bestEpochLoss: %.15f | thisEpochLoss: %.15f%n",
                            epoch, bestEpochLoss, thisEpochLoss
                    );

                if (bestEpochLoss == null) {
                    bestEpochLoss = thisEpochLoss;
                    neuronToMutate.remember();
                } else {
                    if (thisEpochLoss < bestEpochLoss) {
                        bestEpochLoss = thisEpochLoss;
                        neuronToMutate.remember();
                    } else {
                        neuronToMutate.forget(nn);
                    }
                }
            }
        }

        System.out.println("\nFinal best epoch loss: " + bestEpochLoss);
    }

    private static List<Double> parseCsvLine(String line){
        List<Double> inputs = new ArrayList<>();

        for (String d: line.split(",")) {
            inputs.add(Double.parseDouble(d));
        }

        return inputs;
    }
}
