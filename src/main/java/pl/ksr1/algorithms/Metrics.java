package pl.ksr1.algorithms;

import pl.ksr1.datastructures.FeatureVector;

import java.util.*;

import static pl.ksr1.StaticVariables.*;

public class Metrics {

    public static float calculateDistance(FeatureVector featureVector1, FeatureVector featureVector2, String metric) {
        List<Float> vector1 = new ArrayList<>();
        List<Float> vector2 = new ArrayList<>();

        //###### TEXT LENGTH
        vector1.add((float)featureVector1.getTextLength());
        vector2.add((float)featureVector2.getTextLength());

        //###### REGIONS
        vector1.add((float)1.0 -trigram(featureVector1.getFirstRegion(), featureVector2.getFirstRegion()));
        vector2.add((float)0.0);

        vector1.add((float)1.0 -trigram(featureVector1.getMostCommonRegion(), featureVector2.getMostCommonRegion()));
        vector2.add((float)0.0);

        vector1.add((float)1.0 -trigram(featureVector1.getMostOccurringPlaceInRegions(), featureVector2.getMostOccurringPlaceInRegions()));
        vector2.add((float)0.0);

        vector1.add(featureVector1.getPercentageOfMostOccurringPlaceInRegions());
        vector2.add(featureVector2.getPercentageOfMostOccurringPlaceInRegions());

        //###### CURRENCY
        vector1.add((float)1.0 -trigram(featureVector1.getFirstCurrency(), featureVector2.getFirstCurrency()));
        vector2.add((float)0.0);

        vector1.add((float)1.0 -trigram(featureVector1.getMostCommonCurrency(), featureVector2.getMostCommonCurrency()));
        vector2.add((float)0.0);

        //###### CELEBRITIES
        vector1.add((float)1.0 -trigram(featureVector1.getFirstCelebrity(), featureVector2.getFirstCelebrity()));
        vector2.add((float)0.0);

        //###### POLITICIANS
        vector1.add((float)1.0 -trigram(featureVector1.getFirstPolitician(), featureVector2.getFirstPolitician()));
        vector2.add((float)0.0);

        vector1.add(featureVector1.getPercentageOfMostOccurringPlaceInPoliticians());
        vector2.add(featureVector2.getPercentageOfMostOccurringPlaceInPoliticians());

        float distance;
        switch (metric){
            default:
                distance = euclidean(vector1, vector2);
                break;
            case STREET:
                distance = street(vector1, vector2);
                break;
            case CHEBYSHEV:
                distance = chebyshev(vector1, vector2);
                break;

        }

        return distance;
    }

    private static float euclidean(List<Float> vector1, List<Float> vector2) {
        float result = (float)0.0;

        for (int i = 0; i < vector1.size(); i++) {
            result += Math.pow(vector1.get(i) - vector2.get(i), 2);
        }

        return (float)Math.sqrt(result);
    }

    private static float street(List<Float> vector1, List<Float> vector2) {
        float result = (float)0.0;

        for (int i = 0; i < vector1.size(); i++) {
            result += Math.abs(vector1.get(i) - vector2.get(i));
        }

        return result;
    }

    private static float chebyshev(List<Float> vector1, List<Float> vector2) {
        List<Float> values =  new ArrayList<>();

        for (int i = 0; i < vector1.size(); i++) {
            values.add(Math.abs(vector1.get(i) - vector2.get(i)));
        }

        return Collections.max(values);
    }

    private static float trigram(String text1, String text2) {
        if (text1.length() >= 3 && text2.length() >= 3) {
            List<String> trigrams1 = new ArrayList<>();

            for (int i = 0; i < text1.length() - 2; i++) {
                trigrams1.add(text1.toLowerCase().substring(i, i + 3));
            }
            int result = 0;
            for (int i = 0; i < text2.length() - 2; i++) {
                String trigram = text2.toLowerCase().substring(i, i + 3);
                if (trigrams1.contains(trigram)) {
                    result += 1;
                }
            }

            return (float)(result/trigrams1.size());
        } else if (text1.length() == 0 && text2.length() == 0) {
            return (float) 1.0;
        } else {
            return (float) 0.0;
        }
    }
}
