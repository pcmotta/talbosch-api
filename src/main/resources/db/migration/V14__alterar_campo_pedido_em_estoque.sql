ALTER TABLE estoque MODIFY pedido BIGINT(20) NULL;
ALTER TABLE estoque ADD CONSTRAINT FK_estoque_pedido FOREIGN KEY (pedido) REFERENCES pedido(codigo) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE estoque DROP COLUMN nota_fiscal;
ALTER TABLE estoque DROP COLUMN chegada_nota_fiscal;
ALTER TABLE estoque DROP COLUMN emissao_nota_fiscal;