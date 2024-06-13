package com.challenge.forohub.domain.usuario;

import com.challenge.forohub.domain.usuario.dto.DatosActualizarUsuario;
import com.challenge.forohub.domain.usuario.dto.DatosRegistroUsuario;
import com.challenge.forohub.domain.usuario.dto.DatosRespuestaUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public DatosRespuestaUsuario registrarNuevoUsuario(DatosRegistroUsuario datosRegistroUsuario) {
        Usuario nuevoUsuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(nuevoUsuario.getId(),
                nuevoUsuario.getNombre(), nuevoUsuario.getCorreoElectronico(),
                nuevoUsuario.getPerfil().toString().toLowerCase());
        return datosRespuestaUsuario;

    }

    public Page<DatosRespuestaUsuario> buscarUsuariosActivos(Pageable paginacion) {

        return usuarioRepository.findByActivoTrue(paginacion).map(DatosRespuestaUsuario::new);
    }

    public DatosRespuestaUsuario actualizarUsuario(DatosActualizarUsuario datosActualizarUsuario) {
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());
        usuario.actualizarDatos(datosActualizarUsuario);
        return new DatosRespuestaUsuario(usuario.getId(), usuario.getNombre(), usuario.getCorreoElectronico(),
                usuario.getPerfil().toString().toLowerCase());
    }

    public void desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.desactivarUsuario();
    }

    public DatosRespuestaUsuario buscarUsuarioId(Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        return new DatosRespuestaUsuario(usuario);
    }
}
