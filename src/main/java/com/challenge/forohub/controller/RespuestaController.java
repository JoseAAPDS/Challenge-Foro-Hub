package com.challenge.forohub.controller;

import com.challenge.forohub.domain.respuesta.RespuestaService;
import com.challenge.forohub.domain.respuesta.dto.DatosActualizarRespuesta;
import com.challenge.forohub.domain.respuesta.dto.DatosRegistroRespuesta;
import com.challenge.forohub.domain.respuesta.dto.DatosRetornoRespuesta;
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
import java.security.Principal;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {
    private RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService){
        this.respuestaService = respuestaService;
    }

    @Operation(summary = "Registra una nueva respuesta",
            description = """
            Para registrar una nueva respuesta se necesita tener un tópico registrado.
            """)
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRetornoRespuesta> registrarNuevaRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
                                                                         UriComponentsBuilder uriComponentsBuilder, Principal principal){
        DatosRetornoRespuesta retornoRespuesta = respuestaService.registrarRespuesta(datosRegistroRespuesta, principal);
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(retornoRespuesta.id()).toUri();
        return ResponseEntity.created(url).body(retornoRespuesta);
    }

    @Operation(summary = "Listado de todos las respuestas registradas",
            description = """
            Las respuestas vienen ordenadas en forma ascendente por fecha de creación.  Adicionalmente se
            puede filtrar por tópico.
            """)
    @GetMapping
    public ResponseEntity<Page<DatosRetornoRespuesta>> listadoRespuestas(@RequestParam(name = "topicoId", required = false) Long topicoId,
                                                                         @PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(respuestaService.buscarRespuestasActivas(topicoId, paginacion));
    }

    @Operation(summary = "Modifica una respuesta",
            description = """
            Solamente puede modificarse el mensaje de la respuesta. Únicamente el autor de la
            respuesta puede modificarla.
            """)
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRetornoRespuesta> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta,
                                                                     Principal principal){
        return ResponseEntity.ok(respuestaService.actualizarRespuesta(datosActualizarRespuesta, principal));
    }

    @Operation(summary = "Marca una respuesta como solución del tópico asociado",
    description = """
            Aclaración: Solo el autor del tópico puede marcar una respuesta como solución al tópico"
            """)
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRetornoRespuesta> marcarComoSolucion(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(respuestaService.marcarSolucion(id, principal));
    }

    @Operation(summary = "Elimina una respuesta",
            description = """
            Aclaración: No pueden eliminarse respuestas creadas por otros usuarios, a excepción de
            usuarios con rol "administrador" que pueden borrar cualquier respuesta.
            """)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id, Principal principal){
        respuestaService.borrarRespuesta(id, principal);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca una respuesta por id")
    @GetMapping("/{id}")
    public ResponseEntity<DatosRetornoRespuesta> retornaDatosRespuesta(@PathVariable Long id){
        return ResponseEntity.ok(respuestaService.buscarRespuestaId(id));
    }
}
