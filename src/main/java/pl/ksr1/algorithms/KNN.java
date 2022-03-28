package pl.ksr1.algorithms;

import pl.ksr1.datastructures.Article;
import pl.ksr1.datastructures.Dictionary;
import pl.ksr1.datastructures.FeatureVector;

import java.util.*;
import java.util.stream.Collectors;

import static pl.ksr1.StaticVariables.PLACES;

public class KNN {
    public static String classify(FeatureVector featureVector, List<FeatureVector> trainingVectors, String metric, int k) {
        Map<FeatureVector, Float> distances = new HashMap<>();
//        System.out.print("\n");
        for (int i = 0; i < trainingVectors.size(); i++) {
//            System.out.print("\n");
//            System.out.print(Metrics.calculateDistance(featureVector, trainingVectors.get(i), metric));
            distances.put(trainingVectors.get(i), Metrics.calculateDistance(featureVector, trainingVectors.get(i), metric, featureVector.getIsUsed()));
        }
        Map<FeatureVector, Float> sortedDistances = new LinkedHashMap<>();
        List list = new LinkedList(distances.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return -((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
//            System.out.print("\n");
//            System.out.print((Float) entry.getValue());
            sortedDistances.put((FeatureVector) entry.getKey(), (Float) entry.getValue());
        }

//        System.out.print("\n");
//        System.out.print(sortedDistances.entrySet().stream().toList().get(0).getKey().getPlace());
        Map<String, Integer> places = new HashMap<>();
        for (int i = 0; i < k; i++) {
//            System.out.print("\n");
//            System.out.print(sortedDistances.entrySet().stream().toList().get(i).getKey().getPlace());
            if (places.containsKey(sortedDistances.entrySet().stream().toList().get(i).getKey().getPlace())) {
                places.put(sortedDistances.entrySet().stream().toList().get(i).getKey().getPlace(), places.get(sortedDistances.entrySet().stream().toList().get(i).getKey().getPlace()) + 1);
            } else {
                places.put(sortedDistances.entrySet().stream().toList().get(i).getKey().getPlace(), 1);
            }
        }
//        System.out.print("\n");
//        System.out.print(places);
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
