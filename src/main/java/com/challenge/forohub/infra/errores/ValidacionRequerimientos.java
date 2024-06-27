package com.challenge.forohub.infra.errores;

public class ValidacionRequerimientos extends RuntimeException{

    public ValidacionRequerimientos(String message) {
        super(message);
    }
}
