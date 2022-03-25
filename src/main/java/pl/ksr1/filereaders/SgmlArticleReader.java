package pl.ksr1.filereaders;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import pl.ksr1.datastructures.Article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pl.ksr1.StaticVariables.*;

public class SgmlArticleReader {

    public static int ID = 0;

    public static List<Article> readMultipleFiles(List<String> files) throws IOException {
        List<Article> result = new ArrayList<Article>();

        for (int i = 0; i < files.size(); i++) {
            Jsoup.parse(Jsoup.class.getResourceAsStream(files.get(i)), "UTF-8", "")
                    .select("REUTERS")
                    .forEach( (article) -> {
                        List<Element> places = article.getElementsByTag("PLACES").first().getElementsByTag("d").stream().toList();
                        if (places.size() == 1 && PLACES.contains(places.get(0).text())) {
                            Element lastChild = article.child(article.childrenSize() - 1);
                            result.add(new Article(places.get(0).text(), Arrays.stream(lastChild.text().substring(lastChild.text().indexOf(" - ") + 3).split(" ")).toList(), ID));
                            ID++;
                        }
                    });
        }

        return result;
    }
}
