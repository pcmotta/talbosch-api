package br.com.attomtech.talbosch.api.model.enums;

public enum StatusPedido 
{
    AGUARDANDO( "Aguardando Entrega" ),
    CONCLUIDO( "Pedido Concluído" );
    
    private String descricao;
    
    StatusPedido( String descricao )
    {
        this.descricao = descricao;
    }
    
    public String getDescricao( )
    {
        return this.descricao;
    }
}
