
SET FOREIGN_KEY_CHECKS = 0;

-- 1. Tablas de auditoría (dependen de libro, usuario)
TRUNCATE TABLE auditoria_usuario;
TRUNCATE TABLE auditoria_libro;

-- 2. Tablas que dependen de usuario o libro
TRUNCATE TABLE usuario_respuesta;
TRUNCATE TABLE reserva;
TRUNCATE TABLE prestamo;

-- 4. Tablas catálogos (independientes)
TRUNCATE TABLE categoria;
TRUNCATE TABLE pregunta_seguridad;
TRUNCATE TABLE configuracion;
TRUNCATE TABLE estado_reserva_catalogo;
TRUNCATE TABLE estado_prestamo_catalogo;
TRUNCATE TABLE estado_usuario_catalogo;
TRUNCATE TABLE tipo_usuario_catalogo;


-- 3. Tablas principales
TRUNCATE TABLE libro;
TRUNCATE TABLE usuario;


SET FOREIGN_KEY_CHECKS = 1;
