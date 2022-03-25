package pl.ksr1.datastructures;

import lombok.Setter;
import lombok.Getter;

import java.util.List;

public class Article {

    @Getter private int ID;
    @Getter @Setter private String place;
    @Getter @Setter private List<String> text;

    public Article(String place, List<String> text, int ID) {
        this.place = place;
        this.text = text;
        this.ID = ID;
    }
}
