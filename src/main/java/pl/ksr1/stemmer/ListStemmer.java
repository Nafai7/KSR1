package pl.ksr1.stemmer;

import opennlp.tools.stemmer.PorterStemmer;
import pl.ksr1.datastructures.Article;

import java.util.ArrayList;
import java.util.List;

public class ListStemmer {

    public static List<String> stemStringList(List<String> text) {
        List<String> result = new ArrayList<String>();
        PorterStemmer porterStemmer = new PorterStemmer();
        for(int i = 0; i < text.size(); i++) {
            result.add(porterStemmer.stem(text.get(i)));
        }

        return result;
    }

    public static void stemArticleList(List<Article> articles) {
        for (int i = 0; i < articles.size(); i++) {
            articles.get(i).setText(stemStringList(articles.get(i).getText()));
        }
    }
}
