CREATE TABLE log_de_atividades (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	area VARCHAR(10),
	acao VARCHAR(10),
	codigo_objeto BIGINT(20),
	codigo_peca VARCHAR(30),
	data DATETIME NOT NULL,
	codigo_usuario BIGINT(20) NOT NULL,
	texto TEXT,
	CONSTRAINT FK_log_de_atividades_codigo_usuario FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;