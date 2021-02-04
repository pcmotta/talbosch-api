CREATE TABLE usuario (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	login VARCHAR(20) NOT NULL UNIQUE,
	senha VARCHAR(100) NOT NULL,
	ultimo_acesso DATETIME DEFAULT NULL,
	ativo BOOLEAN DEFAULT TRUE,
	incluido_por BIGINT(20),
	incluido_em DATETIME DEFAULT CURRENT_TIMESTAMP,
	alterado_por BIGINT(20),
	alterado_em DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	CONSTRAINT FK_usuario_incluido_por FOREIGN KEY (incluido_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_usuario_alterado_por FOREIGN KEY (alterado_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	INDEX IDX_usuario_login (login)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario (nome, login, senha) VALUES ('Administrador', 'admin', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');

CREATE TABLE usuario_permissao (
	codigo_usuario BIGINT(20) NOT NULL,
	permissao VARCHAR(20) NOT NULL,
	CONSTRAINT PK_usuario_permissao PRIMARY KEY (codigo_usuario, permissao),
	CONSTRAINT FK_usuario_permissao_codigo_usuario FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario_permissao (codigo_usuario, permissao) VALUES (1, 'ADMINISTRADOR');

