package ru.pycott.calc.rest.errors;

class HttpErrorMessage {
    /**
     * Класс для сериализации в json.
     * Представляет ошибку.
     */
    private String message;

    public HttpErrorMessage() {}

    HttpErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
