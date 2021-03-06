package pl.ksr1;

import pl.ksr1.algorithms.KNN;
import pl.ksr1.datastructures.Article;
import pl.ksr1.datastructures.Dictionary;
import pl.ksr1.datastructures.FeatureVector;
import pl.ksr1.filereaders.JsonReader;
import pl.ksr1.filereaders.SgmArticleReader;
import pl.ksr1.measurements.ClassificationMeasurements;
import pl.ksr1.stemmer.ListStemmer;

import java.io.FileWriter;
import java.util.*;

import static pl.ksr1.StaticVariables.*;

public class Main {

    public static void main(String[] args) {
        try {
            // reading necessary stuff
            List<Dictionary> dictionaries = JsonReader.readMultipleDictionaries(DICTIONARIES_FILES);
            List<Article> articles = SgmArticleReader.readMultipleFiles(ARTICLE_FILES);
            List<String> stopWords = JsonReader.readStopWords(STOP_WORDS_FILE);

            for (int i = 0; i < articles.size(); i++) {
                for (int j = 0; j < articles.get(i).getText().size(); j++) {
                    if (stopWords.contains(articles.get(i).getText().get(j))) {
                        articles.get(i).getText().remove(j);
                    }
                }
            }

            ListStemmer.stemArticleList(articles);
            ListStemmer.stemDictionaryList(dictionaries);

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
            in.nextLine();
            while (!((training + testing) == 100)) {
                System.out.print("Enter training and testing proportions like \"50/50\": ");
                proportions = Arrays.stream(in.nextLine().split("/")).toList();
                if (proportions.size() == 2) {
                    training = Integer.valueOf(proportions.get(0));
                    testing = Integer.valueOf(proportions.get(1));
                }
            }
            List<Boolean> isUsed = new ArrayList<>();
            System.out.println("Choose features(t/n);");
            String tmp;
            for (int i = 0; i < 10; i++) {
                System.out.print(FEATURES.get(i));
                tmp = in.next();
                if (tmp.equals("t")) {
                    isUsed.add(true);
                } else {
                    isUsed.add(false);
                }
            }
            System.out.println("""


                    #####################################################
                    #                                                   #
                    #     The classification process is in progress     #
                    #               Have a nice waiting!                #
                    #                                                   #
                    #                   ???    ???    ???                     #
                    #                    ???    ???    ???                    #
                    #                   ???    ???    ???                     #
                    #                 ?????????????????????????????????????????????                   #
                    #                 ???             ?????????                 #
                    #                 ???             ???  ???                #
                    #                 ???             ?????????                 #
                    #                  ???           ???                    #
                    #                   ?????????????????????????????????                     #
                    #                                                   #
                    #####################################################""");


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
//            System.out.print("\nUCZ??CE:\n");
            for (int i = 0; i < trainingArticles.size(); i++) {
                trainingVectors.add(FeatureVector.extractFeatureVector(trainingArticles.get(i), dictionaries, isUsed));
                trainingVectors.get(i).setPlace(trainingArticles.get(i).getPlace());
//                System.out.print("\n##############################\n");
//                System.out.print(trainingVectors.get(i).getTextLength() + ", " +
//                        trainingVectors.get(i).getFirstRegion() + ", " +
//                        trainingVectors.get(i).getMostCommonRegion() + ", " +
//                        trainingVectors.get(i).getMostOccurringPlaceInRegions() + ", " +
//                        trainingVectors.get(i).getPercentageOfMostOccurringPlaceInRegions() + ", " +
//                        trainingVectors.get(i).getFirstCurrency() + ", " +
//                        trainingVectors.get(i).getMostCommonCurrency() + ", " +
//                        trainingVectors.get(i).getFirstCelebrity() + ", " +
//                        trainingVectors.get(i).getFirstPolitician() + ", " +
//                        trainingVectors.get(i).getPercentageOfMostOccurringPlaceInPoliticians() + ", " +
//                        trainingVectors.get(i).getPlace());
            }

//            System.out.print("\nTESTOWE:\n");
            for (int i = 0; i < articles.size(); i++) {
                featureVectors.add(FeatureVector.extractFeatureVector(articles.get(i), dictionaries, isUsed));
//                System.out.print("\n##############################\n");
//                System.out.print(featureVectors.get(i).getTextLength() + ", " +
//                        featureVectors.get(i).getFirstRegion() + ", " +
//                        featureVectors.get(i).getMostCommonRegion() + ", " +
//                        featureVectors.get(i).getMostOccurringPlaceInRegions() + ", " +
//                        featureVectors.get(i).getPercentageOfMostOccurringPlaceInRegions() + ", " +
//                        featureVectors.get(i).getFirstCurrency() + ", " +
//                        featureVectors.get(i).getMostCommonCurrency() + ", " +
//                        featureVectors.get(i).getFirstCelebrity() + ", " +
//                        featureVectors.get(i).getFirstPolitician() + ", " +
//                        featureVectors.get(i).getPercentageOfMostOccurringPlaceInPoliticians());
            }

            // classify
//            System.out.print("\nKlasyfikacja:\n");
            List<Map<String, Integer>> placesStats = new ArrayList<>();
            for (int i = 0; i < PLACES.size(); i++) {
                placesStats.add(new HashMap<>());
            }
            for (int i = 0; i < articles.size(); i++) {
                for (int j = 0; j < placesStats.size(); j++) {
                    if (PLACES.get(j).equals(articles.get(i).getPlace())) {
                        String classifiedPlace = KNN.classify(featureVectors.get(i), trainingVectors, metric, k);
//                        System.out.print("\n");
//                        System.out.print(classifiedPlace);
//                        System.out.print("\n");
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

            Map<String, Integer> actualPlaces = new HashMap<>();
            for (int i = 0; i < PLACES.size(); i++) {
                actualPlaces.put(PLACES.get(i), 0);
            }
            for (int i = 0; i < articles.size(); i++) {
                actualPlaces.put(articles.get(i).getPlace(), actualPlaces.get(articles.get(i).getPlace()) + 1);
            }

            List<List<Integer>> truesAndFalses = new ArrayList<List<Integer>>(); // true, falsepositive, falsenegative
            for (int i = 0; i < PLACES.size(); i++) {
                truesAndFalses.add(new ArrayList<Integer>());
                int trues = 0;
                int falseNegative = 0;
                int falsePositive = 0;

                for (int j = 0; j < placesStats.size(); j++) {
                    if (i != j) {
                        for (Map.Entry<String, Integer> entry : placesStats.get(j).entrySet()) {
                            if (entry.getKey().equals(PLACES.get(i))) {
                                falseNegative += entry.getValue();
                            }
                        }
                    } else {
                        for (Map.Entry<String, Integer> entry : placesStats.get(j).entrySet()) {
                            if (entry.getKey().equals(PLACES.get(i))) {
                                trues += entry.getValue();
                            } else {
                                falsePositive += entry.getValue();
                            }
                        }
                    }
                }
                truesAndFalses.get(i).add(trues);
                truesAndFalses.get(i).add(falsePositive);
                truesAndFalses.get(i).add(falseNegative);
//            System.out.print("\n");
//            System.out.print(truesAndFalses.get(i));
            }

            System.out.print("\n");
            System.out.print("Miary:\n");
            List<Float> measurments = ClassificationMeasurements.calculateClassifcationMeasurements(truesAndFalses, actualPlaces);

//            System.out.print("\n");
//            System.out.print(measurments);

            StringBuilder output = new StringBuilder();
            System.out.print("\n");
            output.append("Cechy: [" + isUsed + "]\n\n");
            output.append("STATYSTYKI:\n");
            output.append("Accuracy: " + (float)(Math.round(measurments.get(0)*1000))/1000 + "\n");
            output.append("Precision: " + (float)(Math.round(measurments.get(1)*1000))/1000 + "\n");
            output.append("Recall: " + (float)(Math.round(measurments.get(2)*1000))/1000 + "\n");
            output.append("F1: " + (float)(Math.round(measurments.get(3)*1000))/1000 + "\n");
            for (int i = 0; i < placesStats.size(); i++) {
                output.append("\n###############################\n");
                output.append(PLACES.get(i) + " " + actualPlaces.get(PLACES.get(i)));
                output.append("\n[");
                List<Integer> c = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : placesStats.get(i).entrySet()) {
                    output.append(entry.getKey() + " " + entry.getValue() + ",");
                    c.add(entry.getValue());
                }
                output.append("]\n");
                if (c.size() == 6) {
                    output.append(c.get(5) + "\t" + c.get(0) + "\t" + c.get(4) + "\t" + c.get(3) + "\t" + c.get(1) + "\t" + c.get(2) + "\n");
                }
                output.append("T: " + truesAndFalses.get(i).get(0) + ", FN: " + truesAndFalses.get(i).get(1) + ", FP: " + truesAndFalses.get(i).get(2) + "\n");
                output.append("Precision: " + (float)(Math.round(measurments.get(4 + i * 3)*1000))/1000 + "\n");
                output.append("Recall: " + (float)(Math.round(measurments.get(5 + i * 3)*1000))/1000 + "\n");
                output.append("F1: " + (float)(Math.round(measurments.get(6 + i * 3)*1000))/1000 + "\n");
            }
            System.out.print(output.toString());
            String fileName = "Results_" + metric + "_" + k + "_" + training + "_" + testing + "_";
            for (int i = 0; i < isUsed.size(); i++) {
                if (isUsed.get(i)) {
                    fileName += "t";
                } else {
                    fileName += "f";
                }
            }
            fileName += ".txt";
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(output.toString());
            myWriter.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
