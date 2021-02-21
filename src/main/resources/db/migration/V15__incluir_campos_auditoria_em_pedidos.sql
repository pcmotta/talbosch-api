ALTER TABLE pedido ADD incluido_por BIGINT(20) NOT NULL;
ALTER TABLE pedido ADD incluido_em DATETIME NOT NULL;
ALTER TABLE pedido ADD alterado_por BIGINT(20);
ALTER TABLE pedido ADD alterado_em DATETIME;