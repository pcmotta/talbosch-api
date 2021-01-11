CREATE TABLE ordem_servico (
	numero BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_cliente BIGINT(20) NOT NULL,
	rnm VARCHAR(30),
	tipo VARCHAR(12) NOT NULL,
	data_chamada DATE,
	data_atendimento DATE,
	codigo_atendente BIGINT(20) NOT NULL,
	observacao TEXT,
	baixa TEXT,
	data_baixa DATETIME,
	codigo_tecnico BIGINT(20),
	status VARCHAR(20) NOT NULL, 
	incluido_por BIGINT(20),
	incluido_em DATETIME,
	alterado_por BIGINT(20),
	alterado_em DATETIME,
	CONSTRAINT FK_ordem_servico_codigo_cliente FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_ordem_servico_atendente FOREIGN KEY (codigo_atendente) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_ordem_servico_tecnico FOREIGN KEY (codigo_tecnico) REFERENCES tecnico(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_ordem_servico_incluido_por FOREIGN KEY (incluido_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_ordem_servico_alterado_por FOREIGN KEY (alterado_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE ordem_servico_endereco (
	ordem_servico BIGINT(20) PRIMARY KEY,
	logradouro VARCHAR(150) NOT NULL,
	numero VARCHAR(20),
	bairro VARCHAR(40),
	municipio VARCHAR(40),
	estado VARCHAR(2),
	complemento VARCHAR(40),
	cep BIGINT(20),
	proximidade TEXT,
	CONSTRAINT FK_ordem_servico_endereco_ordem_servico FOREIGN KEY (ordem_servico) REFERENCES ordem_servico(numero) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE ordem_servico_produto (
	ordem_servico BIGINT(20) PRIMARY KEY,
	aparelho VARCHAR(15) NOT NULL,
	fabricante VARCHAR(10) NOT NULL,
	defeito TEXT,
	nota_fiscal VARCHAR(30),
	emissao_nota_fiscal DATE,
	cor VARCHAR(30),
	modelo VARCHAR(20),
	fab_serie VARCHAR(40),
	modelo_evaporadora VARCHAR(20),
	fab_serie_evaporadora VARCHAR(40),
	tensao INT(3),
	revendedor VARCHAR(50),
	CONSTRAINT FK_ordem_servico_produto_ordem_servico FOREIGN KEY (ordem_servico) REFERENCES ordem_servico(numero) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE ordem_servico_valor (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	ordem_servico BIGINT(20) NOT NULL,
	tipo VARCHAR(20) NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
	CONSTRAINT FK_ordem_servico_valores_ordem_servico FOREIGN KEY (ordem_servico) REFERENCES ordem_servico(numero) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE ordem_servico_andamento (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	ordem_servico BIGINT(20) NOT NULL,
	texto TEXT NOT NULL,
	codigo_usuario BIGINT(20) NOT NULL,
	data DATETIME NOT NULL,
	CONSTRAINT FK_ordem_servico_andamento_ordem_servico FOREIGN KEY (ordem_servico) REFERENCES ordem_servico(numero) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_ordem_servico_andamento_codigo_usuario FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;