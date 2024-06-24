package com.challenge.forohub.infra.security;

import com.challenge.forohub.domain.usuario.UsuarioRepository;
import com.challenge.forohub.domain.usuario.dto.DatosAutenticacioUsuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    private UsuarioRepository usuarioRepository;


    public AuthenticationService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws EntityNotFoundException {
        return usuarioRepository.findByCorreoElectronico(correoElectronico);
    }


}
