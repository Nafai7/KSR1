package pl.ksr1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StaticVariables {
    public static List<String> TEST_FILES = Arrays.stream((
            "/example_articles.txt").split(",")).toList();

    public static List<String> ARTICLE_FILES = Arrays.stream((
            "/articles/reut2-000.sgm," +
                    "/articles/reut2-001.sgm," +
                    "/articles/reut2-002.sgm," +
                    "/articles/reut2-003.sgm," +
                    "/articles/reut2-004.sgm," +
                    "/articles/reut2-005.sgm," +
                    "/articles/reut2-006.sgm," +
                    "/articles/reut2-007.sgm," +
                    "/articles/reut2-008.sgm," +
                    "/articles/reut2-009.sgm," +
                    "/articles/reut2-010.sgm," +
                    "/articles/reut2-011.sgm," +
                    "/articles/reut2-012.sgm," +
                    "/articles/reut2-013.sgm," +
                    "/articles/reut2-014.sgm," +
                    "/articles/reut2-015.sgm," +
                    "/articles/reut2-016.sgm," +
                    "/articles/reut2-017.sgm," +
                    "/articles/reut2-018.sgm," +
                    "/articles/reut2-019.sgm," +
                    "/articles/reut2-020.sgm," +
                    "/articles/reut2-021.sgm,").split(",")).toList();

    public static List<String> PLACES = Arrays.stream((
            "west-germany," +
            "usa," +
            "france," +
            "uk," +
            "canada," +
            "japan,").split(",")).toList();

    public static List<String> DICTIONARIES_FILES = Arrays.stream((
            "src/main/resources/dictionaries/regions.json," +
            "src/main/resources/dictionaries/currency.json," +
            "src/main/resources/dictionaries/celebrities.json," +
            "src/main/resources/dictionaries/politicians.json,").split(",")).toList();

    public static List<String> DICTIONARIES = Arrays.stream((
            "regions," +
            "currency," +
            "celebrities," +
            "politicians,").split(",")).toList();

    public static final String STOP_WORDS_FILE = "src/main/resources/stop_words.json";

    public static final String EUCLIDEAN = "euclidean";
    public static final String STREET = "street";
    public static final String CHEBYSHEV = "chebyshev";

    public static List<String> FEATURES = Arrays.stream((
            "text Length: ," +
            "first Region: ," +
            "most Common Region: ," +
            "most Occurring Place In Regions: ," +
            "percentage Of Most Occurring Place In Regions: ," +
            "first Currency: ," +
            "most Common Currency: ," +
            "first Celebrity: ," +
            "first Politician: ," +
            "percentage Of Most Occurring Place In Politicians: ,"
            ).split(",")).toList();

//    public static List<Float> WEIGHTS = new ArrayList<Float>(
//            List.of((float)0.1,
//                    (float)0.1,
//                    (float)0.1,
//                    (float)0.1,
//                    (float)0.1,
//                    (float)0.1,
//                    (float)0.1,
//                    (float)0.1,
//                    (float)0.1,
//                    (float)0.1));
}
