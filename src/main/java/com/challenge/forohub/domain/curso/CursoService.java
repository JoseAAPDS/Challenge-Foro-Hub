package com.challenge.forohub.domain.curso;

import com.challenge.forohub.domain.curso.dto.DatosActualizarCurso;
import com.challenge.forohub.domain.curso.dto.DatosRegistroCurso;
import com.challenge.forohub.domain.curso.dto.DatosRespuestaCurso;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class CursoService {
    private CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository){
        this.cursoRepository = cursoRepository;
    }

    public DatosRespuestaCurso registrarNuevoCurso(DatosRegistroCurso datosRegistroCurso) {
        Curso nuevoCurso = new Curso(datosRegistroCurso);
        cursoRepository.save(nuevoCurso);
        return new DatosRespuestaCurso(nuevoCurso.getId(), nuevoCurso.getNombre(), nuevoCurso.getCategoria());
    }

    public Page<DatosRespuestaCurso> buscarCursosActivos(Pageable paginacion) {
        return cursoRepository.findByActivoTrue(paginacion).map(DatosRespuestaCurso::new);
    }

    public DatosRespuestaCurso actualizarCurso(DatosActualizarCurso datosActualizarCurso) {
        Curso cursoActualizar = cursoRepository.findByIdActivoTrue(datosActualizarCurso.id());
        if(cursoActualizar == null){
            throw new EntityNotFoundException("Curso with id " + datosActualizarCurso.id() + " not found");
        }
        cursoActualizar.actualizarCurso(datosActualizarCurso);
        return new DatosRespuestaCurso(cursoActualizar);
    }

    public void borrarCurso(Long id) {
        Curso cursoBorrar = cursoRepository.findByIdActivoTrue(id);
        if(cursoBorrar == null){
            throw new EntityNotFoundException("Curso with id " + id + " not found");
        }
        cursoBorrar.borrarCurso();
    }

    public DatosRespuestaCurso buscarCursoId(Long id) {
        Curso curso = cursoRepository.findByIdActivoTrue(id);
        if(curso == null){
            throw new EntityNotFoundException("Curso with id " + id + " not found");
        }
        return new DatosRespuestaCurso(curso);
    }
}
