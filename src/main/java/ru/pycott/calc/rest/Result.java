package ru.pycott.calc.rest;

class Result {
    /**
     * Класс для сериализации в json.
     * Представляет результат вычислений.
     */
    private Double result;

    public Result() {
    }

    Result(Double result) {
        this.result = result;
    }

    Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
