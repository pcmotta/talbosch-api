package br.com.attomtech.talbosch.api.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.abstracts.Model;
import br.com.attomtech.talbosch.api.model.enums.Genero;
import br.com.attomtech.talbosch.api.model.enums.TipoCliente;
import br.com.attomtech.talbosch.api.model.enums.TipoPessoa;

@Table(name = "cliente")
@Entity
public class Cliente extends Model implements Cloneable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull(message = "O Nome é obrigatório")
    private String nome;
    private Long   cpfCnpj;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @Email
    private String email;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private String pessoaContato;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;
    private boolean     bandeiraVermelha;
    private String      motivo;
    private boolean     ativo = true;

    @JsonIgnoreProperties(value = "cliente")
    @OneToMany(mappedBy = "cliente", targetEntity = ClienteEndereco.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteEndereco> enderecos = new ArrayList<ClienteEndereco>( );

    @JsonIgnoreProperties(value = "cliente")
    @OneToMany(mappedBy = "cliente", targetEntity = ClienteTelefone.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteTelefone> telefones = new ArrayList<ClienteTelefone>( );

    @JsonIgnoreProperties(value = "cliente")
    @OneToMany(mappedBy = "cliente", targetEntity = ClienteProduto.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteProduto> produtos = new ArrayList<ClienteProduto>( );
    
    @JsonIgnoreProperties(value = { "cliente", "rnm", "atendente", "observacao", "endereco", "produto", "valores", "andamentos", "pecas", "auditoria" })
    @Transient
    private List<OrdemServico> ordensServico;

    @Override
    public Cliente clone( ) throws NegocioException
    {
        Cliente cliente = null;
        try
        {
            cliente = (Cliente)super.clone( );
            cliente.setEnderecos( getEnderecos( ).stream( ).map( e -> e.clone( ) ).collect( Collectors.toList( ) ) );
            cliente.setTelefones( getTelefones( ).stream( ).map( t -> t.clone( ) ).collect( Collectors.toList( ) ) );
            cliente.setProdutos( getProdutos( ).stream( ).map( p -> p.clone( ) ).collect( Collectors.toList( ) ) );
        }
        catch( CloneNotSupportedException e )
        {
            throw new NegocioException( "Erro ao clonar Cliente" );
        }
        
        return cliente;
    }
    
    @JsonIgnore
    public boolean isPessoaFisica( )
    {
        return this.tipoPessoa == TipoPessoa.FISICA;
    }

    @JsonIgnore
    public boolean isInativo( )
    {
        return !this.ativo;
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

    public Long getCpfCnpj( )
    {
        return cpfCnpj;
    }

    public void setCpfCnpj( Long cpfCnpj )
    {
        this.cpfCnpj = cpfCnpj;
    }

    public TipoPessoa getTipoPessoa( )
    {
        return tipoPessoa;
    }

    public void setTipoPessoa( TipoPessoa tipoPessoa )
    {
        this.tipoPessoa = tipoPessoa;
    }

    public String getEmail( )
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getInscricaoEstadual( )
    {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual( String inscricaoEstadual )
    {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getInscricaoMunicipal( )
    {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal( String inscricaoMunicipal )
    {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getPessoaContato( )
    {
        return pessoaContato;
    }

    public void setPessoaContato( String pessoaContato )
    {
        this.pessoaContato = pessoaContato;
    }

    public Genero getGenero( )
    {
        return genero;
    }

    public void setGenero( Genero genero )
    {
        this.genero = genero;
    }

    public TipoCliente getTipoCliente( )
    {
        return tipoCliente;
    }

    public void setTipoCliente( TipoCliente tipoCliente )
    {
        this.tipoCliente = tipoCliente;
    }

    public boolean isBandeiraVermelha( )
    {
        return bandeiraVermelha;
    }

    public void setBandeiraVermelha( boolean bandeiraVermelha )
    {
        this.bandeiraVermelha = bandeiraVermelha;
    }

    public String getMotivo( )
    {
        return motivo;
    }

    public void setMotivo( String motivo )
    {
        this.motivo = motivo;
    }

    public boolean isAtivo( )
    {
        return ativo;
    }

    public void setAtivo( boolean ativo )
    {
        this.ativo = ativo;
    }

    public List<ClienteEndereco> getEnderecos( )
    {
        return enderecos;
    }

    public void setEnderecos( List<ClienteEndereco> enderecos )
    {
        this.enderecos = enderecos;
    }

    public List<ClienteTelefone> getTelefones( )
    {
        return telefones;
    }

    public void setTelefones( List<ClienteTelefone> telefones )
    {
        this.telefones = telefones;
    }

    public List<ClienteProduto> getProdutos( )
    {
        return produtos;
    }

    public void setProdutos( List<ClienteProduto> produtos )
    {
        this.produtos = produtos;
    }
    
    public List<OrdemServico> getOrdensServico( )
    {
        return ordensServico;
    }

    public void setOrdensServico( List<OrdemServico> ordensServico )
    {
        this.ordensServico = ordensServico;
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
        Cliente other = (Cliente)obj;
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
        return "Cliente [codigo=" + codigo + ", nome=" + nome + ", cpfCnpj=" + cpfCnpj + ", tipoPessoa=" + tipoPessoa
                + ", email=" + email + ", inscricaoEstadual=" + inscricaoEstadual + ", inscricaoMunicipal="
                + inscricaoMunicipal + ", pessoaContato=" + pessoaContato + ", genero=" + genero + ", tipoCliente="
                + tipoCliente + ", bandeiraVermelha=" + bandeiraVermelha + ", motivo=" + motivo + ", ativo=" + ativo
                + ", enderecos=" + enderecos + ", telefones=" + telefones + ", produtos=" + produtos + "]";
    }
}
