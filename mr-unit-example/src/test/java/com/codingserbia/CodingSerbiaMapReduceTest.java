package com.codingserbia;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class CodingSerbiaMapReduceTest {

    private CodingSerbiaMapReduce mr;

    @Before
    public void setUp() {
        mr = new CodingSerbiaMapReduce();
    }

    @Test
    public void testValidateAndParseInput() {
        String inputPath = "/some/input/path";
        String outputPath = "/some/output/path";
        String unneededPath = "/some/unneeded/path";

        Assert.assertFalse(mr.validateAndParseInput(null));
        Assert.assertFalse(mr.validateAndParseInput(new String[] { inputPath }));
        Assert.assertFalse(mr.validateAndParseInput(new String[] { inputPath, outputPath, unneededPath }));

        Assert.assertTrue(mr.validateAndParseInput(new String[] { inputPath, outputPath }));
        Assert.assertEquals(inputPath, mr.inputPath);
        Assert.assertEquals(outputPath, mr.outputPath);
    }

}
