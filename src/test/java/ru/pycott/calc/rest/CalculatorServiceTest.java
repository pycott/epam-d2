package ru.pycott.calc.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import ru.pycott.calc.rest.errors.ExceptionHttpResolver;

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
        final Result result = target("calculator/calculate/(2.1 + 2 ) * 2 + 122^1")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(new GenericType<Result>() {});
        assertEquals(130.2, result.getResult(), 0.01);
    }

    @Test
    public void testCalculateGetWrongExpression() {
        final Response response = target("calculator/calculate/2+2)")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(400, response.getStatus());
    }
}