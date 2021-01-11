package br.com.attomtech.talbosch.api.model.enums;

public enum TipoCliente 
{
    CLIENTE("Cliente"),
    TECNICO("Técnico");
    
    private String descricao;

    private TipoCliente( String descricao )
    {
        this.descricao = descricao;
    }

    public String getDescricao( )
    {
        return descricao;
    }
}
