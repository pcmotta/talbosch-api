package br.com.attomtech.talbosch.api.model.enums;

public enum StatusOrdem 
{
    EMABERTO("Em Aberto"),
    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada");
    
    private String descricao;

    private StatusOrdem( String descricao )
    {
        this.descricao = descricao;
    }

    public String getDescricao( )
    {
        return descricao;
    }
}
