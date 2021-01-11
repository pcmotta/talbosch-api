CREATE TABLE estoque (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_peca VARCHAR(30) NOT NULL,
	codigo_cliente BIGINT(20),
	ordem_servico BIGINT(20),
	rnm VARCHAR(20),
	pedido VARCHAR(20),
	codigo_tecnico BIGINT(20),
	nota_fiscal VARCHAR(20),
	emissao_nota_fiscal DATE,
	chegada_nota_fiscal DATE,
	modelo VARCHAR(25),
	valor DECIMAL(10,2),
	tipo VARCHAR(20) NOT NULL,
	status VARCHAR(20) NOT NULL,
	agendado_por BIGINT(20),
	agendado_para DATE,
	incluido_por BIGINT(20),
	incluido_em DATETIME,
	alterado_por BIGINT(20),
	alterado_em DATETIME,
	CONSTRAINT FK_estoque_codigo_peca FOREIGN KEY (codigo_peca) REFERENCES peca(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_estoque_codigo_cliente FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_estoque_ordem_servico FOREIGN KEY (ordem_servico) REFERENCES ordem_servico(numero) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_estoque_codigo_tecnico FOREIGN KEY (codigo_tecnico) REFERENCES tecnico(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_estoque_agendado_por FOREIGN KEY (agendado_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_estoque_incluido_por FOREIGN KEY (incluido_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_estoque_alterado_por FOREIGN KEY (alterado_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE estoque_observacao (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_estoque BIGINT(20) NOT NULL,
	texto TEXT NOT NULL,
	codigo_usuario BIGINT(20) NOT NULL,
	data DATETIME NOT NULL,
	CONSTRAINT FK_estoque_observacao_codigo_estoque FOREIGN KEY (codigo_estoque) REFERENCES estoque(codigo) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_estoque_observacao_codigo_usuario FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE estoque_telefone (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_estoque BIGINT(20) NOT NULL,
	numero BIGINT(20) NOT NULL,
	operadora VARCHAR(20),
	CONSTRAINT FK_estoque_telefone_codigo_estoque FOREIGN KEY (codigo_estoque) REFERENCES estoque(codigo) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;