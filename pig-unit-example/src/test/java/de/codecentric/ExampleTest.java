package de.codecentric;

import java.io.File;
import java.io.IOException;

import org.apache.pig.pigunit.PigTest;
import org.apache.pig.tools.parameters.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Example of unit test
 * 
 * @author Ozren Gulan (ozren.gulan@codecentric.de)
 * 
 */
public class ExampleTest {

    private static final String TEST_PATH = "src/test/resources/";

    private static PigTest test;

    @BeforeClass
    public static void setUp() throws IOException, ParseException {
        // TODO: load pig script properly
        test = new PigTest("src/main/resources/example.pig");
        test.override("users", "users = LOAD '" + TEST_PATH + "input/users.txt' USING PigStorage(',') AS (id:long," + "firstName:chararray," + "lastName:chararray," + "country:chararray,"
                        + "city:chararray," + "company:chararray);");

        test.override("awesomenessRating", "awesomenessRating = LOAD '" + TEST_PATH + "input/awesomeness-rating.txt' USING PigStorage(',') AS (userId:long, rating:long);");
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