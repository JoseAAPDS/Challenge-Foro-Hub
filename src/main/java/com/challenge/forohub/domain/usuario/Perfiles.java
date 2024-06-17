package com.challenge.forohub.domain.usuario;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = PerfilesDeserializer.class)
public enum Perfiles {
    ADMINISTRADOR,
    INSTRUCTOR,
    ESTUDIANTE
}
