package br.com.attomtech.talbosch.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.attomtech.talbosch.api.model.abstracts.Endereco;

@Table(name = "ordem_servico_endereco")
@Entity
public class OrdemEndereco extends Endereco implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @OneToOne
    @JoinColumn(name = "ordem_servico")
    private OrdemServico ordem;

    public OrdemServico getOrdem( )
    {
        return ordem;
    }

    public void setOrdem( OrdemServico ordem )
    {
        this.ordem = ordem;
    }
    
    @Override
    public int hashCode( )
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( ordem == null ) ? 0 : ordem.hashCode( ) );
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
        OrdemEndereco other = (OrdemEndereco)obj;
        if( ordem == null )
        {
            if( other.ordem != null )
                return false;
        }
        else if( !ordem.equals( other.ordem ) )
            return false;
        return true;
    }
}
