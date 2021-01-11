package br.com.attomtech.talbosch.api.model.enums;

public enum Fabricante 
{
    LG( "LG" ),
    ELGIN( "Elgin" ),
    CCE( "CCE" ),
    HBUSTER( "H-Buster" ),
    SAMSUNG( "Samsung" ),
    TOSHIBA( "Toshiba" ),
    PHILCO( "Philco" ),
    SONY( "Sony" ),
    PANASONIC( "Panasonic" ),
    TCL( "TCL" ),
    AOC( "AOC" );
    
    private String nome;
    
    Fabricante( String nome )
    {
        this.nome = nome;
    }
    
    public String getNome( )
    {
        return this.nome;
    }
}
