package bphc.cse.dragon;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public final class TAC {
    private static final Pattern PATTERN_MULTI_LF =
            Pattern.compile("(?m)(^\\s+|[\\t\\f ](?=[\\t\\f ])|[\\t\\f ]$|\\s+\\z)");
    private static final Pattern PATTERN_LF_FOR_FUNCS = Pattern.compile("def");
    private static final Pattern PATTERN_LF_AT_START = Pattern.compile("^\\n");
    private static final Pattern LF = Pattern.compile("\\n");

    public static void main(String... args) throws IOException {
        final OogwayParser oogwayParser = createOogwayParser(readFile(args[0]));
        final OogwayParser.StartContext start = oogwayParser.start();
        System.out.println(getTac(start));
    }

    public static String getTac(OogwayParser.StartContext start) {
        return LF.matcher(PATTERN_LF_AT_START.matcher(
                PATTERN_LF_FOR_FUNCS.matcher(
                        PATTERN_MULTI_LF.matcher(
                                start.tac
                        ).replaceAll("")
                ).replaceAll("\ndef")
        ).replaceAll(""))
                .replaceAll(System.lineSeparator())
                + System.lineSeparator();
    }

    public static OogwayParser createOogwayParser(String input) {
        final CodePointCharStream in = CharStreams.fromString(input);

        final OogwayLexer lexer = new OogwayLexer(in);

        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        return new OogwayParser(tokens);
    }

    public static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
    }

    private TAC() {
    }
}
