package ru.pycott.calc.rest;

import ru.pycott.calc.calculator.Calculator;
import ru.pycott.calc.calculator.ExpressionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("calculator")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorService {
    /**
     * calculator/calculate/{query}
     * Вычисляет запрашиваемое арифметическое выражение.
     *
     * @param query - арифметическое выражение
     * @return результат вычислений
     * @throws ExpressionException ошибка в арифметическом выражении
     */
    @GET
    @Path("calculate/{query}")
    public Result calculate(@PathParam("query") String query) throws ExpressionException {
        return new Result(Calculator.calculateExpression(query));
    }
}
