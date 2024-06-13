package com.challenge.forohub.domain.topico.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record DatosRegistroTopico(
        Long id,
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        LocalDateTime fecha,
        @NotBlank
        String idAutor,
        @NotBlank
        String idCurso) {


}
