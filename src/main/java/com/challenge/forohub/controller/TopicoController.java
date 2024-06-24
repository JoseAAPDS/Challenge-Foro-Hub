package com.challenge.forohub.controller;

import com.challenge.forohub.domain.topico.dto.DatosActualizarTopico;
import com.challenge.forohub.domain.topico.dto.DatosRegistroTopico;
import com.challenge.forohub.domain.topico.dto.DatosRespuestaTopico;
import com.challenge.forohub.domain.topico.TopicoService;
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
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    private TopicoService topicoService;

    public TopicoController(TopicoService topicoService){
        this.topicoService = topicoService;
    }

    @Operation(summary = "Registra un nuevo tópico",
    description = """
            Para registrar un nuevo tópico se necesita tener como mínimo un usuario y un curso registrados
            en la base de datos.  No se aceptan títulos o mensajes repetidos.
            """)
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarNuevoTopico(@RequestBody @Valid DatosRegistroTopico nuevoTopico,
                                                                    UriComponentsBuilder uriComponentsBuilder){
        DatosRespuestaTopico datosRespuestaTopico = topicoService.registrarNuevoTopico(nuevoTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(datosRespuestaTopico.id()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @Operation(summary = "Listado de todos los tópicos registrados",
            description = """
            Los tópicos vienen ordenados en forma ascendente por fecha de creación.  Adicionalmente se
            puede filtrar por curso, por año, o por curso y año.
            """)
    @GetMapping
    public ResponseEntity<Page<DatosRespuestaTopico>> listadoTopicos(@RequestParam(name = "cursoId", required = false) Long cursoId,
                                                                     @RequestParam(name = "year", required = false) Integer anio,
                                                                     @PageableDefault(size=10) Pageable paginacion){
        return ResponseEntity.ok(topicoService.buscarTopicosActivos(cursoId, anio, paginacion));
    }

    @Operation(summary = "Modifica un tópico",
            description = """
            Solamente pueden modificarse el título y el mensaje del tópico.
            """)
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        return ResponseEntity.ok(topicoService.actualizarTopico(datosActualizarTopico));
    }

    @Operation(summary = "Elimina un tópico",
            description = """
            Aclaración: La eliminación de un tópico eliminará también las respuestas asociadas al mismo.
            """)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        topicoService.borrarTopico(id);
        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Busca un tópico por id")
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopico(@PathVariable Long id){
        return ResponseEntity.ok(topicoService.buscarTopicoId(id));
    }
}
