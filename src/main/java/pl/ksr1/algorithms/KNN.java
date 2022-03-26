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
    public static Map<String, Float> classify(Article article, FeatureVector featureVector, List<Dictionary> dictionaries, List<Float> weights) {
        Map<String, Float> result = new HashMap<>();
        for (int i = 0; i < PLACES.size(); i++) {
            result.put(PLACES.get(i), (float)0.0);
        }

        //nie wiemy nic o autorze więc nie mamy jak obliczyć czegoś z tej długości tekstu

        //######### REGIONS
        for (int i = 0; i < PLACES.size(); i++) {
            Dictionary dictionary = dictionaries.get(i);
            List<String> words = dictionary.getWords().stream().map(String::toLowerCase).collect(Collectors.toList());
            if (words.contains(featureVector.getFirstRegion().toLowerCase())) {
                result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + 1 * weights.get(1));
            }
            if (words.contains(featureVector.getMostCommonRegion().toLowerCase())) {
                result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + 1 * weights.get(2));
            }
            if (dictionary.getCountry().equals(featureVector.getMostOccurringPlaceInRegions())) {
                result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + 1 * weights.get(3));
                if (featureVector.getPercentageOfMostOccurringPlaceInRegions() > 10) {
                    result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + 1 * weights.get(4));
                } else {
                    result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + (1 * weights.get(4) * (featureVector.getPercentageOfMostOccurringPlaceInRegions()/10)));
                }
            }
        }

        //######### CURRENCY
        for (int i = PLACES.size(); i < PLACES.size() * 2; i++) {
            Dictionary dictionary = dictionaries.get(i);
            List<String> words = dictionary.getWords().stream().map(String::toLowerCase).collect(Collectors.toList());
            if (words.contains(featureVector.getFirstCurrency().toLowerCase())) {
                result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + 1 * weights.get(5));
            }
            if (words.contains(featureVector.getMostCommonCurrency().toLowerCase())) {
                result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + 1 * weights.get(6));
            }
        }

        //######### CELEBRITIES
        for (int i = PLACES.size() * 2; i < PLACES.size() * 3; i++) {
            Dictionary dictionary = dictionaries.get(i);
            List<String> words = dictionary.getWords().stream().map(String::toLowerCase).collect(Collectors.toList());
            if (words.contains(featureVector.getFirstCelebrity().toLowerCase())) {
                result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + 1 * weights.get(7));
            }
        }

        //######### POLITICIANS
        for (int i = PLACES.size() * 3; i < PLACES.size() * 4; i++) {
            Dictionary dictionary = dictionaries.get(i);
            List<String> words = dictionary.getWords().stream().map(String::toLowerCase).collect(Collectors.toList());
            if (words.contains(featureVector.getFirstPolitician().toLowerCase())) {
                result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + 1 * weights.get(8));
                if (featureVector.getPercentageOfMostOccurringPlaceInPoliticians() > 10) {
                    result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + 1 * weights.get(9));
                } else {
                    result.put(dictionary.getCountry(), result.get(dictionary.getCountry()) + (1 * weights.get(9) * (featureVector.getPercentageOfMostOccurringPlaceInRegions()/10)));
                }
            }
        }

        return result;
    }
}
