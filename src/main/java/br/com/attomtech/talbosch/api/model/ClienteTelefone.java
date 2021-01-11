package br.com.attomtech.talbosch.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import br.com.attomtech.talbosch.api.model.abstracts.Telefone;

@Table(name = "telefone")
@Entity
public class ClienteTelefone extends Telefone
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    
    @ManyToOne
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;
    
    @Length(max = 100, message = "Observações deve ter no máximo 100 caracteres")
    private String observacoes;

    public Long getCodigo( )
    {
        return codigo;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public Cliente getCliente( )
    {
        return cliente;
    }

    public void setCliente( Cliente cliente )
    {
        this.cliente = cliente;
    }

    public String getObservacoes( )
    {
        return observacoes;
    }

    public void setObservacoes( String observacoes )
    {
        this.observacoes = observacoes;
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
        ClienteTelefone other = (ClienteTelefone)obj;
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
        return "ClienteTelefone [codigo=" + codigo + ", cliente=" + cliente + ", observacoes=" + observacoes
                + ", numero=" + numero + ", operadora=" + operadora + "]";
    }
}
