package br.com.attomtech.talbosch.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table(name = "modelo")
@Entity
public class Modelo
{
    @JsonIgnoreProperties("peca")
    @Id
    @Embedded
    private ModeloId id = new ModeloId( );

    public ModeloId getId( )
    {
        return id;
    }

    public void setId( ModeloId id )
    {
        this.id = id;
    }
    
    public void setModelo( String modelo )
    {
        id.setModelo( modelo );
    }
    
    public void setPeca( String codigo )
    {
        id.getPeca( ).setCodigo( codigo );
    }
    
    public void setPeca( Peca peca )
    {
        id.setPeca( peca );
    }

    @Override
    public int hashCode( )
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode( ) );
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
        Modelo other = (Modelo)obj;
        if( id == null )
        {
            if( other.id != null )
                return false;
        }
        else if( !id.equals( other.id ) )
            return false;
        return true;
    }
}
