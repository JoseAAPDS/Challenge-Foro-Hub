package com.challenge.forohub.domain.respuesta;

import com.challenge.forohub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Page<Respuesta> findByActivoTrueOrderByFechaCreacion(Pageable paginacino);

    @Query("""
            select r from respuesta r
            where r.activo = true and
            r.id = :id
            """)
    Respuesta findByIdActivoTrue(Long id);

    List<Respuesta> findByTopicoId(Long id);
}
