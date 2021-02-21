package br.com.attomtech.talbosch.api.model;

import java.time.LocalDate;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.abstracts.Model;
import br.com.attomtech.talbosch.api.model.enums.StatusOrdem;
import br.com.attomtech.talbosch.api.model.enums.TipoOrdem;

@Table(name = "ordem_servico")
@Entity
public class OrdemServico extends Model implements Cloneable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    @JsonIgnoreProperties(value = { "cpfCnpj", "tipoPessoa", "email", "inscricaoEstadual", "inscricaoMunicipal",
            "pessoaContato", "genero", "tipoCliente", "bandeiraVermelha", "motivo", "ativo", "auditoria", "enderecos",
            "telefones" })
    @ManyToOne
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private TipoOrdem tipo;
    private LocalDate dataChamada;
    private LocalDate dataAtendimento;

    @JsonIgnoreProperties(value = { "login", "ultimoAcesso", "ativo", "permissoes", "auditoria" })
    @ManyToOne
    @JoinColumn(name = "codigo_atendente")
    private Usuario atendente;
    private String  observacao;
    private String  baixa;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataBaixa;

    @JsonIgnoreProperties(value = { "ativo" })
    @ManyToOne
    @JoinColumn(name = "codigo_tecnico")
    private Tecnico tecnico;

    @Enumerated(EnumType.STRING)
    private StatusOrdem status;

    @NotNull(message = "Endereço é obrigatório")
    @JsonIgnoreProperties("ordem")
    @OneToOne(mappedBy = "ordem", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = OrdemEndereco.class)
    private OrdemEndereco endereco;

    @NotNull(message = "Produto é obrigatório")
    @JsonIgnoreProperties("ordem")
    @OneToOne(mappedBy = "ordem", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = OrdemProduto.class)
    private OrdemProduto produto;

    @JsonIgnoreProperties("ordem")
    @OneToMany(mappedBy = "ordem", targetEntity = OrdemValor.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemValor> valores;
    
    @JsonIgnoreProperties("ordem")
    @OneToMany(mappedBy = "ordem", targetEntity = OrdemAtendimento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemAtendimento> atendimentos;

    @JsonIgnoreProperties("ordem")
    @OneToMany(mappedBy = "ordem", targetEntity = OrdemAndamento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemAndamento> andamentos;
    
    @JsonIgnoreProperties(value = { "cliente", "ordemServico", "rnn", "pedido", "tecnico", "modelo", "valor", "tipo", "agendadoPor", "observacoes", "telefones" })
    @Transient
    private List<Estoque> pecas;

    @JsonIgnore
    public boolean temValor( )
    {
        return valores != null && !valores.isEmpty( );
    }

    @JsonIgnore
    public boolean temAndamento( )
    {
        return andamentos != null && !andamentos.isEmpty( );
    }
    
    @JsonIgnore
    public boolean temAtendimento( )
    {
        return atendimentos != null && !atendimentos.isEmpty( );
    }

    public Long getNumero( )
    {
        return numero;
    }

    public void setNumero( Long numero )
    {
        this.numero = numero;
    }

    public Cliente getCliente( )
    {
        return cliente;
    }

    public void setCliente( Cliente cliente )
    {
        this.cliente = cliente;
    }

    public TipoOrdem getTipo( )
    {
        return tipo;
    }

    public void setTipo( TipoOrdem tipo )
    {
        this.tipo = tipo;
    }

    public LocalDate getDataChamada( )
    {
        return dataChamada;
    }

    public void setDataChamada( LocalDate dataChamada )
    {
        this.dataChamada = dataChamada;
    }

    public LocalDate getDataAtendimento( )
    {
        return dataAtendimento;
    }

    public void setDataAtendimento( LocalDate dataAtendimento )
    {
        this.dataAtendimento = dataAtendimento;
    }

    public Usuario getAtendente( )
    {
        return atendente;
    }

    public void setAtendente( Usuario atendente )
    {
        this.atendente = atendente;
    }

    public String getObservacao( )
    {
        return observacao;
    }

    public void setObservacao( String observacao )
    {
        this.observacao = observacao;
    }

    public String getBaixa( )
    {
        return baixa;
    }

    public void setBaixa( String baixa )
    {
        this.baixa = baixa;
    }

    public LocalDate getDataBaixa( )
    {
        return dataBaixa;
    }

    public void setDataBaixa( LocalDate dataBaixa )
    {
        this.dataBaixa = dataBaixa;
    }

    public Tecnico getTecnico( )
    {
        return tecnico;
    }

    public void setTecnico( Tecnico tecnico )
    {
        this.tecnico = tecnico;
    }

    public StatusOrdem getStatus( )
    {
        return status;
    }

    public void setStatus( StatusOrdem status )
    {
        this.status = status;
    }

    public OrdemEndereco getEndereco( )
    {
        return endereco;
    }

    public void setEndereco( OrdemEndereco endereco )
    {
        this.endereco = endereco;
    }

    public OrdemProduto getProduto( )
    {
        return produto;
    }

    public void setProduto( OrdemProduto produto )
    {
        this.produto = produto;
    }

    public List<OrdemValor> getValores( )
    {
        return valores;
    }

    public void setValores( List<OrdemValor> valores )
    {
        this.valores = valores;
    }

    public List<OrdemAtendimento> getAtendimentos( )
    {
        return atendimentos;
    }

    public void setAtendimentos( List<OrdemAtendimento> atendimentos )
    {
        this.atendimentos = atendimentos;
    }

    public List<OrdemAndamento> getAndamentos( )
    {
        return andamentos;
    }

    public void setAndamentos( List<OrdemAndamento> andamentos )
    {
        this.andamentos = andamentos;
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
    public OrdemServico clone( ) throws NegocioException
    {
        OrdemServico ordem = null;
        try
        {
            ordem = (OrdemServico)super.clone( );
            ordem.setEndereco( getEndereco( ).clone( ) );
            ordem.setProduto( getProduto( ).clone( ) );
            ordem.setValores( getValores( ).stream( ).map( v -> v.clone( ) ).collect( Collectors.toList( ) ) );
            ordem.setAndamentos( getAndamentos( ).stream( ).map( a -> a.clone( ) ).collect( Collectors.toList( ) ) );
        }
        catch( CloneNotSupportedException e )
        {
            throw new NegocioException( "Erro ao clonar ordem de serviço" );
        }
        
        return ordem;
    }

    @Override
    public int hashCode( )
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( numero == null ) ? 0 : numero.hashCode( ) );
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
        OrdemServico other = (OrdemServico)obj;
        if( numero == null )
        {
            if( other.numero != null )
                return false;
        }
        else if( !numero.equals( other.numero ) )
            return false;
        return true;
    }

    @Override
    public String toString( )
    {
        return "OrdemServico [numero=" + numero + ", cliente=" + cliente + ", tipo=" + tipo
                + ", dataChamada=" + dataChamada + ", dataAtendimento=" + dataAtendimento + ", atendente=" + atendente
                + ", observacao=" + observacao + ", baixa=" + baixa + ", dataBaixa=" + dataBaixa + ", tecnico="
                + tecnico + ", status=" + status + ", endereco=" + endereco + ", produto=" + produto + ", valores="
                + valores + ", andamentos=" + andamentos + "]";
    }
}
