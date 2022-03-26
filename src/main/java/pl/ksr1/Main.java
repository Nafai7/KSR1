package pl.ksr1;

import pl.ksr1.datastructures.Article;
import pl.ksr1.datastructures.Dictionary;
import pl.ksr1.datastructures.FeatureVector;
import pl.ksr1.filereaders.JsonDictionaryReader;
import pl.ksr1.filereaders.SgmlArticleReader;
import pl.ksr1.stemmer.ListStemmer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            System.out.print(articles.size());
            for (int i = 0; i < articles.size(); i++) {
                System.out.print("\n###########################################\n");
                System.out.print(articles.get(i).getPlace());
                System.out.print(articles.get(i).getText());
                System.out.print("\n");
                FeatureVector vector = FeatureVector.extractFeatureVector(articles.get(i), dictionaries);
                System.out.print(String.valueOf(vector.getTextLength()) + ", " +
                        vector.getFirstRegion() + ", " +
                        vector.getMostCommonRegion() + ", " +
                        vector.getMostOccurringPlaceInRegions() + ", " +
                        vector.getPercentageOfMostOccurringPlaceInRegions() + ", " +
                        vector.getFirstCurrency() + ", " +
                        vector.getMostCommonCurrency() + ", " +
                        vector.getFirstCelebrity() + ", " +
                        vector.getFirstPolitician() + ", " +
                        vector.getPercentageOfMostOccurringPlaceInPoliticians());
            }
        } catch (Exception ex) {
            System.err.print(ex);
        }
    }
}
