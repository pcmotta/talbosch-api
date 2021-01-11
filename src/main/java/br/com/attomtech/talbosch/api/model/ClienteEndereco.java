package br.com.attomtech.talbosch.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.attomtech.talbosch.api.model.abstracts.Endereco;

@Table(name = "endereco")
@Entity
public class ClienteEndereco extends Endereco
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;

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
        ClienteEndereco other = (ClienteEndereco)obj;
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
        return "ClienteEndereco [codigo=" + codigo + ", cliente=" + cliente + ", logradouro=" + logradouro + ", numero="
                + numero + ", complemento=" + complemento + ", bairro=" + bairro + ", municipio=" + municipio
                + ", estado=" + estado + ", cep=" + cep + ", proximidade=" + proximidade + "]";
    }
}
