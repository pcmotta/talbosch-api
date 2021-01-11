package br.com.attomtech.talbosch.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.attomtech.talbosch.api.model.abstracts.Telefone;

@Table(name = "estoque_telefone")
@Entity
public class EstoqueTelefone extends Telefone
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    
    @NotNull(message = "O código do estoque é obrigatório")
    @ManyToOne
    @JoinColumn(name = "codigo_estoque")
    private Estoque estoque;

    public Long getCodigo( )
    {
        return codigo;
    }

    public Estoque getEstoque( )
    {
        return estoque;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public void setEstoque( Estoque estoque )
    {
        this.estoque = estoque;
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
        EstoqueTelefone other = (EstoqueTelefone)obj;
        if( codigo == null )
        {
            if( other.codigo != null )
                return false;
        }
        else if( !codigo.equals( other.codigo ) )
            return false;
        return true;
    }

    @Override
    public String toString( )
    {
        return "EstoqueTelefone [codigo=" + codigo + ", estoque=" + estoque + ", numero=" + numero + ", operadora="
                + operadora + "]";
    }
}
