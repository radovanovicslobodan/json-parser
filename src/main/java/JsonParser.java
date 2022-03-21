import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class JsonParser {

    public static Map<String, Object> parseMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                parseMap((Map<String, Object>) entry.getValue());
            } else if (entry.getValue().toString().startsWith("$")) {
                map.replace(entry.getKey(), replaceValue((String) entry.getValue()));
            }
        }
        return map;
    }

    public static String replaceValue(String value) {

        Faker faker = new Faker();
        String newValue;

        switch (value) {
            case "$RANDOM_NAME":
                newValue = faker.name().firstName();
                break;

            case "$COUNTRY":
                newValue = faker.address().country();
                break;

            case "$EMAIL":
                newValue = faker.internet().emailAddress();
                break;

            case "$TODAY":
                LocalDate todayDate = LocalDate.now();
                newValue = todayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;

            default:
                throw new IllegalArgumentException("Invalid command in JSON file.");
        }
        return newValue;
    }
}
