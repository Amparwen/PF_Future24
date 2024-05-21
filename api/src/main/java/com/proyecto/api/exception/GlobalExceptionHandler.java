package com.proyecto.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler es un controlador de excepciones global que maneja excepciones específicas y
 * generales en la aplicación. Proporciona respuestas de error consistentes a los clientes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción ResourceNotFoundException.
     *
     * @param ex la excepción ResourceNotFoundException que se lanzó.
     * @return un ResponseEntity con un ErrorResponse y el estado HTTP NOT_FOUND.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja la excepción InvalidDataException.
     *
     * @param ex la excepción InvalidDataException que se lanzó.
     * @return un ResponseEntity con un ErrorResponse y el estado HTTP BAD_REQUEST.
     */
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidDataException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja la excepción SaveException.
     *
     * @param ex la excepción SaveException que se lanzó.
     * @return un ResponseEntity con un ErrorResponse y el estado HTTP INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(SaveException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeSaveException(SaveException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja la excepción ErrorEmployeeAssignedProjects.
     *
     * @param ex la excepción ErrorEmployeeAssignedProjects que se lanzó.
     * @return un ResponseEntity con un ErrorResponse y el estado HTTP CONFLICT.
     */
    @ExceptionHandler(ErrorEmployeeAssignedProjects.class)
    public ResponseEntity<ErrorResponse> handleErrorEmployeeAssignedProjectsException(ErrorEmployeeAssignedProjects ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Maneja excepciones generales no controladas específicamente.
     *
     * @param ex la excepción general que se lanzó.
     * @return un ResponseEntity con un ErrorResponse y el estado HTTP INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
