package br.com.attomtech.talbosch.api.model.enums;

public enum TipoOrdem 
{
    GARANTIA("Garantia"),
    FORAGARANTIA("Fora de Garantia");
    
    private String descricao;

    private TipoOrdem( String descricao )
    {
        this.descricao = descricao;
    }

    public String getDescricao( )
    {
        return descricao;
    }
}
