package ru.pycott.calc.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import ru.pycott.calc.rest.errors.ExceptionHttpResolver;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class CalculatorServiceTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(CalculatorService.class)
                .register(new ExceptionHttpResolver());
    }

    @Test
    public void testCalculateGet() {
        Expression expression = new Expression("2+2");
        final Result result = target("calculator/calculate/")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.json(expression), new GenericType<Result>() {});
        assertEquals(4.0, result.getResult(), 0.01);
    }

    @Test
    public void testCalculateGetWithSlash() {
        Expression expression = new Expression("2/2");
        final Result result = target("calculator/calculate/")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.json(expression), new GenericType<Result>() {});
        assertEquals(1, result.getResult(), 0.01);
    }

    @Test
    public void testCalculateGetWrongExpression() {
        Expression expression = new Expression("2+2)");
        final Response response = target("calculator/calculate/")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.json(expression));
        assertEquals(400, response.getStatus());
    }
}