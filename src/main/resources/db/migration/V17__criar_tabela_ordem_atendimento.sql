CREATE TABLE ordem_servico_atendimento (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	ordem_servico BIGINT(20) NOT NULL,
	data_atendimento DATE NOT NULL,
	observacoes TEXT,
	codigo_tecnico BIGINT(20),
	CONSTRAINT FK_ordem_servico_atendimento_ordem_servico FOREIGN KEY (ordem_servico) REFERENCES ordem_servico(numero) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_ordem_servico_atendimento_tecnico FOREIGN KEY (codigo_tecnico) REFERENCES tecnico(codigo) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;