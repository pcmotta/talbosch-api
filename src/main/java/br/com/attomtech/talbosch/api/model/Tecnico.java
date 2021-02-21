package br.com.attomtech.talbosch.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.abstracts.Model;

@Table(name = "tecnico")
@Entity
public class Tecnico extends Model implements Cloneable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull(message = "O Nome é obrigatório")
    private String  nome;
    private boolean ativo;
    
    @Override
    public Tecnico clone( ) throws NegocioException
    {
        Tecnico tecnico = null;
        try
        {
            tecnico = (Tecnico)super.clone( );
        }
        catch( CloneNotSupportedException e )
        {
            throw new NegocioException( "Erro ao clonar técnico" );
        }
        
        return tecnico;
    }

    public Long getCodigo( )
    {
        return codigo;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public String getNome( )
    {
        return nome;
    }

    public void setNome( String nome )
    {
        this.nome = nome;
    }

    public boolean isAtivo( )
    {
        return ativo;
    }

    public void setAtivo( boolean ativo )
    {
        this.ativo = ativo;
    }

    @Override
    public int hashCode( )
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( codigo == null ) ? 0 : codigo.hashCode( ) );
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
        Tecnico other = (Tecnico)obj;
        if( codigo == null )
        {
            if( other.codigo != null )
                return false;
        }
        else if( !codigo.equals( other.codigo ) )
            return false;
        return true;
    }
}
