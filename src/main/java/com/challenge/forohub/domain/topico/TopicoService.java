package com.challenge.forohub.domain.topico;

import com.challenge.forohub.domain.curso.Curso;
import com.challenge.forohub.domain.curso.CursoRepository;
import com.challenge.forohub.domain.topico.dto.DatosActualizarTopico;
import com.challenge.forohub.domain.topico.dto.DatosRegistroTopico;
import com.challenge.forohub.domain.topico.dto.DatosRespuestaTopico;
import com.challenge.forohub.domain.usuario.Perfiles;
import com.challenge.forohub.domain.usuario.Usuario;
import com.challenge.forohub.domain.usuario.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {
    private TopicoRepository topicoRepository;
    private CursoRepository cursoRepository;
    private UsuarioRepository usuarioRepository;

    public TopicoService(TopicoRepository topicoRepository, CursoRepository cursoRepository,
                         UsuarioRepository usuarioRepository){
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public DatosRespuestaTopico registrarNuevoTopico(DatosRegistroTopico datosRegistroTopico){
        var usuario = usuarioRepository.getReferenceById(datosRegistroTopico.idAutor());
        var curso = cursoRepository.getReferenceById(datosRegistroTopico.idCurso());
        Topico nuevoTopico = topicoRepository.save(new Topico(datosRegistroTopico, usuario, curso));

        DatosRespuestaTopico respuestaTopico = new DatosRespuestaTopico(nuevoTopico.getId(), nuevoTopico.getTitulo(),
                nuevoTopico.getMensaje(),nuevoTopico.getFechaCreacion().toString(), nuevoTopico.getAutor().getNombre(),
                nuevoTopico.getCurso().getNombre());

        return respuestaTopico;
    }

    public Page<DatosRespuestaTopico> buscarTopicosActivos(Pageable paginacion) {
        return topicoRepository.findByActivoTrue(paginacion).map(DatosRespuestaTopico::new);
    }

    public DatosRespuestaTopico actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        return new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(),topico.getFechaCreacion().toString(), topico.getAutor().getNombre(),
                topico.getCurso().getNombre());
    }

    public void desactivarTopico(Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
    }

    public DatosRespuestaTopico buscarTopicoId(Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        return new DatosRespuestaTopico(topico);
    }
}
