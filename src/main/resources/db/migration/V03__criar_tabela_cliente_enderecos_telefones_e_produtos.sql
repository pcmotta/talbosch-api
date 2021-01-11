CREATE TABLE cliente (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	cpf_cnpj BIGINT(20) UNIQUE,
	tipo_pessoa VARCHAR(8) NOT NULL,
	email VARCHAR(60),
	inscricao_estadual VARCHAR(20),
	inscricao_municipal VARCHAR(20),
	pessoa_contato VARCHAR(50),
	genero VARCHAR(10) NOT NULL,
	tipo_cliente VARCHAR(7) NOT NULL,
	bandeira_vermelha BOOLEAN NOT NULL DEFAULT FALSE,
	motivo TEXT,
	ativo BOOLEAN NOT NULL DEFAULT TRUE,
	incluido_por BIGINT(20),
	incluido_em DATETIME,
	alterado_por BIGINT(20),
	alterado_em DATETIME,
	CONSTRAINT FK_cliente_incluido_por FOREIGN KEY (incluido_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_cliente_alterado_por FOREIGN KEY (alterado_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE,
	INDEX IDX_cliente_cpf_cnpj (cpf_cnpj)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE endereco (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_cliente BIGINT(20) NOT NULL,
	logradouro VARCHAR(150) NOT NULL,
	numero VARCHAR(20),
	complemento VARCHAR(50),
	bairro VARCHAR(40),
	municipio VARCHAR(40),
	estado VARCHAR(2),
	cep BIGINT(9),
	proximidade TEXT,
	CONSTRAINT FK_endereco_codigo_cliente FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE telefone (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_cliente BIGINT(20) NOT NULL,
	numero BIGINT(20) NOT NULL,
	operadora VARCHAR(20),
	observacoes VARCHAR(100),
	CONSTRAINT FK_telefone_codigo_cliente FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE produto (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_cliente BIGINT(20) NOT NULL,
	aparelho VARCHAR(20) NOT NULL,
	fabricante VARCHAR(15) NOT NULL,
	nota_fiscal VARCHAR(30),
	emissao_nota_fiscal DATE,
	cor VARCHAR(15),
	modelo VARCHAR(20),
	fab_serie VARCHAR(40),
	modelo_evaporadora VARCHAR(20),
	fab_serie_evaporadora VARCHAR(40),
	tensao INT(3),
	revendedor VARCHAR(50),
	CONSTRAINT FK_produto_codigo_cliente FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;