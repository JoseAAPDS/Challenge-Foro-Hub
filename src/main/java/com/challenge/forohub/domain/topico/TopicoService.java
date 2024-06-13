package com.challenge.forohub.domain.topico;

import com.challenge.forohub.domain.curso.Curso;
import com.challenge.forohub.domain.topico.dto.DatosRegistroTopico;
import com.challenge.forohub.domain.topico.dto.DatosRespuestaTopico;
import com.challenge.forohub.domain.usuario.Perfiles;
import com.challenge.forohub.domain.usuario.Usuario;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {
    private TopicoRepository topicoRepository;

    public TopicoService(TopicoRepository topicoRepository){
        this.topicoRepository = topicoRepository;
    }

    public DatosRespuestaTopico registrarNuevoTopico(DatosRegistroTopico datosRegistroTopico){
        var usuario = new Usuario(1L,"Alejandra Cueva", "alejandra.cueva@correo.com", "contraseña", Perfiles.ESTUDIANTE, true);
        var curso = new Curso(1L,"Spring Boot", "Programación");
        Topico nuevoTopico = topicoRepository.save(new Topico(datosRegistroTopico, usuario, curso));

        DatosRespuestaTopico respuestaTopico = new DatosRespuestaTopico(nuevoTopico.getId(), nuevoTopico.getTitulo(),
                nuevoTopico.getMensaje(),nuevoTopico.getFechaCreacion().toString(), nuevoTopico.getAutor().getNombre(),
                nuevoTopico.getCurso().getNombre());

        return respuestaTopico;
    }
}
