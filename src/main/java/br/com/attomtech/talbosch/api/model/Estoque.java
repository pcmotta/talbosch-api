package br.com.attomtech.talbosch.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

import br.com.attomtech.talbosch.api.model.abstracts.Model;
import br.com.attomtech.talbosch.api.model.enums.StatusEstoque;
import br.com.attomtech.talbosch.api.model.enums.TipoEstoque;

@Table(name = "estoque")
@Entity
public class Estoque extends Model
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
    private String       rnm;
    private String       pedido;

    @ManyToOne
    @JoinColumn(name = "codigo_tecnico")
    private Tecnico    tecnico;
    private String     notaFiscal;
    private LocalDate  emissaoNotaFiscal;
    private LocalDate  chegadaNotaFiscal;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime agendadoPara;

    @JsonIgnoreProperties("estoque")
    @OneToMany(mappedBy = "estoque", targetEntity = EstoqueObservacao.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstoqueObservacao> observacoes = new ArrayList<EstoqueObservacao>( );

    @JsonIgnoreProperties("estoque")
    @OneToMany(mappedBy = "estoque", targetEntity = EstoqueTelefone.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstoqueTelefone> telefones = new ArrayList<EstoqueTelefone>( );

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

    public String getRnm( )
    {
        return rnm;
    }

    public String getPedido( )
    {
        return pedido;
    }

    public Tecnico getTecnico( )
    {
        return tecnico;
    }

    public String getNotaFiscal( )
    {
        return notaFiscal;
    }

    public LocalDate getEmissaoNotaFiscal( )
    {
        return emissaoNotaFiscal;
    }

    public LocalDate getChegadaNotaFiscal( )
    {
        return chegadaNotaFiscal;
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

    public LocalDateTime getAgendadoPara( )
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

    public void setRnm( String rnm )
    {
        this.rnm = rnm;
    }

    public void setPedido( String pedido )
    {
        this.pedido = pedido;
    }

    public void setTecnico( Tecnico tecnico )
    {
        this.tecnico = tecnico;
    }

    public void setNotaFiscal( String notaFiscal )
    {
        this.notaFiscal = notaFiscal;
    }

    public void setEmissaoNotaFiscal( LocalDate emissaoNotaFiscal )
    {
        this.emissaoNotaFiscal = emissaoNotaFiscal;
    }

    public void setChegadaNotaFiscal( LocalDate chegadaNotaFiscal )
    {
        this.chegadaNotaFiscal = chegadaNotaFiscal;
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

    public void setAgendadoPara( LocalDateTime agendadoPara )
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
                + ordemServico + ", rnm=" + rnm + ", pedido=" + pedido + ", tecnico=" + tecnico + ", notaFiscal="
                + notaFiscal + ", emissaoNotaFiscal=" + emissaoNotaFiscal + ", chegadaNotaFiscal=" + chegadaNotaFiscal
                + ", modelo=" + modelo + ", valor=" + valor + ", tipo=" + tipo + ", status=" + status + ", agendadoPor="
                + agendadoPor + ", agendadoEm=" + agendadoPara + ", observacoes=" + observacoes + ", telefones="
                + telefones + "]";
    }
}
