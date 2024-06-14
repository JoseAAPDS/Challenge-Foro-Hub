package com.challenge.forohub.domain.curso.dto;

import com.challenge.forohub.domain.curso.Curso;

public record DatosRespuestaCurso(
        Long id,
        String nombre,
        String categoria) {

    public DatosRespuestaCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
