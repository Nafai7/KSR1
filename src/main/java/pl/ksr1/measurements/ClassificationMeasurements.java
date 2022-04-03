package pl.ksr1.measurements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.ksr1.StaticVariables.PLACES;

public class ClassificationMeasurements {

    public static List<Float> calculateClassifcationMeasurements(List<List<Integer>> truesAndFalses, Map<String, Integer> allPlaces) {
        List<Float> result = new ArrayList<Float>();

        result.add(accuracy(truesAndFalses, allPlaces));
        result.add(precisionAll(truesAndFalses, allPlaces));
        result.add(recallAll(truesAndFalses, allPlaces));
        result.add(f1(result.get(result.size() - 1), result.get(result.size() - 2)));
        for (int i = 0; i < PLACES.size(); i++) {
            result.add(precision(truesAndFalses.get(i).get(0), truesAndFalses.get(i).get(1)));
            result.add(recall(truesAndFalses.get(i).get(0), truesAndFalses.get(i).get(2)));
            result.add(f1(result.get(result.size() - 1), result.get(result.size() - 2)));
        }
        return result;
    }

    private static float accuracy(List<List<Integer>> truesAndFalses, Map<String, Integer> allPlaces) {
        float result = 0;
        int sum = 0;
        for (int i = 0; i < PLACES.size(); i++) {
            for (Map.Entry<String, Integer> entry : allPlaces.entrySet()) {
                if (entry.getKey().equals(PLACES.get(i))) {
                    result += (float) truesAndFalses.get(i).get(0);
                    sum += entry.getValue();
                }
            }
        }

        return result / sum;
    }

    private static float precision(float truePositive, float falsePositive) {
        if ((truePositive + falsePositive) != 0) {
            return truePositive / (truePositive + falsePositive);
        } else {
            return 0;
        }
    }

    private static float precisionAll(List<List<Integer>> truesAndFalses, Map<String, Integer> allPlaces) {
        float result = 0;
        int sum = 0;
        for (int i = 0; i < PLACES.size(); i++) {
            for (Map.Entry<String, Integer> entry : allPlaces.entrySet()) {
                if (entry.getKey().equals(PLACES.get(i))) {
//                    System.out.print("\n");
//                    System.out.print(entry.getKey());
//                    System.out.print(truesAndFalses.get(i));
                    if ((truesAndFalses.get(i).get(0) + truesAndFalses.get(i).get(1)) != 0) {
                        result += (float) precision(truesAndFalses.get(i).get(0), truesAndFalses.get(i).get(1)) * entry.getValue();
//                        System.out.print(result);
                    } else {
                        result += 0;
                    }
                    sum += entry.getValue();
                }
            }
        }

        return result / sum;
    }

    private static float recall(float truePositive, float falseNegative) {
        if ((truePositive + falseNegative) != 0) {
            return truePositive / (truePositive + falseNegative);
        } else {
            return 0;
        }
    }

    private static float recallAll(List<List<Integer>> truesAndFalses, Map<String, Integer> allPlaces) {
        float result = 0;
        int sum = 0;
        for (int i = 0; i < PLACES.size(); i++) {
            for (Map.Entry<String, Integer> entry : allPlaces.entrySet()) {
                if (entry.getKey().equals(PLACES.get(i))) {
//                    System.out.print("\n");
//                    System.out.print(entry.getKey());
//                    System.out.print(truesAndFalses.get(i));
                    if ((truesAndFalses.get(i).get(0) + truesAndFalses.get(i).get(2)) != 0) {
                        result += (float) recall(truesAndFalses.get(i).get(0), truesAndFalses.get(i).get(2)) * entry.getValue();
//                        System.out.print(result);
                    } else {
                        result += 0;
                    }
                    sum += entry.getValue();
                }
            }
        }

        return result / sum;
    }

    private static float f1(float precision, float recall) {
        if ((precision + recall) != 0) {
            return (2 * precision * recall) / (precision + recall);
        } else {
            return 0;
        }
    }
}