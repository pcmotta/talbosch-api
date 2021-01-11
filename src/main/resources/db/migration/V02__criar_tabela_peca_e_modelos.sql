CREATE TABLE peca (
	codigo VARCHAR(30) PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL,
	valor DECIMAL(10,2),
	valor_tecnico DECIMAL(10,2),
	valor_mo DECIMAL(10,2),
	aparelho VARCHAR(20) NOT NULL,
	fabricante VARCHAR(20) NOT NULL,
	ativo BOOLEAN NOT NULL DEFAULT TRUE,
	incluido_por BIGINT(20),
	incluido_em DATETIME,
	alterado_por BIGINT(20),
	alterado_em DATETIME,
	CONSTRAINT FK_peca_incluido_por FOREIGN KEY (incluido_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_peca_alterado_por FOREIGN KEY (alterado_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE modelo(
	codigo_peca VARCHAR(30) NOT NULL,
	modelo VARCHAR(30) NOT NULL,
	CONSTRAINT PK_modelo PRIMARY KEY (codigo_peca, modelo),
	CONSTRAINT FK_modelo_codigo_peca FOREIGN KEY (codigo_peca) REFERENCES peca(codigo) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;