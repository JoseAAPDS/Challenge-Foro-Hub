package com.challenge.forohub.domain.curso;

import com.challenge.forohub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Page<Curso> findByActivoTrue(Pageable paginacion);
    @Query("""
            select c from curso c
            where c.activo = true and
            c.id = :id
            """)
    Curso findByIdActivoTrue(Long id);
}
