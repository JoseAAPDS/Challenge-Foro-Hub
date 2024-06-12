CREATE TABLE respuestas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje VARCHAR(500) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    topico_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    solucion BOOLEAN,
    activo BOOLEAN,
    PRIMARY KEY(id),
    CONSTRAINT fk_respuestas_topico_id FOREIGN KEY (topico_id) REFERENCES topicos(id),
    CONSTRAINT fk_respuestas_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);