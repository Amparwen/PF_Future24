package com.proyecto.api.exception;

/**
 * ErrorResponse es una clase que representa una respuesta de error con un estado y un mensaje.
 */
public class ErrorResponse {
    private int status;
    private String message;

    /**
     * Construye una nueva instancia de ErrorResponse con el estado y el mensaje especificados.
     *
     * @param status el estado de la respuesta de error.
     * @param message el mensaje de la respuesta de error.
     */
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    /**
     * Obtiene el estado de la respuesta de error.
     *
     * @return el estado de la respuesta de error.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Establece el estado de la respuesta de error.
     *
     * @param status el nuevo estado de la respuesta de error.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Obtiene el mensaje de la respuesta de error.
     *
     * @return el mensaje de la respuesta de error.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el mensaje de la respuesta de error.
     *
     * @param message el nuevo mensaje de la respuesta de error.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

