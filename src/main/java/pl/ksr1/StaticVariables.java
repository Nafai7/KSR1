package pl.ksr1;

import java.util.Arrays;
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
}
