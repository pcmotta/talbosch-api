package br.com.attomtech.talbosch.api.model.enums;

public enum AcaoLog
{
    CADASTRO("Cadastro"),
    ALTERACAO("Alteração"),
    EXCLUSAO("Exclusão"),
    LOGIN("Login"),
    LOGOUT("Logout");
    
    private String descricao;
    
    AcaoLog( String descricao )
    {
        this.descricao = descricao;
    }
    
    public String getDescricao( )
    {
        return this.descricao;
    }
}
