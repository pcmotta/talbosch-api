CREATE TABLE mensagem (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_usuario_origem BIGINT(20) NOT NULL,
	codigo_usuario_destino BIGINT(20) NOT NULL,
	mensagem TEXT NOT NULL,
	lido BOOLEAN NOT NULL DEFAULT FALSE,
	data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	deletado_origem	BOOLEAN NOT NULL DEFAULT FALSE,
	deletado_destino BOOLEAN NOT NULL DEFAULT FALSE,
	CONSTRAINT FK_mensagem_codigo_usuario_origem FOREIGN KEY (codigo_usuario_origem) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_mensagem_codigo_usuario_destino FOREIGN KEY (codigo_usuario_destino) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;