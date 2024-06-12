package com.challenge.forohub.controller;

import com.challenge.forohub.domain.topico.DatosRegistroTopico;
import com.challenge.forohub.domain.topico.DatosRespuestaTopico;
import com.challenge.forohub.domain.topico.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    private TopicoService topicoService;

    public TopicoController(TopicoService topicoService){
        this.topicoService = topicoService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarNuevoTopico(@RequestBody @Valid DatosRegistroTopico nuevoTopico,
                                                                    UriComponentsBuilder uriComponentsBuilder){
        DatosRespuestaTopico datosRespuestaTopico = topicoService.registrarNuevoTopico(nuevoTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(datosRespuestaTopico.id()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }
}
