package br.com.attomtech.talbosch.api.model.enums;

public enum Genero 
{
    MASCULINO("Masculino"),
    FEMININO("Feminino");
    
    private String descricao;

    Genero( String descricao )
    {
        this.descricao = descricao;
    }

    public String getDescricao( )
    {
        return descricao;
    }
}
