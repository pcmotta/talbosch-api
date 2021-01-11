package br.com.attomtech.talbosch.api.model.enums;

public enum TipoPessoa 
{
    FISICA("Pessoa Física"),
    JURIDICA("Pessoa Jurídica");
    
    private String descricao;
    
    TipoPessoa( String descricao )
    {
        this.descricao = descricao;
    }
    
    public String getDescricao( )
    {
        return this.descricao;
    }
}
