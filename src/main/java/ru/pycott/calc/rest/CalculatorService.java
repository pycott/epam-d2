package ru.pycott.calc.rest;

import ru.pycott.calc.calculator.Calculator;
import ru.pycott.calc.calculator.ExpressionException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("calculator")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorService {
    /**
     * calculator/calculate/{query}
     * Вычисляет запрашиваемое арифметическое выражение.
     *
     * @param expression - арифметическое выражение
     * @return результат вычислений
     * @throws ExpressionException ошибка в арифметическом выражении
     */
    @POST
    @Path("calculate")
    public Result calculate(Expression expression) throws ExpressionException {
        return new Result(Calculator.calculateExpression(expression.getExpression()));
    }
}
