package ru.pycott.calc.rest.errors;


import ru.pycott.calc.calculator.ExpressionException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHttpResolver implements ExceptionMapper<Throwable> {
    /**
     * Обработчик ошибок.
     * Сериализует все неотлавливаемые ошибки в json
     */
    @Override
    public Response toResponse(Throwable throwable) {
        Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;

        if (throwable instanceof ExpressionException)
            httpStatus = Response.Status.BAD_REQUEST;

        return Response.status(httpStatus)
                .entity(new HttpErrorMessage(throwable.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}