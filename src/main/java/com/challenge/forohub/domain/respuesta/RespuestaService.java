package com.challenge.forohub.domain.respuesta;

import com.challenge.forohub.domain.respuesta.dto.DatosActualizarRespuesta;
import com.challenge.forohub.domain.respuesta.dto.DatosRegistroRespuesta;
import com.challenge.forohub.domain.respuesta.dto.DatosRetornoRespuesta;
import com.challenge.forohub.domain.topico.Topico;
import com.challenge.forohub.domain.topico.TopicoRepository;
import com.challenge.forohub.domain.usuario.Usuario;
import com.challenge.forohub.domain.usuario.UsuarioRepository;
import com.challenge.forohub.infra.errores.ValidacionRequerimientos;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class RespuestaService {
    private TopicoRepository topicoRepository;
    private UsuarioRepository usuarioRepository;
    private RespuestaRepository respuestaRepository;

    public RespuestaService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository,
                         RespuestaRepository respuestaRepository){
        this.topicoRepository = topicoRepository;
        this.respuestaRepository = respuestaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public DatosRetornoRespuesta registrarRespuesta(DatosRegistroRespuesta datosRegistroRespuesta,
                                                    Principal principal){
        //El usuario que envía el token se asigna como autor de la respuesta.
        Usuario usuario = (Usuario) usuarioRepository.findByCorreoElectronico(principal.getName());

        var topico = topicoRepository.findByIdActivoTrue(datosRegistroRespuesta.idTopico());
        if (topico == null){
            throw new EntityNotFoundException("Topico with id " + datosRegistroRespuesta.idTopico() + " not found");
        }
        Respuesta nuevaRespuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta, usuario, topico));

        DatosRetornoRespuesta retornoRespuesta = new DatosRetornoRespuesta(nuevaRespuesta);

        return retornoRespuesta;
    }

    public Page<DatosRetornoRespuesta> buscarRespuestasActivas(Long topicoId, Pageable paginacion) {
        //Lista por tópico
        if (topicoId != null){
            Page listaRespuestas = respuestaRepository.findByActivoTrueAndTopicoIdOrderByFechaCreacion(topicoId, paginacion);
            if (listaRespuestas.getContent().isEmpty()){
                throw new EntityNotFoundException("Id not found");
            }
            return respuestaRepository.findByActivoTrueAndTopicoIdOrderByFechaCreacion(topicoId, paginacion).map(DatosRetornoRespuesta::new);
        }

        return respuestaRepository.findByActivoTrueOrderByFechaCreacion(paginacion).map(DatosRetornoRespuesta::new);
    }

    public DatosRetornoRespuesta actualizarRespuesta(DatosActualizarRespuesta datosActualizarRespuesta,
                                                     Principal principal) {
        Usuario usuario = (Usuario) usuarioRepository.findByCorreoElectronico(principal.getName());
        Respuesta respuesta = respuestaRepository.findByIdActivoTrue(datosActualizarRespuesta.id());
        if (respuesta == null){
            throw new EntityNotFoundException("Respuesta with id " + datosActualizarRespuesta.id() + " not found");
        }
        //Solamente el autor de la respuesta puede modificarla
        if (usuario.getId() == respuesta.getAutor().getId()){
            respuesta.actualizarDatos(datosActualizarRespuesta);
            return new DatosRetornoRespuesta(respuesta);
        } else {
        throw new ValidacionRequerimientos("Unable to update a response from another author");
        }

    }

    public DatosRetornoRespuesta marcarSolucion(Long id, Principal principal){
        Usuario usuario = (Usuario) usuarioRepository.findByCorreoElectronico(principal.getName());
        Respuesta respuesta = respuestaRepository.findByIdActivoTrue(id);
        if (respuesta == null){
            throw new EntityNotFoundException("Respuesta with id " + id + " not found");
        }
        //Solamente el autor del tópico puede marcar la respuesta como solucíón
        if (usuario.getId() == respuesta.getTopico().getAutor().getId()){
            respuesta.marcarSolucion();
            return new DatosRetornoRespuesta(respuesta);
        } else {
            throw new ValidacionRequerimientos("Only the topic author can check the answer as a solution");
        }
    }

    public void borrarRespuesta(Long id, Principal principal) {
        //Usuario que solicita la acción
        Usuario usuario = (Usuario) usuarioRepository.findByCorreoElectronico(principal.getName());

        //Revisa si la respuesta existe
        Optional<Respuesta> respuesta = respuestaRepository.findById(id);
        if (respuesta.isEmpty()){
            throw new EntityNotFoundException("Respuesta with id " + id + " not found");
        }

        //Usuario con rol "administrador" tiene permitido borrar respuestas
        if(usuario.getPerfil().toString().equals("ADMINISTRADOR")){
            respuestaRepository.delete(respuesta.get());
        } else if (respuesta.get().getAutor().getId() == usuario.getId()) {
            //Borra la respuesta si el usuario que solicita la acción es el autor de la respuesta.
            respuestaRepository.delete(respuesta.get());
        } else {
            throw new ValidacionRequerimientos("Unable to erase a response from another author");
        }
    }

    public DatosRetornoRespuesta buscarRespuestaId(Long id) {
        Respuesta respuesta = respuestaRepository.findByIdActivoTrue(id);
        if (respuesta == null){
            throw new EntityNotFoundException("Respuesta with id " + id + " not found");
        }
        return new DatosRetornoRespuesta(respuesta);
    }
}
