package com.challenge.forohub.domain.topico;

import com.challenge.forohub.domain.curso.CursoRepository;
import com.challenge.forohub.domain.respuesta.Respuesta;
import com.challenge.forohub.domain.respuesta.RespuestaRepository;
import com.challenge.forohub.domain.topico.dto.DatosActualizarTopico;
import com.challenge.forohub.domain.topico.dto.DatosRegistroTopico;
import com.challenge.forohub.domain.topico.dto.DatosRespuestaTopico;
import com.challenge.forohub.domain.usuario.Usuario;
import com.challenge.forohub.domain.usuario.UsuarioRepository;
import com.challenge.forohub.infra.errores.ValidacionRequerimientos;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
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

    public DatosRespuestaTopico registrarNuevoTopico(DatosRegistroTopico datosRegistroTopico, Principal principal){
        //El usuario que envía el token se asigna como autor del tópico
        var usuario = usuarioRepository.findByCorreoElectronico(principal.getName());

        var curso = cursoRepository.findByIdActivoTrue(datosRegistroTopico.idCurso());
        if (curso == null){
            throw new EntityNotFoundException("Curso with id " + datosRegistroTopico.idCurso() + " not found");
        }

        Topico nuevoTopico = topicoRepository.save(new Topico(datosRegistroTopico, (Usuario) usuario, curso));

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

    public DatosRespuestaTopico actualizarTopico(DatosActualizarTopico datosActualizarTopico, Principal principal) {
        Usuario usuario = (Usuario) usuarioRepository.findByCorreoElectronico(principal.getName());
        Topico topico = topicoRepository.findByIdActivoTrue(datosActualizarTopico.id());
        if (topico == null){
            throw new EntityNotFoundException("Topico with id " + datosActualizarTopico.id() + " not found");
        }
        //Solamente el autor del tópico puede modificarlo
        if(usuario.getId() == topico.getAutor().getId()){
            topico.actualizarDatos(datosActualizarTopico);

            return new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
                    topico.getMensaje(),topico.getFechaCreacion().toString(), topico.getAutor().getNombre(),
                    topico.getCurso().getNombre());
        } else {
            throw new ValidacionRequerimientos("Unable to update a topic from another author");
        }
    }

    public void borrarTopico(Long id, Principal principal) {
        //Usuario que solicita la acción
        Usuario usuario = (Usuario) usuarioRepository.findByCorreoElectronico(principal.getName());

        //Revisa si el tópico existe
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isEmpty()){
            throw new EntityNotFoundException("Topico with id " + id + " not found");
        }

        //Usuario con rol "administrador" tiene permitido borrar tópicos
        if(usuario.getPerfil().toString().equals("ADMINISTRADOR")){
            borrarRespuestaYTopico(id,topico.get());
        } else if (topico.get().getAutor().getId() == usuario.getId()) {
            //Borra el tópico si el usuario que solicita la acción es el autor del tópico.
            borrarRespuestaYTopico(id,topico.get());
        } else {
            throw new ValidacionRequerimientos("Unable to erase a topic from another author");
        }

    }

    private void borrarRespuestaYTopico(Long id, Topico topico){
        //borra respuestas asociadas con el tópico.
        List<Respuesta> respuestasPorTopico = respuestaRepository.findByTopicoId(id);
        if(!respuestasPorTopico.isEmpty()){
            for (Respuesta respuesta : respuestasPorTopico){
                respuestaRepository.delete(respuesta);
            }
        }
        //borra el tópico.
        topicoRepository.delete(topico);
    }

    public DatosRespuestaTopico buscarTopicoId(Long id) {
        Topico topico = topicoRepository.findByIdActivoTrue(id);
        if (topico == null){
            throw new EntityNotFoundException("Topico with id " + id + " not found");
        }
        return new DatosRespuestaTopico(topico);
    }

}
