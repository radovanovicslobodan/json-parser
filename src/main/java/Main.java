import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        JsonReader getLocalJsonFile = new JsonReader(new FileReader("src/main/resources/sample.json"));

        Type mapTokenType = new TypeToken<Map<String, Object>>() {
        }.getType();

        Map<String, Object> jsonMap = new Gson().fromJson(getLocalJsonFile, mapTokenType);

        Map<String, Object> parsedMap = JsonParser.parseMap(jsonMap);

        System.out.println(parsedMap);
    }
}
