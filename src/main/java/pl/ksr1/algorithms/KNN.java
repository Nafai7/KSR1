package pl.ksr1.algorithms;

import pl.ksr1.datastructures.Article;
import pl.ksr1.datastructures.Dictionary;
import pl.ksr1.datastructures.FeatureVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.ksr1.StaticVariables.PLACES;

public class KNN {
    public static String classify(FeatureVector featureVector, List<FeatureVector> trainingVectors, String metric, int k) {
        Map<FeatureVector, Float> distances = new HashMap<>();
        System.out.print("\n");
        for (int i = 0; i < trainingVectors.size(); i++) {
            System.out.print("\n");
            System.out.print(Metrics.calculateDistance(featureVector, trainingVectors.get(i), metric));
            distances.put(trainingVectors.get(i), Metrics.calculateDistance(featureVector, trainingVectors.get(i), metric));
        }
        Map<FeatureVector, Float> sortedDistances = new HashMap<>();
        distances.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> sortedDistances.put(x.getKey(), x.getValue()));

        System.out.print("\n");
        System.out.print(k);
        System.out.print("\n");
        System.out.print(sortedDistances.entrySet().stream().toList());
        Map<String, Integer> places = new HashMap<>();
        for (int i = 0; i < k; i++) {
            System.out.print("\n");
            System.out.print(sortedDistances.entrySet().stream().toList().get(i).getKey().getPlace());
            if (places.containsKey(sortedDistances.entrySet().stream().toList().get(i).getKey().getPlace())) {
                places.put(sortedDistances.entrySet().stream().toList().get(i).getKey().getPlace(), places.get(sortedDistances.entrySet().stream().toList().get(i).getKey().getPlace()) + 1);
            } else {
                places.put(sortedDistances.entrySet().stream().toList().get(i).getKey().getPlace(), 1);
            }
        }

        int maxValue = 0;
        String result = "";
        for (Map.Entry<String, Integer> entry : places.entrySet()) {
            if (maxValue < entry.getValue()) {
                maxValue = entry.getValue();
                result = entry.getKey();
            }
        }

        return result;
    }
}
