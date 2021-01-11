package br.com.attomtech.talbosch.api.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class ModeloId implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "codigo_peca")
    private Peca peca;

    @NotNull(message = "Modelo é obrigatório")
    private String modelo;
    
    public ModeloId( )
    {
        peca = new Peca( );
    }

    public Peca getPeca( )
    {
        return peca;
    }

    public void setPeca( Peca peca )
    {
        this.peca = peca;
    }

    public String getModelo( )
    {
        return modelo;
    }

    public void setModelo( String modelo )
    {
        this.modelo = modelo;
    }

    @Override
    public int hashCode( )
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( modelo == null ) ? 0 : modelo.hashCode( ) );
        result = prime * result + ( ( peca == null ) ? 0 : peca.hashCode( ) );
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass( ) != obj.getClass( ) )
            return false;
        ModeloId other = (ModeloId)obj;
        if( modelo == null )
        {
            if( other.modelo != null )
                return false;
        }
        else if( !modelo.equals( other.modelo ) )
            return false;
        if( peca == null )
        {
            if( other.peca != null )
                return false;
        }
        else if( !peca.equals( other.peca ) )
            return false;
        return true;
    }
}
