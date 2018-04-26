package bphc.cse.dragon;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for TAC.
 */
public class TACTest {
    public static final String TEST_FILES_DIR = "./src/test/test-resources/";

    @Test
    public void testTacGen1() throws IOException {
        final String expected =
                "def func2:\n" +
                "MOV t0 \"this\"\n" +
                "DIV t3 t1 t2\n" +
                "MUL t5 t4 0\n" +
                "ADD t6 t3 t5\n" +
                "MOV t1 t6\n" +
                "\n" +
                "def main:\n" +
                "LOAD t8 7(b)\n" +
                "MOV t7 t8\n";
        final String filePath = TEST_FILES_DIR + "testTacGen1.txt";
        final String tac = TAC.getTac(TAC.createOogwayParser(TAC.readFile(filePath)).start());
        Assert.assertEquals("", expected, tac);
    }
}

