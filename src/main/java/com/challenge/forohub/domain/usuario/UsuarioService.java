package com.challenge.forohub.domain.usuario;

import com.challenge.forohub.domain.usuario.dto.DatosActualizarUsuario;
import com.challenge.forohub.domain.usuario.dto.DatosRegistroUsuario;
import com.challenge.forohub.domain.usuario.dto.DatosRespuestaUsuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public DatosRespuestaUsuario registrarNuevoUsuario(DatosRegistroUsuario datosRegistroUsuario) {
        Usuario nuevoUsuario = new Usuario(datosRegistroUsuario);
        //Encriptar contraseña
        String contrasenaEncriptada = passwordEncoder.encode(nuevoUsuario.getContrasena());
        nuevoUsuario.setContrasena(contrasenaEncriptada);
        //Guardar nuevo usuario
        usuarioRepository.save(nuevoUsuario);
        //Crear DTO para enviar la respuesta
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(nuevoUsuario.getId(),
                nuevoUsuario.getNombre(), nuevoUsuario.getCorreoElectronico(),
                nuevoUsuario.getPerfil().toString().toLowerCase());
        return datosRespuestaUsuario;

    }

    public Page<DatosRespuestaUsuario> buscarUsuariosActivos(Pageable paginacion) {

        return usuarioRepository.findByActivoTrue(paginacion).map(DatosRespuestaUsuario::new);
    }

    public DatosRespuestaUsuario actualizarUsuario(DatosActualizarUsuario datosActualizarUsuario) {
        Usuario usuario = usuarioRepository.findByIdActivoTrue(datosActualizarUsuario.id());
        if (usuario == null){
            throw new EntityNotFoundException("Usuario with id " + datosActualizarUsuario.id() + " not found");
        }
        usuario.actualizarDatos(datosActualizarUsuario);
        return new DatosRespuestaUsuario(usuario.getId(), usuario.getNombre(), usuario.getCorreoElectronico(),
                usuario.getPerfil().toString().toLowerCase());
    }

    public void desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findByIdActivoTrue(id);
        if (usuario == null){
            throw new EntityNotFoundException("Usuario with id " + id + " not found");
        }
        usuario.desactivarUsuario();
    }

    public DatosRespuestaUsuario buscarUsuarioId(Long id) {
        Usuario usuario = usuarioRepository.findByIdActivoTrue(id);
        if (usuario == null){
            throw new EntityNotFoundException("Usuario with id " + id + " not found");
        }
        return new DatosRespuestaUsuario(usuario);
    }
}
