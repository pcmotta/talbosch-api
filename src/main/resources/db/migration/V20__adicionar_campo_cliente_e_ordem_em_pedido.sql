ALTER TABLE pedido ADD CONSTRAINT FK_pedido_incluido_por FOREIGN KEY (incluido_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE pedido ADD CONSTRAINT FK_pedido_alterado_por FOREIGN KEY (alterado_por) REFERENCES usuario(codigo) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE pedido ADD codigo_cliente BIGINT(20) NULL;
ALTER TABLE pedido ADD ordem_servico BIGINT(20) NULL;

ALTER TABLE pedido ADD CONSTRAINT FK_pedido_codigo_cliente FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE pedido ADD CONSTRAINT FK_pedido_ordem_servico FOREIGN KEY (ordem_servico) REFERENCES ordem_servico(numero) ON DELETE RESTRICT ON UPDATE CASCADE;