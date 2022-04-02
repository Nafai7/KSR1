package pl.ksr1.datastructures;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static pl.ksr1.StaticVariables.DICTIONARIES;
import static pl.ksr1.StaticVariables.PLACES;

public class FeatureVector {

    @Getter private int articleID;
    @Getter private float textLength;
    @Getter private String firstRegion;
    @Getter private String mostCommonRegion;
    @Getter private String mostOccurringPlaceInRegions;
    @Getter private float percentageOfMostOccurringPlaceInRegions;
    @Getter private String firstCurrency;
    @Getter private String mostCommonCurrency;
    @Getter private String firstCelebrity;
    @Getter private String firstPolitician;
    @Getter private float percentageOfMostOccurringPlaceInPoliticians;
    @Getter @Setter private String place;

    @Getter private List<Boolean> isUsed;

    private FeatureVector(int articleID,
                          float textLength,
                          String firstRegion,
                          String mostCommonRegion,
                          String mostOccurringPlaceInRegions,
                          float percentageOfMostOccurringPlaceInRegions,
                          String firstCurrency,
                          String mostCommonCurrency,
                          String firstCelebrity,
                          String firstPolitician,
                          float percentageOfMostOccurringPlaceInPoliticians,
                          List<Boolean> isUsed) {
        this.articleID = articleID;
        this.textLength = textLength;
        this.firstRegion = firstRegion;
        this.mostCommonRegion = mostCommonRegion;
        this.mostOccurringPlaceInRegions = mostOccurringPlaceInRegions;
        this.percentageOfMostOccurringPlaceInRegions = percentageOfMostOccurringPlaceInRegions;
        this.firstCurrency = firstCurrency;
        this.mostCommonCurrency = mostCommonCurrency;
        this.firstCelebrity = firstCelebrity;
        this.firstPolitician = firstPolitician;
        this.percentageOfMostOccurringPlaceInPoliticians = percentageOfMostOccurringPlaceInPoliticians;
        this.place = "";
        this.isUsed = isUsed;
    }

