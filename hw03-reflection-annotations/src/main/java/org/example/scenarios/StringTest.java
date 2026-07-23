package org.example.scenarios;

import org.example.annotations.After;
import org.example.annotations.Before;
import org.example.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StringTest {
    String a = "";
    String b = "";
    private static final Logger LOGGER
            = LoggerFactory.getLogger(StringTest.class);

    @Before
    public void prepare() {
        LOGGER.info("prepare");
        this.a = "";
        this.b = "";
    }

    @After
    public void reset() {
        LOGGER.info("reset");
        this.a = "";
        this.b = "";
    }

    @Test
    public void concat() {
        LOGGER.info("concat test");
        this.a = "Hello, ";
        this.b = "World!";
        assertEquals("Hello, World!", a + b, "concatenation is wrong");
    }

    @Test
    public void length() {
        LOGGER.info("length test");
        this.a = "reflection";
        assertEquals(10, a.length(), "length is wrong");
    }

    @Test
    public void toUpperCase() {
        LOGGER.info("uppercase test");
        this.a = "test runner";
        assertEquals("TEST RUNNER", a.toUpperCase(), "uppercase conversion is wrong");
    }

    @Test
    public void contains() {
        LOGGER.info("contains test");
        this.a = "annotations are useful";
        assertTrue(a.contains("annotations"), "contains check is wrong");
    }

    @Test
    public void equalsIgnoreCase() {
        LOGGER.info("equalsIgnoreCase test");
        this.a = "JAVA";
        this.b = "java";
        assertTrue(a.equalsIgnoreCase(b), "equalsIgnoreCase is wrong");
    }

    @Test
    public void notEqual() {
        LOGGER.info("notEqual test");
        this.a = "before";
        this.b = "after";
        assertFalse(a.equals(b), "strings should not be equal");
    }

    @Test
    public void supposeToFail() {
        LOGGER.info("index out of bounds test");
        char c = "short".charAt(100);
    }

    public void test() {
        LOGGER.error("Should not be executed");
    }
}