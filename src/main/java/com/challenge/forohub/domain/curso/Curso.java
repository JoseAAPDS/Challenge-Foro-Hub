package com.challenge.forohub.domain.curso;

import com.challenge.forohub.domain.curso.dto.DatosActualizarCurso;
import com.challenge.forohub.domain.curso.dto.DatosRegistroCurso;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table (name = "cursos")
@Entity (name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;
    private Boolean activo;

    public Curso(DatosRegistroCurso datosRegistroCurso) {
        this.nombre = datosRegistroCurso.nombre();
        this.categoria = datosRegistroCurso.categoria();
        this.activo = true;
    }

    public void actualizarCurso(DatosActualizarCurso datosActualizarCurso) {
        if(datosActualizarCurso.nombre() != null) {
            this.nombre = datosActualizarCurso.nombre();
        }
        if (datosActualizarCurso.categoria() != null) {
            this.categoria = datosActualizarCurso.categoria();
        }
    }

    public void borrarCurso() {
        this.activo = false;
    }
}