    public static FeatureVector extractFeatureVector(Article article, List<Dictionary> dictionaries, List<Boolean> uses) {
        float length = article.getText().size()/(float)1000.0;

        //####### REGIONS
        boolean flag = false;
        String region1 = "";
        Map<String, Integer> regionMap = new HashMap<>();
        Map<String, Integer> placeMap = new HashMap<>();
        for (int i = 0; i < article.getText().size(); i++) {
            for (int j = 0; j < PLACES.size(); j++) {
                for (int k = 0; k < dictionaries.get(j).getWords().size(); k++) {
                    List<String> region = Arrays.stream(dictionaries.get(j).getWords().get(k).toLowerCase().split(" ")).toList(); //słowa w słowniku są po spacjach więc trzeba sprawdzić czy następujące słowa tez pasują
                    boolean wordFlag = false;
                    for (int l = 0; l < region.size(); l++) {
                        if (i + l < article.getText().size()) {
                            if (!region.get(l).equals(article.getText().get(i + l).toLowerCase())) {
                                wordFlag = true;
                            }
                        } else {
                            wordFlag = true;
                        }
                    }

                    if (!wordFlag) {
                        i += region.size() - 1;
                        String word = dictionaries.get(j).getWords().get(k);
                        if (!flag) {
                            region1 = dictionaries.get(j).getCountry();
                            flag = true;
                        }
                        if (regionMap.containsKey(word)) {
                            regionMap.put(word, regionMap.get(word) + 1);
                        } else {
                            regionMap.put(word, 1);
                        }

                        String place = dictionaries.get(j).getCountry();
                        if (placeMap.containsKey(place)) {
                            placeMap.put(place, placeMap.get(place) + region.size());
                        } else {
                            placeMap.put(place, region.size());
                        }
                    }
                }
            }
        }

        String region2 = "";
        int maxValue = 0;
        for (Map.Entry<String, Integer> entry : regionMap.entrySet()) {
            if (maxValue < entry.getValue()) {
                maxValue = entry.getValue();
                region2 = entry.getKey();
            }
        }
        for (int i = 0; i < PLACES.size(); i++) {
            if (dictionaries.get(i).getWords().contains(region2)) {
                region2 = dictionaries.get(i).getCountry();
                break;
            }
        }

        String region3 = "";
        maxValue = 0;
        for (Map.Entry<String, Integer> entry : placeMap.entrySet()) {
            if (maxValue < entry.getValue()) {
                maxValue = entry.getValue();
                region3 = entry.getKey();
            }
        }
        float region4 = Math.round((((float)maxValue/article.getText().size()) * 100) * 100.0) / (float)100; //kocham jave

        //####### CURRENCY
        flag = false;
        String currency1 = "";
        Map<String, Integer> currencyMap = new HashMap<>();
        for (int i = 0; i < article.getText().size(); i++) {
            for (int j = PLACES.size(); j < PLACES.size() * 2; j++) {
                for (int k = 0; k < dictionaries.get(j).getWords().size(); k++) {
                    List<String> currency = Arrays.stream(dictionaries.get(j).getWords().get(k).toLowerCase().split(" ")).toList();
                    boolean wordFlag = false;
                    for (int l = 0; l < currency.size(); l++) {
                        if (i + l < article.getText().size()) {
                            if (!currency.get(l).equals(article.getText().get(i + l).toLowerCase())) {
                                wordFlag = true;
                            }
                        } else {
                            wordFlag = true;
                        }
                    }

                    if (!wordFlag) {
                        i += currency.size() - 1;
                        String word = dictionaries.get(j).getWords().get(k);
                        if (!flag) {
                            currency1 = dictionaries.get(j).getCountry();
                            flag = true;
                        }
                        if (currencyMap.containsKey(word)) {
                            currencyMap.put(word, currencyMap.get(word) + currency.size());
                        } else {
                            currencyMap.put(word, currency.size());
                        }
                    }
                }
            }
        }

        String currency2 = "";
        maxValue = 0;
        for (Map.Entry<String, Integer> entry : currencyMap.entrySet()) {
            if (maxValue < entry.getValue()) {
                maxValue = entry.getValue();
                currency2 = entry.getKey();
            }
        }
        for (int i = PLACES.size(); i < PLACES.size() * 2; i++) {
            if (dictionaries.get(i).getWords().contains(currency2)) {
                currency2 = dictionaries.get(i).getCountry();
                break;
            }
        }

        //####### CELEBRITIES
        String celebrity1 = "";
        celebrityLoop:
        for (int i = 0; i < article.getText().size(); i++) {
            for (int j = PLACES.size() * 2; j < PLACES.size() * 3; j++) {
                for (int k = 0; k < dictionaries.get(j).getWords().size(); k++) {
                    List<String> celebrity = Arrays.stream(dictionaries.get(j).getWords().get(k).toLowerCase().split(" ")).toList();
                    boolean wordFlag = false;
                    for (int l = 0; l < celebrity.size(); l++) {
                        if (i + l < article.getText().size()) {
                            if (!celebrity.get(l).equals(article.getText().get(i + l).toLowerCase())) {
                                wordFlag = true;
                            }
                        } else {
                            wordFlag = true;
                        }
                    }

                    if (!wordFlag) {
                        String word = dictionaries.get(j).getWords().get(k);
                        celebrity1 = dictionaries.get(j).getCountry();
                        break celebrityLoop;
                    }
                }
            }
        }

        //####### POLITICIANS
        flag = false;
        String politician1 = "";
        placeMap = new HashMap<>();
        for (int i = 0; i < article.getText().size(); i++) {
            for (int j = PLACES.size() * 3; j < PLACES.size() * 4; j++) {
                for (int k = 0; k < dictionaries.get(j).getWords().size(); k++) {
                    List<String> politician = Arrays.stream(dictionaries.get(j).getWords().get(k).toLowerCase().split(" ")).toList();
                    boolean wordFlag = false;
                    for (int l = 0; l < politician.size(); l++) {
                        if (i + l < article.getText().size()) {
                            if (!politician.get(l).equals(article.getText().get(i + l).toLowerCase())) {
                                wordFlag = true;
                            }
                        } else {
                            wordFlag = true;
                        }
                    }

                    if (!wordFlag) {
                        String word = dictionaries.get(j).getWords().get(k);
                        if (!flag) {
                            politician1 = dictionaries.get(j).getCountry();
                            flag = true;
                        }

                        String place = dictionaries.get(j).getCountry();
                        if (placeMap.containsKey(place)) {
                            placeMap.put(place, placeMap.get(place) + politician.size());
                        } else {
                            placeMap.put(place, politician.size());
                        }
                    }
                }
            }
        }

        maxValue = 0;
        for (Map.Entry<String, Integer> entry : placeMap.entrySet()) {
            if (maxValue < entry.getValue()) {
                maxValue = entry.getValue();
            }
        }
        float politician2 = Math.round((((float)maxValue/article.getText().size()) * 100) * 100.0) / (float)100; //kocham jave

        return new FeatureVector(article.getID(), length, region1, region2, region3, region4, currency1, currency2, celebrity1, politician1, politician2, uses);
    }

}
