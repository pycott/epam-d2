package ru.pycott.calc.rest;

public class Expression {
    /**
     * Класс для сериализации в json.
     * Представляет запрос клиента - арифметическое выражение.
     */
    private String expression;

    public Expression() {
    }

    Expression(String expression) {
        this.expression = expression;
    }

    String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
