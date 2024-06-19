package com.challenge.forohub.domain.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacioUsuario(
        @NotBlank
        String correoElectronico,
        @NotBlank
        String password
) {
}
