package com.challenge.forohub.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroRespuesta(
        @NotBlank
        String mensaje,
        @NotNull
        Long idTopico,
        @NotNull
        Long idAutor
) {
}
