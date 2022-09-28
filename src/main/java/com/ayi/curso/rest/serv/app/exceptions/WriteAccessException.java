package com.ayi.curso.rest.serv.app.exceptions;

public class WriteAccessException extends ReadAccessException { // Llama a la clase que creé más arriba
    public WriteAccessException(String message) {
        super(message);
    }
}
