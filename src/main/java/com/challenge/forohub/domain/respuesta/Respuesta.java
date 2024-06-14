package com.challenge.forohub.domain.respuesta;

import com.challenge.forohub.domain.respuesta.dto.DatosActualizarRespuesta;
import com.challenge.forohub.domain.respuesta.dto.DatosRegistroRespuesta;
import com.challenge.forohub.domain.topico.Topico;
import com.challenge.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "respuestas")
@Entity(name = "respuesta")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;
    private Boolean solucion;
    private Boolean activo;

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta, Usuario autor, Topico topico) {
        this.mensaje = datosRegistroRespuesta.mensaje();
        this.topico = topico;
        this.fechaCreacion = datosRegistroRespuesta.fechaCreacion();
        this.autor = autor;
        this.activo = true;
        this.solucion = false;
    }

    public void actualizarDatos(DatosActualizarRespuesta datosActualizarRespuesta) {
        if (datosActualizarRespuesta.mensaje() != null){
            this.mensaje = datosActualizarRespuesta.mensaje();
        }
    }

    public void desactivarRespuesta() {
        this.activo = false;
    }

    public void marcarSolucion() {
        this.solucion = true;
    }
}
