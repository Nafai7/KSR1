package pl.ksr1;

import pl.ksr1.algorithms.KNN;
import pl.ksr1.datastructures.Article;
import pl.ksr1.datastructures.Dictionary;
import pl.ksr1.datastructures.FeatureVector;
import pl.ksr1.filereaders.JsonDictionaryReader;
import pl.ksr1.filereaders.SgmlArticleReader;
import pl.ksr1.stemmer.ListStemmer;

import java.util.*;

import static pl.ksr1.StaticVariables.*;

public class Main {

    public static void main(String[] args) {
        try {
            // reading necessary stuff
            List<Dictionary> dictionaries = JsonDictionaryReader.readMultipleDictionaries(DICTIONARIES_FILES);
            List<Article> articles = SgmlArticleReader.readMultipleFiles(TEST_FILES);
//            ListStemmer.stemArticleList(articles);

            // getting user input
            Scanner in = new Scanner(System.in);
            int metricChoice = 0;
            while (metricChoice <= 0 || metricChoice >= 4) {
                System.out.print("[1] Euclidean\n[2] Street\n[3] Chebyshev\n");
                metricChoice = in.nextInt();
            }
            String metric = EUCLIDEAN;
            switch (metricChoice) {
                case 1:
                    metric = EUCLIDEAN;
                    break;
                case 2:
                    metric = STREET;
                    break;
                case 3:
                    metric = CHEBYSHEV;
                    break;
            }

            int k = 0;
            while (k <= 0) {
                System.out.print("Enter k value: ");
                k = in.nextInt();
            }
            List<String> proportions;
            int training = 0, testing = 0;
            while (!((training + testing) == 100)) {
                System.out.print("Enter training and testing proportions like \"50/50\": ");
                proportions = Arrays.stream(in.nextLine().split("/")).toList();
                if (proportions.size() == 2) {
                    training = Integer.valueOf(proportions.get(0));
                    testing = Integer.valueOf(proportions.get(1));
                }
            }

            // divide into training and test lists
            int trainingSize = (articles.size() * training) / 100;
            Random random = new Random(69);
            List<Article> trainingArticles = new ArrayList<>();

            for (int i = 0; i < trainingSize; i++) {
                int randomNumber = random.nextInt(articles.size());
                trainingArticles.add(articles.get(randomNumber));
                articles.remove(randomNumber);
            }

            // calculate vectors (training vectors know their place
            List<FeatureVector> trainingVectors = new ArrayList<FeatureVector>();
            List<FeatureVector> featureVectors = new ArrayList<FeatureVector>();
            System.out.print("\nUCZĄCE:\n");
            for (int i = 0; i < trainingArticles.size(); i++) {
                trainingVectors.add(FeatureVector.extractFeatureVector(trainingArticles.get(i), dictionaries));
                trainingVectors.get(i).setPlace(trainingArticles.get(i).getPlace());
                System.out.print("\n##############################\n");
                System.out.print(trainingVectors.get(i).getTextLength() + ", " +
                        trainingVectors.get(i).getFirstRegion() + ", " +
                        trainingVectors.get(i).getMostCommonRegion() + ", " +
                        trainingVectors.get(i).getMostOccurringPlaceInRegions() + ", " +
                        trainingVectors.get(i).getPercentageOfMostOccurringPlaceInRegions() + ", " +
                        trainingVectors.get(i).getFirstCurrency() + ", " +
                        trainingVectors.get(i).getMostCommonCurrency() + ", " +
                        trainingVectors.get(i).getFirstCelebrity() + ", " +
                        trainingVectors.get(i).getFirstPolitician() + ", " +
                        trainingVectors.get(i).getPercentageOfMostOccurringPlaceInPoliticians() + ", " +
                        trainingVectors.get(i).getPlace());
            }

            System.out.print("\nTESTOWE:\n");
            for (int i = 0; i < articles.size(); i++) {
                featureVectors.add(FeatureVector.extractFeatureVector(articles.get(i), dictionaries));
                System.out.print("\n##############################\n");
                System.out.print(featureVectors.get(i).getTextLength() + ", " +
                        featureVectors.get(i).getFirstRegion() + ", " +
                        featureVectors.get(i).getMostCommonRegion() + ", " +
                        featureVectors.get(i).getMostOccurringPlaceInRegions() + ", " +
                        featureVectors.get(i).getPercentageOfMostOccurringPlaceInRegions() + ", " +
                        featureVectors.get(i).getFirstCurrency() + ", " +
                        featureVectors.get(i).getMostCommonCurrency() + ", " +
                        featureVectors.get(i).getFirstCelebrity() + ", " +
                        featureVectors.get(i).getFirstPolitician() + ", " +
                        featureVectors.get(i).getPercentageOfMostOccurringPlaceInPoliticians());
            }

            // classify
            System.out.print("\nKlasyfikacja:\n");
            List<Map<String, Integer>> placesStats = new ArrayList<>();
            for (int i = 0; i < PLACES.size(); i++) {
                placesStats.add(new HashMap<>());
            }
            for (int i = 0; i < articles.size(); i++) {
                for (int j = 0; j < placesStats.size(); j++) {
                    if (PLACES.get(j).equals(articles.get(i).getPlace())) {
                        String classifiedPlace = KNN.classify(featureVectors.get(i), trainingVectors, metric, k);
                        System.out.print("\n");
                        System.out.print(classifiedPlace);
                        System.out.print("\n");
                        if (placesStats.get(j).containsKey(classifiedPlace)) {
                            placesStats.get(j).put(classifiedPlace, placesStats.get(j).get(classifiedPlace) + 1);
                        } else {
                            placesStats.get(j).put(classifiedPlace, 1);
                        }
                    }
                }
            }
//            System.out.print("\nKlasyfikacja:\n");
//            System.out.print(classification);
//
//            // printing results
//            List<Map<String, Integer>> placesStats = new ArrayList<>();
//            for (int i = 0; i < PLACES.size(); i++) {
//                placesStats.add(new HashMap<>());
//            }
//            for (Map.Entry<String, String> entry : classification.entrySet()) {
//                for (int i = 0; i < PLACES.size(); i++) {
//                    if (PLACES.get(i).equals(entry.getKey())) {
//                        if (placesStats.get(i).containsKey(entry.getValue())) {
//                            placesStats.get(i).put(entry.getValue(), placesStats.get(i).get(entry.getValue()) + 1);
//                        } else {
//                            placesStats.get(i).put(entry.getValue(), 1);
//                        }
//                    }
//                }
//            }

            System.out.print("\nSTATY:\n");
            for (int i = 0; i < placesStats.size(); i++) {
                System.out.print("\n###############################\n");
                System.out.print(PLACES.get(i));
                System.out.print("\n");
                System.out.print(placesStats.get(i));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
