package pl.ksr1.filereaders;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.ksr1.datastructures.Dictionary;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {

    public static List<Dictionary> readMultipleDictionaries(List<String> files) throws IOException, ParseException {
        List<Dictionary> result = new ArrayList<Dictionary>();
        for (int i = 0; i < files.size(); i++) {
            FileReader reader = new FileReader(files.get(i));
            Object obj = new JSONParser().parse(reader);
            JSONObject json = (JSONObject) obj;
            String dictionaryName = json.get("dictionary").toString();
            JSONArray data = (JSONArray) json.get("data");
            for (int j = 0; j < data.size(); j++) {
                JSONObject jsonObject = (JSONObject) data.get(j);
                JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                List<String> words = new ArrayList<String>();
                for (int k = 0; k < jsonArray.size(); k++) {
                    words.add(jsonArray.get(k).toString());
                }
                result.add(new Dictionary(dictionaryName, jsonObject.get("country").toString(), words));
            }
        }

        return result;
    }

    public static List<String> readStopWords(String file) throws IOException, ParseException {
        List<String> result = new ArrayList<>();
        FileReader reader = new FileReader(file);
        Object obj = new JSONParser().parse(reader);
        JSONArray jsonArray = (JSONArray) obj;
        for (int i = 0; i < jsonArray.size(); i++) {
            result.add(jsonArray.get(i).toString());
        }

        return result;
    }
}
