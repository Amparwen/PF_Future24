package com.proyecto.api.exception;

public class ErrorEmployeeAssignedProjects extends RuntimeException {
    /**
     * Construye una nueva excepción en tiempo de ejecución con el mensaje de detalle especificado.
     *
     * @param message el mensaje de detalle. El mensaje de detalle se guarda para
     *                su posterior recuperación mediante el método getMessage().
     */
    public ErrorEmployeeAssignedProjects(String message) {
        super(message);
    }
}
