package br.com.attomtech.talbosch.api.model.enums;

public enum AreaLog 
{
    CLIENTES("Cadastro de Clientes"),
    ORDEM("Ordens de Serviço"),
    PECAS("Cadastro de Peças"),
    ESTOQUE("Estoque"),
    TECNICO("Cadastro de Técnicos"),
    USUARIO("Cadastro de Usuários"),
    PEDIDOS("Cadastro de Pedidos");
    
    private String descricao;
    
    AreaLog( String descricao )
    {
        this.descricao = descricao;
    }
    
    public String getDescricao( )
    {
        return this.descricao;
    }
}
