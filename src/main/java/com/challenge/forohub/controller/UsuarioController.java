package com.challenge.forohub.controller;

import com.challenge.forohub.domain.usuario.dto.DatosActualizarUsuario;
import com.challenge.forohub.domain.usuario.dto.DatosRegistroUsuario;
import com.challenge.forohub.domain.usuario.dto.DatosRespuestaUsuario;
import com.challenge.forohub.domain.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Registra un nuevo usuario",
            description = """
            Los perfiles de usuario reconocidos son: "administrador" y "estudiante". El registro de usuarios
            únicamente puede ser realizado por un usuario con perfil "administrador".  No se aceptan correos
            electrónicos repetidos.
            """)
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                                                  UriComponentsBuilder uriComponentsBuilder){
        DatosRespuestaUsuario nuevoUsuario = usuarioService.registrarNuevoUsuario(datosRegistroUsuario);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(nuevoUsuario.id()).toUri();
        return ResponseEntity.created(url).body(nuevoUsuario);
    }

    @Operation(summary = "Listado de todos los usuarios",
            description = """
            Muestra solamente los usuarios activos.  Accesible únicamente para usuarios con perfil
            "administrador".
            """)
    @GetMapping
    public ResponseEntity<Page<DatosRespuestaUsuario>> listadoUsuarios(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(usuarioService.buscarUsuariosActivos(paginacion));
    }

    @Operation(summary = "Modifica un usuario",
            description = """
            Solamente pueden modificarse el nombre y el correo electrónico. Accesible únicamente
            para usuarios con perfil "administrador".
            """)
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario){
        return ResponseEntity.ok(usuarioService.actualizarUsuario(datosActualizarUsuario));
    }

    @Operation(summary = "Elimina un usuario",
            description = """
            Aclaración: No se elimina el usuario de la base de datos, únicamente se marca como inactivo.
            Accesible para usuarios con perfil "administrador".
            """)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id){
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Busca un usuario por id",
            description = """
            Aclaración: Accesible únicamente para usuarios con perfil "administrador".
            """)
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaUsuario> retornaDatosUsuario(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarUsuarioId(id));
    }
}
