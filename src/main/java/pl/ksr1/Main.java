package pl.ksr1;

import pl.ksr1.datastructures.Article;
import pl.ksr1.filereaders.SgmlArticleReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
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

    public static void main(String[] args) {
        try {
            List<Article> articles = SgmlArticleReader.readMultipleFiles(TEST_FILES);
            for (int i = 0; i < articles.size(); i++) {
                System.out.print("\n###########################################\n");
                System.out.print(articles.get(i).getPlace());
                System.out.print(articles.get(i).getText());
            }
        } catch (Exception ex) {
            System.err.print(ex);
        }
    }
}
