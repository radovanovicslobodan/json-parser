import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {

    public static String parseJson(String json) {
        Pattern pat = Pattern.compile("%(.*?)%");
        Matcher m = pat.matcher(json);

        return m.replaceAll((match) -> {
            String commandToReplace = match.group(1);
            return replaceValue(commandToReplace);
        });
    }

    public static String replaceValue(String value) {

        Faker faker = new Faker();
        String newValue;

        switch (value) {
            case "RANDOM_NAME":
                newValue = faker.name().firstName();
                break;

            case "COUNTRY":
                newValue = faker.address().country();
                break;

            case "EMAIL":
                newValue = faker.internet().emailAddress();
                break;

            case "TODAY":
                LocalDate todayDate = LocalDate.now();
                newValue = todayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;

            case "WEEKBEFORE":
                LocalDate weekBeforeDate = LocalDate.now().minusWeeks(1);
                newValue = weekBeforeDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;

            case "WEEKAFTER":
                LocalDate weekAfterDate = LocalDate.now().plusWeeks(1);
                newValue = weekAfterDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;

            default:
                throw new IllegalArgumentException("Invalid command in JSON file.");
        }
        return newValue;
    }
}
