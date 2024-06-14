package com.challenge.forohub.domain.respuesta;

import com.challenge.forohub.domain.curso.CursoRepository;
import com.challenge.forohub.domain.respuesta.dto.DatosActualizarRespuesta;
import com.challenge.forohub.domain.respuesta.dto.DatosRegistroRespuesta;
import com.challenge.forohub.domain.respuesta.dto.DatosRetornoRespuesta;
import com.challenge.forohub.domain.topico.Topico;
import com.challenge.forohub.domain.topico.TopicoRepository;
import com.challenge.forohub.domain.topico.dto.DatosActualizarTopico;
import com.challenge.forohub.domain.topico.dto.DatosRegistroTopico;
import com.challenge.forohub.domain.topico.dto.DatosRespuestaTopico;
import com.challenge.forohub.domain.usuario.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public DatosRetornoRespuesta registrarRespuesta(DatosRegistroRespuesta datosRegistroRespuesta){
        var usuario = usuarioRepository.getReferenceById(datosRegistroRespuesta.idAutor());
        var topico = topicoRepository.getReferenceById(datosRegistroRespuesta.idTopico());
        Respuesta nuevaRespuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta, usuario, topico));

        DatosRetornoRespuesta retornoRespuesta = new DatosRetornoRespuesta(nuevaRespuesta);

        return retornoRespuesta;
    }

    public Page<DatosRetornoRespuesta> buscarRespuestasActivas(Pageable paginacion) {
        return respuestaRepository.findByActivoTrue(paginacion).map(DatosRetornoRespuesta::new);
    }

    public DatosRetornoRespuesta actualizarRespuesta(DatosActualizarRespuesta datosActualizarRespuesta) {
        Respuesta respuesta = respuestaRepository.getReferenceById(datosActualizarRespuesta.id());
        respuesta.actualizarDatos(datosActualizarRespuesta);
        return new DatosRetornoRespuesta(respuesta);
    }

    public DatosRetornoRespuesta marcarSolucion(Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.marcarSolucion();
        return new DatosRetornoRespuesta(respuesta);
    }

    public void desactivarRespuesta(Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.desactivarRespuesta();
    }

    public DatosRetornoRespuesta buscarRespuestaId(Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        return new DatosRetornoRespuesta(respuesta);
    }
}
