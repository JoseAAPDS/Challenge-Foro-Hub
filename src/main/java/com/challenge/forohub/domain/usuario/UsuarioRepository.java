package com.challenge.forohub.domain.usuario;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByActivoTrue(Pageable paginacion);

    @Query("""
            select u from usuario u
            where u.activo = true and
            u.id = :id
            """)
    Usuario findByIdActivoTrue(Long id);

    UserDetails findByCorreoElectronico(String correoElectronico);
}
