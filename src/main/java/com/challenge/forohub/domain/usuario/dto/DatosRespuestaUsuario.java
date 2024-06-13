package com.challenge.forohub.domain.usuario.dto;

import com.challenge.forohub.domain.usuario.Usuario;

public record DatosRespuestaUsuario(
        Long id,
        String nombre,
        String correoElectronico,
        String perfil) {

    public DatosRespuestaUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getCorreoElectronico(),
                usuario.getPerfil().toString().toLowerCase());
    }
}
