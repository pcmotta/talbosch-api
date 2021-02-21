package br.com.attomtech.talbosch.api.model.abstracts;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@MappedSuperclass
public abstract class Telefone implements Cloneable
{
    @NotNull(message = "Número é obrigatório")
    protected Long numero;
    
    @Length(max = 20, message = "Operadora deve ter no máximo 20 caracteres")
    protected String operadora;

    public Long getNumero( )
    {
        return numero;
    }

    public String getOperadora( )
    {
        return operadora;
    }

    public void setNumero( Long numero )
    {
        this.numero = numero;
    }

    public void setOperadora( String operadora )
    {
        this.operadora = operadora;
    }
}
