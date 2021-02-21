ALTER TABLE tecnico ADD incluido_por BIGINT(20);
ALTER TABLE tecnico ADD incluido_em DATETIME;
ALTER TABLE tecnico ADD alterado_por BIGINT(20);
ALTER TABLE tecnico ADD alterado_em DATETIME;
ALTER TABLE tecnico ADD	CONSTRAINT FK_tecnico_incluido_por FOREIGN KEY (incluido_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE tecnico ADD CONSTRAINT FK_tecnico_alterado_por FOREIGN KEY (alterado_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE;