package br.com.attomtech.talbosch.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Embeddable
public class Auditoria implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonIgnoreProperties(value = { "codigo", "login", "ultimoAcesso", "ativo", "permissoes", "auditoria" })
    @ManyToOne
    @JoinColumn(name = "incluido_por")
    private Usuario       incluidoPor;
    private LocalDateTime incluidoEm;

    @JsonIgnoreProperties(value = { "codigo", "login", "ultimoAcesso", "ativo", "permissoes", "auditoria" })
    @ManyToOne
    @JoinColumn(name = "alterado_por")
    private Usuario       alteradoPor;
    private LocalDateTime alteradoEm;

    public Usuario getIncluidoPor( )
    {
        return incluidoPor;
    }

    public void setIncluidoPor( Usuario incluidoPor )
    {
        this.incluidoPor = incluidoPor;
    }

    public LocalDateTime getIncluidoEm( )
    {
        return incluidoEm;
    }

    public void setIncluidoEm( LocalDateTime incluidoEm )
    {
        this.incluidoEm = incluidoEm;
    }

    public Usuario getAlteradoPor( )
    {
        return alteradoPor;
    }

    public void setAlteradoPor( Usuario alteradoPor )
    {
        this.alteradoPor = alteradoPor;
    }

    public LocalDateTime getAlteradoEm( )
    {
        return alteradoEm;
    }

    public void setAlteradoEm( LocalDateTime alteradoEm )
    {
        this.alteradoEm = alteradoEm;
    }
}
