package br.com.attomtech.talbosch.api.model.enums;

public enum Aparelho 
{
    MAQUINA( "Máquina de Lavar" ),
    SIDEBYSIDE( "Side by Side" ),
    DUPLEX( "Duplex" ),
    ADEGA( "Adéga" ),
    SPLIT( "Split" ),
    CONDARJANELA( "Cond. de Ar de Janela" ),
    UMIDIFICADOR( "Umidificador" ),
    TV( "TV" ),
    CONDARPORTATIL( "Cond. de Ar Portátil" ),
    CLIMATIZADOR( "Climatizador" ),
    MICROONDAS( "Micro-ondas" ),
    NOTEBOOK( "Notebook" ),
    COMPUTADOR( "Computador" ),
    FORNO( "Forno" ),
    COOKTOP( "Cooktop" ),
    MONITOR( "Monitor" );
    
    private String descricao;
    
    Aparelho( String descricao )
    {
        this.descricao = descricao;
    }
    
    public String getDescricao( )
    {
        return this.descricao;
    }
}
