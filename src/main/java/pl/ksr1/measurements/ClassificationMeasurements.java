package pl.ksr1.measurements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.ksr1.StaticVariables.PLACES;

public class ClassificationMeasurements {

    public static List<Float> calculateClassifcationMeasurements(List<Map<String, Integer>> classifiedPlaces, Map<String, Integer> allPlaces) {
        List<Float> result = new ArrayList<Float>();
        List<List<Integer>> truesAndFalses = new ArrayList<List<Integer>>(); // true, falsepositive, falsenegative
        for (int i = 0; i < PLACES.size(); i++) {
            truesAndFalses.add(new ArrayList<Integer>());
            int trues = 0;
            int falsePositives = 0;
            int falseNegatives = 0;

            for (int j = 0; j < classifiedPlaces.size(); j++) {
                if (i != j) {
                    for (Map.Entry<String, Integer> entry : classifiedPlaces.get(j).entrySet()) {
                        if (entry.getKey().equals(PLACES.get(i))) {
                            falsePositives += entry.getValue();
                        }
                    }
                } else {
                    for (Map.Entry<String, Integer> entry : classifiedPlaces.get(j).entrySet()) {
                        if (entry.getKey().equals(PLACES.get(i))) {
                            trues += entry.getValue();
                        } else {
                            falseNegatives += entry.getValue();
                        }
                    }
                }
            }
            truesAndFalses.get(i).add(trues);
            truesAndFalses.get(i).add(falsePositives);
            truesAndFalses.get(i).add(falseNegatives);
//            System.out.print("\n");
//            System.out.print(trueAndFalsePositives.get(i));
        }

        for (int i = 0; i < PLACES.size(); i++) {
//            result.add();
            result.add(precision(truesAndFalses, allPlaces));
        }


        return result;
    }

    private static float precision(List<List<Integer>> truesAndFalses, Map<String, Integer> allPlaces) {
        float result = 0;
        int i = 0;
        int sum = 0;
        for (Map.Entry<String, Integer> entry : allPlaces.entrySet()) {

            result += (truesAndFalses.get(i).get(0) * entry.getValue()) / (truesAndFalses.get(i).get(0) + truesAndFalses.get(i).get(1));
            sum += entry.getValue();
            i++;
        }

        return result/sum;
    }

}
