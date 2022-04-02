package pl.ksr1.stemmer;

import opennlp.tools.stemmer.PorterStemmer;
import pl.ksr1.datastructures.Article;
import pl.ksr1.datastructures.Dictionary;

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

    public static void stemDictionaryList(List<Dictionary> dictionaries) {
        for (int i = 0; i < dictionaries.size(); i++) {
            dictionaries.get(i).setWords(stemStringList(dictionaries.get(i).getWords()));
//            System.out.print("\n" + dictionaries.get(i).getDictionaryName() + "\n");
//            for (int j = 0; j < dictionaries.get(i).getWords().size(); j++) {
//                System.out.print(dictionaries.get(i).getWords().get(j) + ", ");
//            }
        }
    }
}
