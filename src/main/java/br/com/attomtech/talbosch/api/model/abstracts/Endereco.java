package br.com.attomtech.talbosch.api.model.abstracts;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class Endereco implements Cloneable
{
    @NotNull(message = "Logradouro é obrigatório")
    protected String logradouro;

    @Size(max = 20, message = "Número deve ter no máximo 20 caracteres")
    protected String numero;

    @Size(max = 50, message = "Complemento deve ter no máximo 50 caracteres")
    protected String complemento;

    @Size(max = 40, message = "Complemento deve ter no máximo 40 caracteres")
    protected String bairro;

    @Size(max = 40, message = "Município deve ter no máximo 40 caracteres")
    protected String municipio;

    @Size(max = 2, message = "Estado deve ter no máximo 2 caracteres")
    protected String estado;

    protected Long   cep;
    protected String proximidade;
    
    public String getLogradouro( )
    {
        return logradouro;
    }

    public void setLogradouro( String logradouro )
    {
        this.logradouro = logradouro;
    }

    public String getNumero( )
    {
        return numero;
    }

    public void setNumero( String numero )
    {
        this.numero = numero;
    }

    public String getComplemento( )
    {
        return complemento;
    }

    public void setComplemento( String complemento )
    {
        this.complemento = complemento;
    }

    public String getBairro( )
    {
        return bairro;
    }

    public void setBairro( String bairro )
    {
        this.bairro = bairro;
    }

    public String getMunicipio( )
    {
        return municipio;
    }

    public void setMunicipio( String municipio )
    {
        this.municipio = municipio;
    }

    public String getEstado( )
    {
        return estado;
    }

    public void setEstado( String estado )
    {
        this.estado = estado;
    }

    public Long getCep( )
    {
        return cep;
    }

    public void setCep( Long cep )
    {
        this.cep = cep;
    }

    public String getProximidade( )
    {
        return proximidade;
    }

    public void setProximidade( String proximidade )
    {
        this.proximidade = proximidade;
    }
}
