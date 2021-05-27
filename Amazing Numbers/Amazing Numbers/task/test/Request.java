import util.LinesChecker;

import java.util.Random;
import java.util.regex.Pattern;

public class Request {
    public static final Pattern PARAMETERS_SEPARATOR = Pattern.compile("\\s+");
    public static final int MAX_COUNT = 20;
    private static final Random random = new Random();

    private final String request;
    private final long start;
    private final int count;
    private String[] properties;

    public Request(String request) {
        this.request = request;
        var data = PARAMETERS_SEPARATOR.split(request, 3);
        int type = data.length;
        properties = type == 3 ? PARAMETERS_SEPARATOR.split(data[2]) : new String[0];
        count = type > 1 ? Integer.parseInt(data[1]) : 0;
        start = Long.parseLong(data[0]);
    }

    public static Request random(Parameter params) {
        final var start = 1 + random.nextInt(Short.MAX_VALUE);
        if (params == Parameter.ONE) {
            return new Request(String.valueOf(start));
        }
        final var count = 1 + random.nextInt(MAX_COUNT);
        if (params == Parameter.TWO) {
            return new Request(start + " " + count);
        }
        final var index = random.nextInt(NumberProperty.values().length);
        final var property = NumberProperty.values()[index].name();
        final var request = start + " " + count + " " + property;
        return new Request(request);
    }

    public long getStart() {
        return start;
    }

    public int getCount() {
        return count;
    }

    public String[] getProperties() {
        return properties;
    }

    public LinesChecker getLinesChecker() {
        return new LinesChecker(count + 1);
    }

    @Override
    public String toString() {
        return request;
    }

    enum Parameter {ONE, TWO, THREE}
}
