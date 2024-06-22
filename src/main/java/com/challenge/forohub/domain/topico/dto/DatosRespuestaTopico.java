package com.challenge.forohub.domain.topico.dto;

import com.challenge.forohub.domain.topico.Topico;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        String autor,
        String curso
) {
    public DatosRespuestaTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion().toString(),
                topico.getAutor().getNombre(), topico.getCurso().getNombre());
    }

}
