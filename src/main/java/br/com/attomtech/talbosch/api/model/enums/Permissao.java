package br.com.attomtech.talbosch.api.model.enums;

public enum Permissao 
{
    ADMINISTRADOR("Administrador"),
    ESTOQUE("Estoque"),
    CADASTROPECA("Cadastro de Peças"),
    ORDEMSERVICO("Ordem de Serviço"),
    CLIENTES("Clientes"),
    LOG("Log de Atividades"),
    USUARIO("Cadastro de Usuários");
    
    private String descricao;

    private Permissao( String descricao )
    {
        this.descricao = descricao;
    }

    public String getDescricao( )
    {
        return descricao;
    }
}
