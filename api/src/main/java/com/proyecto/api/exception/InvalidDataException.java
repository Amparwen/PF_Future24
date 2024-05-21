package com.proyecto.api.exception;
/**
 * InvalidDataException es una excepción en tiempo de ejecución que se lanza cuando se encuentran datos inválidos.
 */
public class InvalidDataException extends RuntimeException{

    /**
     * Construye una nueva excepción InvalidDataException con el mensaje de detalle especificado.
     *
     * @param message el mensaje de detalle.
     */
    public InvalidDataException(String message) {
        super(message);
    }
}
