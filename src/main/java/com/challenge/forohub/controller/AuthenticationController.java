package com.challenge.forohub.controller;

import com.challenge.forohub.domain.usuario.Usuario;
import com.challenge.forohub.domain.usuario.dto.DatosAutenticacioUsuario;
import com.challenge.forohub.infra.security.DatosJWToken;
import com.challenge.forohub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

      private AuthenticationManager authenticationManager;
      private TokenService tokenService;

    public  AuthenticationController(AuthenticationManager authenticationManager,
                                     TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService= tokenService;

    }

    @Operation(summary = "Obtiene un token para usuario registrado")
    @PostMapping
    @Transactional
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacioUsuario datosAutenticacioUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacioUsuario.correoElectronico(),
                datosAutenticacioUsuario.password());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWToken(JWToken));
    }
}
