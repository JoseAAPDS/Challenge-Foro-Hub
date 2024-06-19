package com.challenge.forohub.domain.usuario;

import com.challenge.forohub.domain.usuario.dto.DatosActualizarUsuario;
import com.challenge.forohub.domain.usuario.dto.DatosRegistroUsuario;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "usuarios")
@Entity(name = "usuario")
public class Usuario implements UserDetails {
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.toString()));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}
