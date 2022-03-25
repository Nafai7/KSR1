package pl.ksr1.datastructures;

import lombok.Setter;
import lombok.Getter;

import java.util.List;

public class Dictionary {
    @Getter @Setter private String country;
    @Getter @Setter private List<String> words;

    public Dictionary(String country, List<String> words) {
        this.country = country;
        this.words = words;
    }
}
