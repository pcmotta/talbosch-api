package br.com.attomtech.talbosch.api.model.enums;

public enum StatusOrdem 
{
    EMABERTO("Em Aberto", 1),
    AGUARDANDOPECA("Aguardando Peça", 2),
    FINALIZADA("Finalizada", 3),
    CANCELADA("Cancelada", 4);
    
    private String descricao;
    private int ordem;

    private StatusOrdem( String descricao, int ordem )
    {
        this.descricao = descricao;
        this.ordem = ordem;
    }

    public String getDescricao( )
    {
        return descricao;
    }
    
    public int getOrdem( )
    {
        return ordem;
    }
}
