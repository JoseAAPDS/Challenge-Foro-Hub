package com.challenge.forohub.domain.topico;

import com.challenge.forohub.domain.Curso;
import com.challenge.forohub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public class Topico {
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private Boolean activo;
    private Usuario autor;
    private Curso curso;
    private List<Respuesta> respuestas;



}
