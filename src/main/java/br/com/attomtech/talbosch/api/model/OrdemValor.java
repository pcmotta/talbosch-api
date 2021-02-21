package br.com.attomtech.talbosch.api.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.enums.TipoOrdemValor;

@Table(name = "ordem_servico_valor")
@Entity
public class OrdemValor implements Cloneable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    
    @ManyToOne
    @JoinColumn(name = "ordem_servico")
    private OrdemServico ordem;
    
    @NotNull(message = "Tipo de Valor é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoOrdemValor tipo;
    
    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;
    
    @Override
    public OrdemValor clone( ) throws NegocioException
    {
        OrdemValor valor = null;
        try
        {
            valor = (OrdemValor)super.clone( );
        }
        catch( CloneNotSupportedException e )
        {
            throw new NegocioException( "Erro ao clonar valor de ordem de serviço" );
        }
        
        return valor;
    }

    public Long getCodigo( )
    {
        return codigo;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public OrdemServico getOrdem( )
    {
        return ordem;
    }

    public void setOrdem( OrdemServico ordem )
    {
        this.ordem = ordem;
    }

    public TipoOrdemValor getTipo( )
    {
        return tipo;
    }

    public void setTipo( TipoOrdemValor tipo )
    {
        this.tipo = tipo;
    }

    public BigDecimal getValor( )
    {
        return valor;
    }

    public void setValor( BigDecimal valor )
    {
        this.valor = valor;
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
        OrdemValor other = (OrdemValor)obj;
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
