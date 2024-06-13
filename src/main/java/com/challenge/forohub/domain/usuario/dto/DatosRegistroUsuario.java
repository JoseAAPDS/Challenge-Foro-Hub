package com.challenge.forohub.domain.usuario.dto;

import com.challenge.forohub.domain.usuario.Perfiles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DatosRegistroUsuario(
        @NotBlank
        String nombre,
        @NotBlank @Email
        String correoElectronico,
        @NotBlank
        String contrasena,
        @NotNull
        Perfiles perfil
) {
}
