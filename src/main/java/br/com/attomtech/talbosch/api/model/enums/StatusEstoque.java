package br.com.attomtech.talbosch.api.model.enums;

public enum StatusEstoque 
{
    ACHEGAR("A Chegar", 1),
    EMESTOQUE("Em Estoque", 2),
    USADA("Usada", 4),
    USADABALCAO("Usada no Balcão", 5),
    USADAENTREGUE("Entreque", 6),
    BALCAO("Balcão", 3),
    NAOBUSCADA("Não Buscada", 7),
    DEVOLUCAOLG("Devolução LG", 8),
    DEVOLVIDA("Devolvida", 9);
    
    private String descricao;
    private int ordem;

    private StatusEstoque( String descricao, int ordem )
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
