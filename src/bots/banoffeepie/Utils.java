package bots.banoffeepie;

import java.util.List;

public class Utils {
    /**
     * Apply the sigmoid mathematical function to the parameter `in`.
     * <br>
     * <a href="https://en.wikipedia.org/wiki/Sigmoid_function">More in en.wikipedia.org</a>
     * @param in X
     * @return The result of the function
     */
    public static double sigmoid(double in){
        return 1 / (1 + Math.exp(-in));
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/Mean_squared_error">See en.wikipedia.org</a>
     * @param correctAnswers The expected answers
     * @param predictedAnswers The predicted answers by the Neural Network
     * @return The loss
     */
    public static Double meanSquareLoss(List<Double> correctAnswers, List<Double> predictedAnswers){
        double sumSquare = 0;
        for (int i = 0; i < correctAnswers.size(); i++){
            double error = correctAnswers.get(i) - predictedAnswers.get(i);
            sumSquare += (error * error);
        }
        return sumSquare / (correctAnswers.size());
    }
}
