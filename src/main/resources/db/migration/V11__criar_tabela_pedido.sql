CREATE TABLE pedido (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	pedido VARCHAR(20) NOT NULL,
	nota_fiscal VARCHAR(20),
	data DATE NOT NULL,
	emissao_nota_fiscal DATE,
	chegada_nota_fiscal DATE,
	pedido_por BIGINT(20) NOT NULL,
	CONSTRAINT FK_pedido_usuario FOREIGN KEY (pedido_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;