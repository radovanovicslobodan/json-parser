import ro.skyah.comparator.JSONCompare;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonCompare {

    public static void main(String[] args) throws IOException {

        String request = Files.readString(Path.of("src/main/resources/request.json"), StandardCharsets.US_ASCII);
        String response = Files.readString(Path.of("src/main/resources/response.json"), StandardCharsets.US_ASCII);
        String dummy = Files.readString(Path.of("src/main/resources/dummy.json"), StandardCharsets.US_ASCII);

        JSONCompare.assertEquals(request, response);
        System.out.println("JSON files are equal.");
    }
}
