package pl.ksr1.datastructures;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Dictionary {
    @Getter private String dictionaryName;
    @Getter private String country;
    @Getter @Setter private List<String> words;

    public Dictionary(String dictionaryName, String country, List<String> words) {
        this.dictionaryName = dictionaryName;
        this.country = country;
        this.words = words;
    }
}
