package br.com.attomtech.talbosch.api.model.enums;

public enum StatusEstoque 
{
    ACHEGAR("A Chegar"),
    EMESTOQUE("Em Estoque"),
    USADA("Usada"),
    ACORDO("Acordo"),
    APEDIR("A pedir"),
    BALCAO("Balcão"),
    COELHONETO("Coelho Neto"),
    CAMPOGRANDE("Campo Grande"),
    DEVOLUCAOLG("Devolução LG"),
    DEVOLVIDA("Devolvida");
    
    private String descricao;

    private StatusEstoque( String descricao )
    {
        this.descricao = descricao;
    }

    public String getDescricao( )
    {
        return descricao;
    }
}
