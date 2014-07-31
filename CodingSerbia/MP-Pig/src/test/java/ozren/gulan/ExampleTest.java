package ozren.gulan;

import java.io.File;
import java.io.IOException;

import org.apache.pig.pigunit.PigTest;
import org.apache.pig.tools.parameters.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Example of unit test
 * 
 * @author Ozren Gulan
 */
public class ExampleTest {

    private static final String TEST_PATH = "src/test/resources/";

    private static PigTest test;

    @BeforeClass
    public static void setUp() throws IOException, ParseException {
        test = new PigTest("src/main/resources/example.pig");
        test.override("students", "students = LOAD '" + TEST_PATH + "input/students.txt' USING PigStorage(',') AS (id:long,"
                                                                                                                + "firstName:chararray,"
                                                                                                                + "lastName:chararray,"
                                                                                                                + "country:chararray,"
                                                                                                                + "city:chararray,"
                                                                                                                + "university:chararray,"
                                                                                                                + "course:chararray);");

        test.override("rating", "rating = LOAD '" + TEST_PATH + "input/rating.txt' USING PigStorage(',') AS (studentId:long, rating:float);");
    }

    @Test
    public void testJoinedRecords() throws IOException, ParseException {
        test.assertOutput("joinedRecords", new File(TEST_PATH + "results/joinedRecords.txt"));
    }

    @Test
    public void testGeneratedRecords() throws IOException, ParseException {
        test.assertOutput("generatedRecords", new File(TEST_PATH + "results/generatedRecords.txt"));
    }

}