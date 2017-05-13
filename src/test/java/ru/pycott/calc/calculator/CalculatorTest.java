package ru.pycott.calc.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    @Test
    public void testCalculateSimpleExpression() throws Exception {
        assertEquals(4.0, Calculator.calculateExpression("2 + 2"), 0.01);
    }

    @Test
    public void testCalculatePriorityExpression() throws Exception {
        assertEquals(10.0, Calculator.calculateExpression("2 + 2 * 2 ^ 2"), 0.01);
    }

    @Test
    public void testCalculateBracketsExpression() throws Exception {
        assertEquals(64.0, Calculator.calculateExpression("(2+2)^2*(2+2)"), 0.01);
    }

    @Test
    public void testCalculateFloatExpression() throws Exception {
        assertEquals(3.8, Calculator.calculateExpression("1.3 + 2 +0.5"), 0.01);
    }

    @Test
    public void testCalculateBigNumExpression() throws Exception {
        assertEquals(3339.0, Calculator.calculateExpression("1112+1113+1114"), 0.01);
    }

    @Test
    public void testCalculateHardExpression() throws Exception {
        assertEquals(86.15, Calculator.calculateExpression("((2+2)+3)*4 / (3*0+1.3)*4"), 0.01);
    }

    @Test(expected = ExpressionException.class)
    public void testCalculateWrongExpressionLeftBracket() throws Exception {
        Calculator.calculateExpression("(2+2");
    }

    @Test(expected = ExpressionException.class)
    public void testCalculateWrongExpressionRightBracket() throws Exception {
        Calculator.calculateExpression("2+2)");
    }

    @Test(expected = ExpressionException.class)
    public void testCalculateWrongExpressionUnknownOperator() throws Exception {
        Calculator.calculateExpression("2%1");
    }

    @Test(expected = ExpressionException.class)
    public void testCalculateWrongExpressionEmpty() throws Exception {
        Calculator.calculateExpression("");
    }
}