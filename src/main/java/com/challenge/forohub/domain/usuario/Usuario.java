package com.challenge.forohub.domain.usuario;

import com.challenge.forohub.domain.usuario.dto.DatosActualizarUsuario;
import com.challenge.forohub.domain.usuario.dto.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "usuarios")
@Entity(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String correoElectronico;
    private String contrasena;
    @Enumerated(EnumType.STRING)
    private Perfiles perfil;
    private Boolean activo;

    public Usuario(DatosRegistroUsuario datosRegistroUsuario) {
        this.nombre = datosRegistroUsuario.nombre();
        this.correoElectronico = datosRegistroUsuario.correoElectronico();
        this.contrasena = datosRegistroUsuario.contrasena();
        this.perfil = datosRegistroUsuario.perfil();
        this.activo = true;
    }

    public void actualizarDatos(DatosActualizarUsuario datosActualizarUsuario) {
        if (datosActualizarUsuario.nombre() != null){
            this.nombre = datosActualizarUsuario.nombre();
        }
        if (datosActualizarUsuario.correoElectronico() != null){
            this.correoElectronico = datosActualizarUsuario.correoElectronico();
        }

    }

    public void desactivarUsuario() {
        this.activo = false;
    }
}
