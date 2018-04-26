package bphc.cse.dragon;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import static bphc.cse.dragon.TAC.readFile;

/**
 * Unit test for TAC.
 */
public class TACTest {
    public static final String TEST_INPUTS_DIR =
            "./src/test/test-resources/test-inputs/";
    public static final String EXP_OUTPUTS_DIR =
            "./src/test/test-resources/test-inputs/expected-outputs/";

    @Test
    public void testTacGen1() throws IOException {
        final String fileName = "testTacGen1.txt";
        final String expected = getExpectedOutput(fileName);
        final String actual = get3AC(fileName);
        Assert.assertEquals("", expected, actual);
    }

    @Test
    public void testTacGen2() throws IOException {
        final String fileName = "testTacGen2.txt";
        final String expected = getExpectedOutput(fileName);
        final String actual = get3AC(fileName);
        Assert.assertEquals("", expected, actual);
    }

    @Test
    public void testTacGen3() throws IOException {
        final String fileName = "testTacGen3.txt";
        final String expected = getExpectedOutput(fileName);
        final String actual = get3AC(fileName);
        Assert.assertEquals("", expected, actual);
    }

    @Test
    public void testTacGen4() throws IOException {
        final String fileName = "testTacGen4.txt";
        final String expected = getExpectedOutput(fileName);
        final String actual = get3AC(fileName);
        Assert.assertEquals("", expected, actual);
    }

    @Test
    public void testTacGen5() throws IOException {
        final String fileName = "testTacGen5.txt";
        final String expected = getExpectedOutput(fileName);
        final String actual = get3AC(fileName);
        Assert.assertEquals("", expected, actual);
    }

    @Test
    public void testTacGen6() throws IOException {
        final String fileName = "testTacGen6.txt";
        final String expected = getExpectedOutput(fileName);
        final String actual = get3AC(fileName);
        Assert.assertEquals("", expected, actual);
    }

    private static String get3AC(String fileName) throws IOException {
        final String filePath = TEST_INPUTS_DIR + fileName;
        return TAC.getTac(TAC.createOogwayParser(readFile(filePath)).start());
    }

    private static String getExpectedOutput(String fileName) throws IOException {
        return readFile(EXP_OUTPUTS_DIR + fileName);
    }
}
