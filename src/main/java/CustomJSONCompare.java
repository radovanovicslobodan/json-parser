import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.DefaultJsonComparator;
import ro.skyah.comparator.JSONCompare;
import ro.skyah.comparator.JsonComparator;
import ro.skyah.comparator.matcher.JsonMatcher;
import ro.skyah.comparator.matcher.MatcherException;
import ro.skyah.util.MessageUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class CustomJSONCompare extends JSONCompare {

    private static final ObjectMapper MAPPER;

    public static void assertEquals(String expected, String actual, CompareMode... compareModes) {
        assertEquals((String)null, (String)expected, (String)actual, compareModes);
    }

    public static void assertEquals(String message, String expected, String actual, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        assertEquals(message, (JsonNode)expectedJson, (JsonNode)actualJson, (JsonComparator)null, compareModes);
    }

    private static JsonNode getJson(String json) {
        JsonNode jsonNode = null;

        try {
            jsonNode = MAPPER.readTree(json);
        } catch (IOException var3) {
            Assertions.fail(String.format("Not a JSON:\n%s", MessageUtil.cropL(json)));
        }

        return jsonNode;
    }

    public static void assertEquals(String message, JsonNode expected, JsonNode actual, JsonComparator comparator, CompareMode... compareModes) {
        try {
            (new JsonMatcher(expected, actual, (JsonComparator)(comparator == null ? new DefaultJsonComparator() : comparator), new HashSet(Arrays.asList(compareModes)))).match();
        } catch (MatcherException var7) {
            String defaultMessage = String.format("%s\nExpected:\n%s\nBut got:\n%s", var7.getMessage(), prettyPrint(expected), MessageUtil.cropL(prettyPrint(actual)));
            if (comparator == null || comparator.getClass().equals(DefaultJsonComparator.class)) {
                defaultMessage = defaultMessage + "\n\nHint: By default, json matching uses regular expressions.\nIf expected json contains unintentional regexes, then quote them between \\Q and \\E delimiters or use a custom comparator.\n";
            }

            Assertions.fail(message == null ? defaultMessage : defaultMessage + "\n" + message);
        }

    }

    static {
        MAPPER = (new ObjectMapper()).enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
    }
}
