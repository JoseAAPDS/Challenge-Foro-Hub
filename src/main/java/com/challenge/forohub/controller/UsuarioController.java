package com.challenge.forohub.controller;

import com.challenge.forohub.domain.usuario.dto.DatosActualizarUsuario;
import com.challenge.forohub.domain.usuario.dto.DatosRegistroUsuario;
import com.challenge.forohub.domain.usuario.dto.DatosRespuestaUsuario;
import com.challenge.forohub.domain.usuario.UsuarioService;
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
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                                                  UriComponentsBuilder uriComponentsBuilder){
        DatosRespuestaUsuario nuevoUsuario = usuarioService.registrarNuevoUsuario(datosRegistroUsuario);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(nuevoUsuario.id()).toUri();
        return ResponseEntity.created(url).body(nuevoUsuario);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaUsuario>> listadoUsuarios(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(usuarioService.buscarUsuariosActivos(paginacion));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario){
        return ResponseEntity.ok(usuarioService.actualizarUsuario(datosActualizarUsuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id){
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaUsuario> retornaDatosUsuario(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarUsuarioId(id));
    }
}
