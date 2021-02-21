package br.com.attomtech.talbosch.api.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.abstracts.Model;
import br.com.attomtech.talbosch.api.model.enums.StatusPedido;
import br.com.attomtech.talbosch.api.model.enums.TipoEstoque;

@Entity
@Table(name = "pedido")
public class Pedido extends Model implements Cloneable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String pedido;

    @Column(name = "nota_fiscal")
    private String notaFiscal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "emissao_nota_fiscal")
    private LocalDate emissaoNotaFiscal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "chegada_nota_fiscal")
    private LocalDate chegadaNotaFiscal;

    @NotNull(message = "Pedido Por é obrigatório")
    @JsonIgnoreProperties(value = { "login", "ultimoAcesso", "ativo", "permissoes", "auditoria" })
    @ManyToOne
    @JoinColumn(name = "pedido_por")
    private Usuario pedidoPor;
    
    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoEstoque tipo;
    
    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    
    @Transient
    private List<Estoque> pecas;

    public Long getCodigo( )
    {
        return codigo;
    }

    public String getPedido( )
    {
        return pedido;
    }

    public String getNotaFiscal( )
    {
        return notaFiscal;
    }

    public LocalDate getData( )
    {
        return data;
    }

    public LocalDate getEmissaoNotaFiscal( )
    {
        return emissaoNotaFiscal;
    }

    public LocalDate getChegadaNotaFiscal( )
    {
        return chegadaNotaFiscal;
    }

    public Usuario getPedidoPor( )
    {
        return pedidoPor;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public void setPedido( String pedido )
    {
        this.pedido = pedido;
    }

    public void setNotaFiscal( String notaFiscal )
    {
        this.notaFiscal = notaFiscal;
    }

    public void setData( LocalDate data )
    {
        this.data = data;
    }

    public void setEmissaoNotaFiscal( LocalDate emissaoNotaFiscal )
    {
        this.emissaoNotaFiscal = emissaoNotaFiscal;
    }

    public void setChegadaNotaFiscal( LocalDate chegadaNotaFiscal )
    {
        this.chegadaNotaFiscal = chegadaNotaFiscal;
    }

    public void setPedidoPor( Usuario pedidoPor )
    {
        this.pedidoPor = pedidoPor;
    }

    public TipoEstoque getTipo( )
    {
        return tipo;
    }

    public StatusPedido getStatus( )
    {
        return status;
    }

    public void setTipo( TipoEstoque tipo )
    {
        this.tipo = tipo;
    }

    public void setStatus( StatusPedido status )
    {
        this.status = status;
    }

    public List<Estoque> getPecas( )
    {
        return pecas;
    }

    public void setPecas( List<Estoque> pecas )
    {
        this.pecas = pecas;
    }
    
    @Override
    public Pedido clone( ) throws NegocioException
    {
        Pedido pedido = null;
        try
        {
            pedido = (Pedido)super.clone( );
        }
        catch( CloneNotSupportedException e )
        {
            throw new NegocioException( "Erro ao clonar Pedido", e );
        }
        
        return pedido;
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
        Pedido other = (Pedido)obj;
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
        return "Pedido [codigo=" + codigo + ", pedido=" + pedido + ", notaFiscal=" + notaFiscal + ", data=" + data
                + ", emissaoNotaFiscal=" + emissaoNotaFiscal + ", chegadaNotaFiscal=" + chegadaNotaFiscal
                + ", pedidoPor=" + pedidoPor + "]";
    }
}
