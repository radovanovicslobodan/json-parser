import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.skyah.comparator.CompareMode;
import ro.skyah.comparator.DefaultJsonComparator;
import ro.skyah.comparator.JsonComparator;
import ro.skyah.comparator.matcher.JsonMatcher;
import ro.skyah.comparator.matcher.MatcherException;
import ro.skyah.util.MessageUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import static ro.skyah.comparator.JSONCompare.prettyPrint;

public class CustomJSONCompare {

    private static Logger logger = LoggerFactory.getLogger(CustomJSONCompare.class);

    private static final ObjectMapper MAPPER;

    public static boolean assertEquals(String expected, String actual, CompareMode... compareModes) {
        return assertEquals((String) null, (String) expected, (String) actual, compareModes);

    }

    public static boolean assertEquals(String message, String expected, String actual, CompareMode... compareModes) {
        JsonNode expectedJson = getJson(expected);
        JsonNode actualJson = getJson(actual);
        return assertEquals(message, (JsonNode) expectedJson, (JsonNode) actualJson, (JsonComparator) null, compareModes);
    }

    private static JsonNode getJson(String json) {
        JsonNode jsonNode = null;

        try {
            jsonNode = MAPPER.readTree(json);
        } catch (IOException e) {
            Assertions.fail(String.format("Not a JSON:\n%s", MessageUtil.cropL(json)));
        }

        return jsonNode;
    }

    public static boolean assertEquals(String message, JsonNode expected, JsonNode actual, JsonComparator comparator, CompareMode... compareModes) {
        try {
            (new JsonMatcher(expected, actual, (JsonComparator) (comparator == null ? new DefaultJsonComparator() : comparator), new HashSet(Arrays.asList(compareModes)))).match();
            return true;
        } catch (MatcherException var7) {
            String defaultMessage = String.format("%s\nExpected:\n%s\nBut got:\n%s", var7.getMessage(), prettyPrint(expected), MessageUtil.cropL(prettyPrint(actual)));
            if (comparator == null || comparator.getClass().equals(DefaultJsonComparator.class)) {
                defaultMessage = defaultMessage + "\n\nHint: By default, json matching uses regular expressions.\nIf expected json contains unintentional regexes, then quote them between \\Q and \\E delimiters or use a custom comparator.\n";
            }

            if (message == null) {
                logger.error(defaultMessage);
            } else {
                logger.error(defaultMessage + "\n" + message);
            }
            return false;
        }

    }

    static {
        MAPPER = (new ObjectMapper()).enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
    }
}
