package com.challenge.forohub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;


public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByActivoTrueOrderByFechaCreacion(Pageable paginacion);
    Page<Topico> findByActivoTrueAndCursoIdOrderByFechaCreacion(Long cursoId, Pageable paginacion);
    @Query("""
            select t from topico t join t.curso c
            where t.fechaCreacion between :fechaInicio and :fechaFinal
            and t.activo = true
            and c.id = :id
            order by t.fechaCreacion
            """)
    Page<Topico> encontrarPorCursoActivoEnAnio(Long id, LocalDateTime fechaInicio,
                                               LocalDateTime fechaFinal, Pageable paginacion);

    @Query("""
            select t from topico t
            where t.fechaCreacion between :fechaInicio and :fechaFinal
            and t.activo = true
            order by t.fechaCreacion
            """)
    Page<Topico> findByActivoTrueAndFechaCreacionOrderByFechaCreacion(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Pageable paginacion);

    @Query("""
            select t from topico t
            where t.activo = true and
            t.id = :id
            """)
    Topico findByIdActivoTrue(Long id);
}
