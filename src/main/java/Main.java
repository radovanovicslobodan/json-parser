import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {

        String jsonFile = Files.readString(Path.of("src/main/resources/sample.json"), StandardCharsets.US_ASCII);
        String parsedJsonFile = JsonParser.parseJson(jsonFile);
        System.out.println(parsedJsonFile);
    }
}
