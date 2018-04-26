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
        final String actual = get3AC(filePath);
        Assert.assertEquals("", expected, actual);
    }

    @Test
    public void testTacGen2() throws IOException {
        final String expected =
                "def main:\n" +
                        "MOV t0 5\n" +
                        "MOV t1 6\n" +
                        "CALL h\n" +
                        "MOV t3 ret_loc\n" +
                        "MOV t2 t3\n";
        final String filePath = TEST_FILES_DIR + "testTacGen2.txt";
        final String actual = get3AC(filePath);
        Assert.assertEquals("", expected, actual);
    }

    @Test
    public void testTacGen3() throws IOException {
        final String expected =
                "def main:\n" +
                        "GT t1 t0 6\n" +
                        "JMPEVAL t1 t2\n" +
                        "MOV t0 5\n" +
                        "t2:\n";
        final String filePath = TEST_FILES_DIR + "testTacGen3.txt";
        final String actual = get3AC(filePath);
        Assert.assertEquals("", expected, actual);
    }

    @Test
    public void testTacGen4() throws IOException {
        final String expected =
                "def main:\n" +
                        "GT t1 t0 6\n" +
                        "JMPEVAL t1 t5\n" +
                        "MOV t0 5\n" +
                        "LT t3 t2 5\n" +
                        "JMPEVAL t3 t4\n" +
                        "MOV t2 6\n" +
                        "t4:\n" +
                        "t5:\n";
        final String filePath = TEST_FILES_DIR + "testTacGen4.txt";
        final String actual = get3AC(filePath);
        Assert.assertEquals("", expected, actual);
    }

    @Test
    public void testTacGen5() throws IOException {
        final String expected =
                "def main:\n" +
                        "Syscall write \"yo\"\n";
        final String filePath = TEST_FILES_DIR + "testTacGen5.txt";
        final String actual = get3AC(filePath);
        Assert.assertEquals("", expected, actual);
    }

    @Test
    public void testTacGen6() throws IOException {
        final String expected =
                "def main:\n" +
                        "MOV t0 5\n" +
                        "MOV t1 \"India\"\n" +
                        "Syscall read t2\n" +
                        "Syscall write \"string\"\n" +
                        "MUL t4 t2 4\n" +
                        "DIV t5 t4 6\n" +
                        "ADD t6 t0 t5\n" +
                        "ADD t7 t6 6\n" +
                        "ADD t8 t7 8.885\n" +
                        "CALL h\n" +
                        "MOV t9 ret_loc\n" +
                        "ADD t10 t8 t9\n" +
                        "MOV t3 t10\n" +
                        "MUL t11 8 8\n" +
                        "ADD t12 5 t11\n" +
                        "ADD t13 5 9\n" +
                        "EQ t14 t12 t13\n" +
                        "JMPEVAL t14 t17\n" +
                        "MUL t16 t3 8.8\n" +
                        "MOV t15 t16\n" +
                        "t17:\n";
        final String filePath = TEST_FILES_DIR + "testTacGen6.txt";
        final String actual = get3AC(filePath);
        Assert.assertEquals("", expected, actual);
    }

    private static String get3AC(String filePath) throws IOException {
        return TAC.getTac(TAC.createOogwayParser(TAC.readFile(filePath)).start());
    }
}
