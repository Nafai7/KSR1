package pl.ksr1;

import pl.ksr1.algorithms.KNN;
import pl.ksr1.datastructures.Article;
import pl.ksr1.datastructures.Dictionary;
import pl.ksr1.datastructures.FeatureVector;
import pl.ksr1.filereaders.JsonDictionaryReader;
import pl.ksr1.filereaders.SgmlArticleReader;
import pl.ksr1.stemmer.ListStemmer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static pl.ksr1.StaticVariables.*;

public class Main {

    public static void main(String[] args) {
        try {
            List<Dictionary> dictionaries = JsonDictionaryReader.readMultipleDictionaries(DICTIONARIES_FILES);
//            for (int i = 0; i < dictionaries.size(); i++) {
//                System.out.print("\n###########################################\n");
//                System.out.print(dictionaries.get(i).getDictionaryName());
//                System.out.print(dictionaries.get(i).getCountry());
//                System.out.print(dictionaries.get(i).getWords());
//            }

            List<Article> articles = SgmlArticleReader.readMultipleFiles(TEST_FILES);
//            ListStemmer.stemArticleList(articles);
            List<FeatureVector> featureVectors = new ArrayList<FeatureVector>();
            System.out.print(articles.size());
            for (int i = 0; i < articles.size(); i++) {
                System.out.print("\n###########################################\n");
//                System.out.print(articles.get(i).getPlace());
//                System.out.print(articles.get(i).getText());
//                System.out.print("\n");
                featureVectors.add(FeatureVector.extractFeatureVector(articles.get(i), dictionaries));
                System.out.print(String.valueOf(featureVectors.get(i).getTextLength()) + ", " +
                        featureVectors.get(i).getFirstRegion() + ", " +
                        featureVectors.get(i).getMostCommonRegion() + ", " +
                        featureVectors.get(i).getMostOccurringPlaceInRegions() + ", " +
                        featureVectors.get(i).getPercentageOfMostOccurringPlaceInRegions() + ", " +
                        featureVectors.get(i).getFirstCurrency() + ", " +
                        featureVectors.get(i).getMostCommonCurrency() + ", " +
                        featureVectors.get(i).getFirstCelebrity() + ", " +
                        featureVectors.get(i).getFirstPolitician() + ", " +
                        featureVectors.get(i).getPercentageOfMostOccurringPlaceInPoliticians());
                System.out.print("\n");
                Map<String, Float> classification = KNN.classify(articles.get(i), featureVectors.get(i), dictionaries, WEIGHTS);
                System.out.print(classification);
            }

        } catch (Exception ex) {
            System.err.print(ex);
        }
    }
}
