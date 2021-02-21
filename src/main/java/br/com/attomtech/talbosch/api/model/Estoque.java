package br.com.attomtech.talbosch.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.abstracts.Model;
import br.com.attomtech.talbosch.api.model.enums.StatusEstoque;
import br.com.attomtech.talbosch.api.model.enums.TipoEstoque;

@Table(name = "estoque")
@Entity
public class Estoque extends Model implements Cloneable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull(message = "O código da peça é obrigatório")
    @ManyToOne
    @JoinColumn(name = "codigo_peca")
    private Peca peca;

    @ManyToOne
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ordem_servico")
    private OrdemServico ordemServico;
    private String       rnn;
    
    @JsonIgnoreProperties("pecas")
    @ManyToOne
    @JoinColumn(name = "pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "codigo_tecnico")
    private Tecnico    tecnico;
    private String     modelo;
    private BigDecimal valor;

    @NotNull(message = "O tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoEstoque tipo;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    private StatusEstoque status;

    @ManyToOne
    @JoinColumn(name = "agendado_por")
    private Usuario agendadoPor;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate agendadoPara;

    @JsonIgnoreProperties("estoque")
    @OneToMany(mappedBy = "estoque", targetEntity = EstoqueObservacao.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstoqueObservacao> observacoes = new ArrayList<EstoqueObservacao>( );

    @JsonIgnoreProperties("estoque")
    @OneToMany(mappedBy = "estoque", targetEntity = EstoqueTelefone.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstoqueTelefone> telefones = new ArrayList<EstoqueTelefone>( );

    @Override
    public Estoque clone( ) throws NegocioException
    {
        Estoque estoque = null;
        try
        {
            estoque = (Estoque)super.clone( );
            estoque.setObservacoes( getObservacoes( ).stream( ).map( o -> o.clone( ) ).collect( Collectors.toList( ) ) );
            estoque.setTelefones( getTelefones( ).stream( ).map( t -> t.clone( ) ).collect( Collectors.toList( ) ) );
        }
        catch( CloneNotSupportedException e )
        {
            throw new NegocioException( "Erro ao clonar estoque" );
        }
        
        return estoque;
    }
    
    public Long getCodigo( )
    {
        return codigo;
    }

    public Peca getPeca( )
    {
        return peca;
    }

    public Cliente getCliente( )
    {
        return cliente;
    }

    public OrdemServico getOrdemServico( )
    {
        return ordemServico;
    }

    public String getRnn( )
    {
        return rnn;
    }

    public Pedido getPedido( )
    {
        return pedido;
    }

    public Tecnico getTecnico( )
    {
        return tecnico;
    }

    public String getModelo( )
    {
        return modelo;
    }

    public BigDecimal getValor( )
    {
        return valor;
    }

    public TipoEstoque getTipo( )
    {
        return tipo;
    }

    public StatusEstoque getStatus( )
    {
        return status;
    }

    public Usuario getAgendadoPor( )
    {
        return agendadoPor;
    }

    public LocalDate getAgendadoPara( )
    {
        return agendadoPara;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public void setPeca( Peca peca )
    {
        this.peca = peca;
    }

    public void setCliente( Cliente cliente )
    {
        this.cliente = cliente;
    }

    public void setOrdemServico( OrdemServico ordemServico )
    {
        this.ordemServico = ordemServico;
    }

    public void setRnn( String rnn )
    {
        this.rnn = rnn;
    }

    public void setPedido( Pedido pedido )
    {
        this.pedido = pedido;
    }

    public void setTecnico( Tecnico tecnico )
    {
        this.tecnico = tecnico;
    }

    public void setModelo( String modelo )
    {
        this.modelo = modelo;
    }

    public void setValor( BigDecimal valor )
    {
        this.valor = valor;
    }

    public void setTipo( TipoEstoque tipo )
    {
        this.tipo = tipo;
    }

    public void setStatus( StatusEstoque status )
    {
        this.status = status;
    }

    public void setAgendadoPor( Usuario agendadoPor )
    {
        this.agendadoPor = agendadoPor;
    }

    public void setAgendadoPara( LocalDate agendadoPara )
    {
        this.agendadoPara = agendadoPara;
    }

    public List<EstoqueObservacao> getObservacoes( )
    {
        return observacoes;
    }

    public List<EstoqueTelefone> getTelefones( )
    {
        return telefones;
    }

    public void setObservacoes( List<EstoqueObservacao> observacoes )
    {
        this.observacoes = observacoes;
    }

    public void setTelefones( List<EstoqueTelefone> telefones )
    {
        this.telefones = telefones;
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
        Estoque other = (Estoque)obj;
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
        return "Estoque [codigo=" + codigo + ", peca=" + peca + ", cliente=" + cliente + ", ordemServico="
                + ordemServico + ", rnn=" + rnn + ", pedido=" + pedido + ", tecnico=" + tecnico + ", modelo=" + modelo 
                + ", valor=" + valor + ", tipo=" + tipo + ", status=" + status + ", agendadoPor="
                + agendadoPor + ", agendadoEm=" + agendadoPara + ", observacoes=" + observacoes + ", telefones="
                + telefones + "]";
    }
}
