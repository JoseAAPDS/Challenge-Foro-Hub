package com.challenge.forohub.domain.usuario;

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
    @Enumerated
    private Perfiles perfil;
    private Boolean activo;

//    public Usuario(Long id, String nombre, String correoElectronico, String contrasena, Perfiles perfil, Boolean activo) {
//        this.id = id;
//        this.nombre = nombre;
//        this.correoElectronico = correoElectronico;
//        this.contrasena = contrasena;
//        this.perfil = perfil;
//        this.activo = activo;
//    }
}
