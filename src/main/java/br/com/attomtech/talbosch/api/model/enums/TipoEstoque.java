package br.com.attomtech.talbosch.api.model.enums;

public enum TipoEstoque 
{
    GARANTIA("Garantia", "success"),
    FORAGARANTIA("Fora de Garantia", "warning"),
    BUFFER("Buffer", "danger");
    
    private String descricao;
    private String color;

    private TipoEstoque( String descricao, String color )
    {
        this.descricao = descricao;
        this.color = color;
    }

    public String getDescricao( )
    {
        return descricao;
    }
    
    public String getColor( )
    {
        return color;
    }
}
