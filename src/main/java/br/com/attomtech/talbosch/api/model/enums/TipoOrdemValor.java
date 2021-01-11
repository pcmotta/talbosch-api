package br.com.attomtech.talbosch.api.model.enums;

public enum TipoOrdemValor 
{
    MAODEOBRA("Mão de Obra"),
    MANUTENCAO("Manutenção"),
    DESCONTO("Desconto"),
    INSTALACAO("Instalação"),
    ROLAMENTOS("Rolamentos");
    
    private String descricao;

    private TipoOrdemValor( String descricao )
    {
        this.descricao = descricao;
    }

    public String getDescricao( )
    {
        return descricao;
    }
}
