package com.proyecto.api.exception;
/**
 * SaveException es una excepción en tiempo de ejecución que se lanza cuando ocurre un error al guardar datos.
 */
public class SaveException extends RuntimeException {
    /**
     * Construye una nueva excepción SaveException con el mensaje de detalle especificado.
     *
     * @param message el mensaje de detalle.
     */
    public SaveException(String message) {
        super(message);
    }
}
