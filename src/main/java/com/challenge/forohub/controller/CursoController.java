package com.challenge.forohub.controller;

import com.challenge.forohub.domain.curso.CursoService;
import com.challenge.forohub.domain.curso.dto.DatosActualizarCurso;
import com.challenge.forohub.domain.curso.dto.DatosRegistroCurso;
import com.challenge.forohub.domain.curso.dto.DatosRespuestaCurso;
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
@RequestMapping("/cursos")
public class CursoController {
    private CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> registrarNuevoCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso,
                                                                   UriComponentsBuilder uriComponentsBuilder){

        DatosRespuestaCurso datosRespuestaCurso = cursoService.registrarNuevoCurso(datosRegistroCurso);
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(datosRespuestaCurso.id()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaCurso);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaCurso>> listadoCursos(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(cursoService.buscarCursosActivos(paginacion));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> actualizarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso){
        return ResponseEntity.ok(cursoService.actualizarCurso(datosActualizarCurso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity borraCurso(@PathVariable Long id){
        cursoService.borrarCurso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> retornarDatosCurso(@PathVariable Long id){
        return ResponseEntity.ok(cursoService.buscarCursoId(id));
    }



}
