package com.proyecto.api.exception;
/**
 * Excepción personalizada para representar situaciones donde un recurso no es encontrado.
 * Extiende de RuntimeException, lo que permite ser lanzada sin necesidad de declararla en los métodos.
 *
 * @author ResourceNotFoundException
 */
public class ResourceNotFoundException extends RuntimeException{
    /**
     * Constructor que recibe un mensaje de error que se pasará a la superclase RuntimeException.
     *
     * @param message Mensaje que detalla la causa de la excepción, típicamente incluirá el identificador
     *                del recurso no encontrado para facilitar la depuración y el manejo de errores.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
