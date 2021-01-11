package br.com.attomtech.talbosch.api.repository.filter;

import br.com.attomtech.talbosch.api.model.enums.TipoPessoa;

public class ClienteFilter
{
    public static final String NOME             = "nome",
                               CPFCNPJ          = "cpfCnpj",
                               TIPOPESSOA       = "tipoPessoa",
                               EMAIL            = "email",
                               BANDEIRAVERMELHA = "bandeiraVermelha",
                               LOGRADOURO       = "logradouro",
                               BAIRRO           = "bairro",
                               CEP              = "cep",
                               NUMEROTELEFONE   = "numero";
    
    public static final String ENDERECO = "enderecos",
                               TELEFONE = "telefones";
    
    private String     nome;
    private Long       cpfCnpj;
    private TipoPessoa tipoPessoa;
    private String     email;
    private Boolean    bandeiraVermelha;
    private String     logradouro;
    private String     bairro;
    private Long       cep;
    private Long       numeroTelefone;

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

    public Boolean getBandeiraVermelha( )
    {
        return bandeiraVermelha;
    }

    public void setBandeiraVermelha( Boolean bandeiraVermelha )
    {
        this.bandeiraVermelha = bandeiraVermelha;
    }

    public String getLogradouro( )
    {
        return logradouro;
    }

    public void setLogradouro( String logradouro )
    {
        this.logradouro = logradouro;
    }

    public String getBairro( )
    {
        return bairro;
    }

    public void setBairro( String bairro )
    {
        this.bairro = bairro;
    }

    public Long getCep( )
    {
        return cep;
    }

    public void setCep( Long cep )
    {
        this.cep = cep;
    }

    public Long getNumeroTelefone( )
    {
        return numeroTelefone;
    }

    public void setNumeroTelefone( Long numero )
    {
        this.numeroTelefone = numero;
    }
}
