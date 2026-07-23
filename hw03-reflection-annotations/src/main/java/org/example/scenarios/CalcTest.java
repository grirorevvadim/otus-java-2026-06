package org.example.scenarios;

import org.example.annotations.After;
import org.example.annotations.Before;
import org.example.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcTest {
    int a = 0;
    int b = 0;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(CalcTest.class);


    @Before
    public void prepare() {
        LOGGER.info("prepare");
        this.a = 0;
        this.b = 0;
    }

    @After
    public void reset() {
        LOGGER.info("reset");
        this.a = 0;
        this.b = 0;
    }

    @Test
    public void sum() {
        LOGGER.info("+ test");
        this.a = 2;
        this.b = 2;
        assertEquals(4, a + b, "sum is wrong");
    }

    @Test
    public void subtraction() {
        LOGGER.info("- test");
        this.a = 4;
        this.b = 2;
        assertEquals(2, a - b, "subtraction is wrong");
    }

    @Test
    public void multi() {
        LOGGER.info("* test");
        this.a = 2;
        this.b = 2;
        assertEquals(4, a * b, "multiplication is wrong");
    }

    @Test
    public void divide() {
        LOGGER.info("/ test");
        this.a = 4;
        this.b = 2;
        assertEquals(2, a / b, "divide is wrong");
    }

    @Test
    public void mod() {
        LOGGER.info("% test");
        this.a = 4;
        this.b = 3;
        assertEquals(1, a % b, "mod is wrong");
    }

    @Test
    public void equal() {
        LOGGER.info("== test");
        assertEquals(a, b, "equal is wrong");
    }

    @Test
    public void supposeToFail(){
        LOGGER.info("0 test");
        int a = 2/0;
    }

    public void test(){
        LOGGER.error("Should not be executed");
    }
}

