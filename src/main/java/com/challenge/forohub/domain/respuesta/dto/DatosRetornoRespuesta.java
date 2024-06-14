package com.challenge.forohub.domain.respuesta.dto;

import com.challenge.forohub.domain.respuesta.Respuesta;

import java.time.LocalDateTime;

public record DatosRetornoRespuesta(
        Long id,
        String mensaje,
        Long idTopico,
        String tituloTopico,
        LocalDateTime fechaCreacion,
        String autorRespuesta,
        Boolean solucion
) {
    public DatosRetornoRespuesta(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo(), respuesta.getFechaCreacion(),
                respuesta.getAutor().getNombre(), respuesta.getSolucion());
    }
}
