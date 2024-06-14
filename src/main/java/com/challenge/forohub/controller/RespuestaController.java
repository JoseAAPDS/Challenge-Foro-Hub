package com.challenge.forohub.controller;

import com.challenge.forohub.domain.respuesta.RespuestaService;
import com.challenge.forohub.domain.respuesta.dto.DatosActualizarRespuesta;
import com.challenge.forohub.domain.respuesta.dto.DatosRegistroRespuesta;
import com.challenge.forohub.domain.respuesta.dto.DatosRetornoRespuesta;
import com.challenge.forohub.domain.topico.dto.DatosActualizarTopico;
import com.challenge.forohub.domain.topico.dto.DatosRegistroTopico;
import com.challenge.forohub.domain.topico.dto.DatosRespuestaTopico;
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
@RequestMapping("/respuestas")
public class RespuestaController {
    private RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService){
        this.respuestaService = respuestaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRetornoRespuesta> registrarNuevaRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
                                                                         UriComponentsBuilder uriComponentsBuilder){
        DatosRetornoRespuesta retornoRespuesta = respuestaService.registrarRespuesta(datosRegistroRespuesta);
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(retornoRespuesta.id()).toUri();
        return ResponseEntity.created(url).body(retornoRespuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRetornoRespuesta>> listadoRespuestas(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(respuestaService.buscarRespuestasActivas(paginacion));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRetornoRespuesta> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta){
        return ResponseEntity.ok(respuestaService.actualizarRespuesta(datosActualizarRespuesta));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRetornoRespuesta> marcarComoSolucion(@PathVariable Long id){
        return ResponseEntity.ok(respuestaService.marcarSolucion(id));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        respuestaService.desactivarRespuesta(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRetornoRespuesta> retornaDatosRespuesta(@PathVariable Long id){
        return ResponseEntity.ok(respuestaService.buscarRespuestaId(id));
    }
}
