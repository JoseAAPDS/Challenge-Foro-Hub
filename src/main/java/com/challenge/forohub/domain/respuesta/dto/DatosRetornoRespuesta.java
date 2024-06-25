package com.challenge.forohub.domain.respuesta.dto;

import com.challenge.forohub.domain.respuesta.Respuesta;

import java.time.LocalDateTime;

public record DatosRetornoRespuesta(
        Long id,
        String mensaje,
        Long idTopico,
        String tituloTopico,
        String fechaCreacion,
        String autorRespuesta,
        Boolean solucion
) {
    public DatosRetornoRespuesta(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo(), respuesta.getFechaCreacion().toString(),
                respuesta.getAutor().getNombre(), respuesta.getSolucion());
    }
}
