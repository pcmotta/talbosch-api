package br.com.attomtech.talbosch.api.model.enums;

public enum TipoEstoque 
{
    GARANTIA("Garantia"),
    FORAGARANTIA("Fora de Garantia"),
    BUFFER("Buffer");
    
    private String descricao;

    private TipoEstoque( String descricao )
    {
        this.descricao = descricao;
    }

    public String getDescricao( )
    {
        return descricao;
    }
}
