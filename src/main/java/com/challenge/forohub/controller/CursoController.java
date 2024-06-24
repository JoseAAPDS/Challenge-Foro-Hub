package com.challenge.forohub.controller;

import com.challenge.forohub.domain.curso.CursoService;
import com.challenge.forohub.domain.curso.dto.DatosActualizarCurso;
import com.challenge.forohub.domain.curso.dto.DatosRegistroCurso;
import com.challenge.forohub.domain.curso.dto.DatosRespuestaCurso;
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
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {
    private CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    @Operation(summary = "Registra un nuevo curso",
            description = """
            El registro de cursos únicamente puede ser realizado por un usuario con perfil "administrador".
            """)
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> registrarNuevoCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso,
                                                                   UriComponentsBuilder uriComponentsBuilder){

        DatosRespuestaCurso datosRespuestaCurso = cursoService.registrarNuevoCurso(datosRegistroCurso);
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(datosRespuestaCurso.id()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaCurso);
    }

    @Operation(summary = "Listado de todos los cursos",
            description = """
            Muestra solamente los cursos activos.  Accesible únicamente para usuarios con perfil
            "administrador".
            """)
    @GetMapping
    public ResponseEntity<Page<DatosRespuestaCurso>> listadoCursos(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(cursoService.buscarCursosActivos(paginacion));
    }

    @Operation(summary = "Modifica un curso",
            description = """
            Solamente pueden modificarse el nombre y la categoria. Accesible únicamente
            para usuarios con perfil "administrador".
            """)
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> actualizarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso){
        return ResponseEntity.ok(cursoService.actualizarCurso(datosActualizarCurso));
    }

    @Operation(summary = "Elimina un curso",
            description = """
            Aclaración: No se elimina el curso de la base de datos, únicamente se marca como inactivo.
            Accesible para usuarios con perfil "administrador".
            """)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity borraCurso(@PathVariable Long id){
        cursoService.borrarCurso(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca un curso por id",
            description = """
            Aclaración: Accesible únicamente para usuarios con perfil "administrador".
            """)
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> retornarDatosCurso(@PathVariable Long id){
        return ResponseEntity.ok(cursoService.buscarCursoId(id));
    }



}
