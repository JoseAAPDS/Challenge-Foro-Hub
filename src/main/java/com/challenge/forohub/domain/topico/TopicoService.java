package com.challenge.forohub.domain.topico;

import com.challenge.forohub.domain.curso.CursoRepository;
import com.challenge.forohub.domain.respuesta.Respuesta;
import com.challenge.forohub.domain.respuesta.RespuestaRepository;
import com.challenge.forohub.domain.topico.dto.DatosActualizarTopico;
import com.challenge.forohub.domain.topico.dto.DatosRegistroTopico;
import com.challenge.forohub.domain.topico.dto.DatosRespuestaTopico;
import com.challenge.forohub.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {
    private TopicoRepository topicoRepository;
    private CursoRepository cursoRepository;
    private UsuarioRepository usuarioRepository;
    private RespuestaRepository respuestaRepository;

    public TopicoService(TopicoRepository topicoRepository, CursoRepository cursoRepository,
                         UsuarioRepository usuarioRepository, RespuestaRepository respuestaRepository){
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
        this.respuestaRepository = respuestaRepository;
    }

    public DatosRespuestaTopico registrarNuevoTopico(DatosRegistroTopico datosRegistroTopico){
        var usuario = usuarioRepository.findByIdActivoTrue(datosRegistroTopico.idAutor());
        if (usuario == null){
            throw new EntityNotFoundException("Usuario with id " + datosRegistroTopico.idAutor() + " not found");
        }
        var curso = cursoRepository.findByIdActivoTrue(datosRegistroTopico.idCurso());
        if (curso == null){
            throw new EntityNotFoundException("Curso with id " + datosRegistroTopico.idCurso() + " not found");
        }

        Topico nuevoTopico = topicoRepository.save(new Topico(datosRegistroTopico, usuario, curso));

        DatosRespuestaTopico respuestaTopico = new DatosRespuestaTopico(nuevoTopico.getId(), nuevoTopico.getTitulo(),
                nuevoTopico.getMensaje(),nuevoTopico.getFechaCreacion().toString(), nuevoTopico.getAutor().getNombre(),
                nuevoTopico.getCurso().getNombre());

        return respuestaTopico;
    }

    public Page<DatosRespuestaTopico> buscarTopicosActivos(Long cursoId, Integer anio,
                                                                 Pageable paginacion) {
        //Lista por curso y año de creación
        if(cursoId != null && anio != null){
            LocalDateTime fechaInicio = LocalDateTime.of(anio, 1, 1,0,0,0);
            LocalDateTime fechaFinal = fechaInicio.plusYears(1L);
            Page listaTopicos = topicoRepository.encontrarPorCursoActivoEnAnio(cursoId, fechaInicio, fechaFinal, paginacion);
            if (listaTopicos.getContent().isEmpty()){
                throw new EntityNotFoundException("Id or year not found");
            }
            return topicoRepository.encontrarPorCursoActivoEnAnio(cursoId, fechaInicio, fechaFinal, paginacion).map(DatosRespuestaTopico::new);
        }

        //Lista por curso
        if (cursoId != null){
            Page listaTopicos = topicoRepository.findByActivoTrueAndCursoIdOrderByFechaCreacion(cursoId, paginacion);
            if (listaTopicos.getContent().isEmpty()){
                throw new EntityNotFoundException("Id not found");
            }
            return topicoRepository.findByActivoTrueAndCursoIdOrderByFechaCreacion(cursoId, paginacion).map(DatosRespuestaTopico::new);
        }

        //Lista por fecha
        if (anio != null){
            LocalDateTime fechaInicio = LocalDateTime.of(anio, 1, 1,0,0,0);
            LocalDateTime fechaFinal = fechaInicio.plusYears(1L);
            Page listaTopicos = topicoRepository.findByActivoTrueAndFechaCreacionOrderByFechaCreacion(fechaInicio, fechaFinal ,paginacion);
            if (listaTopicos.getContent().isEmpty()){
                throw new EntityNotFoundException("year not found");
            }
            return topicoRepository.findByActivoTrueAndFechaCreacionOrderByFechaCreacion(fechaInicio, fechaFinal ,paginacion).map(DatosRespuestaTopico::new);
        }

        //Lista completa
        return topicoRepository.findByActivoTrueOrderByFechaCreacion(paginacion).map(DatosRespuestaTopico::new);

    }

    public DatosRespuestaTopico actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.findByIdActivoTrue(datosActualizarTopico.id());
        if (topico == null){
            throw new EntityNotFoundException("Topico with id " + datosActualizarTopico.id() + " not found");
        }
        topico.actualizarDatos(datosActualizarTopico);

        return new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(),topico.getFechaCreacion().toString(), topico.getAutor().getNombre(),
                topico.getCurso().getNombre());
    }

    public void borrarTopico(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isEmpty()){
            throw new EntityNotFoundException("Topico with id " + id + " not found");
        }
        List<Respuesta> respuestasPorTopico = respuestaRepository.findByTopicoId(id);
        if(!respuestasPorTopico.isEmpty()){
            for (Respuesta respuesta : respuestasPorTopico){
                respuestaRepository.delete(respuesta);
            }
        }
        Topico topicoBorrar = topico.get();
        topicoRepository.delete(topicoBorrar);
    }

    public DatosRespuestaTopico buscarTopicoId(Long id) {
        Topico topico = topicoRepository.findByIdActivoTrue(id);
        if (topico == null){
            throw new EntityNotFoundException("Topico with id " + id + " not found");
        }
        return new DatosRespuestaTopico(topico);
    }

}
