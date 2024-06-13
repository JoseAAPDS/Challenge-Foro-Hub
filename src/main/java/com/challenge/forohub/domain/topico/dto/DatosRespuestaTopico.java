package com.challenge.forohub.domain.topico.dto;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        String autor,
        String curso
) {
}
