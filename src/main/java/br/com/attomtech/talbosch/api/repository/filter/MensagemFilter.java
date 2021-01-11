package br.com.attomtech.talbosch.api.repository.filter;

import java.time.LocalDate;

import br.com.attomtech.talbosch.api.model.Usuario;

public class MensagemFilter
{
    public static final String USUARIOORIGEM   = "usuarioOrigem",
                               USUARIODESTINO  = "usuarioDestino",
                               DATA            = "data",
                               LIDO            = "lido",
                               DELETADOORIGEM  = "deletadoOrigem",
                               DELETADODESTINO = "deletadoDestino";
    
    private String    loginUsuarioOrigem;
    private Usuario   usuarioLogado;
    private Usuario   usuarioOrigem;
    private Usuario   usuarioDestino;
    private LocalDate dataDe;
    private LocalDate dataAte;
    private Boolean   lido;

    public Usuario getUsuarioOrigem( )
    {
        return usuarioOrigem;
    }

    public void setUsuarioOrigem( Usuario usuarioOrigem )
    {
        this.usuarioOrigem = usuarioOrigem;
    }

    public Usuario getUsuarioDestino( )
    {
        return usuarioDestino;
    }

    public void setUsuarioDestino( Usuario usuarioDestino )
    {
        this.usuarioDestino = usuarioDestino;
    }

    public LocalDate getDataDe( )
    {
        return dataDe;
    }

    public void setDataDe( LocalDate dataDe )
    {
        this.dataDe = dataDe;
    }

    public LocalDate getDataAte( )
    {
        return dataAte;
    }

    public void setDataAte( LocalDate dataAte )
    {
        this.dataAte = dataAte;
    }

    public Boolean getLido( )
    {
        return lido;
    }

    public void setLido( Boolean lido )
    {
        this.lido = lido;
    }

    public String getLoginUsuarioOrigem( )
    {
        return loginUsuarioOrigem;
    }

    public void setLoginUsuarioOrigem( String loginUsuarioOrigem )
    {
        this.loginUsuarioOrigem = loginUsuarioOrigem;
    }

    @Override
    public String toString( )
    {
        return "MensagemFilter [loginUsuarioOrigem=" + loginUsuarioOrigem + ", usuarioOrigem=" + usuarioOrigem
                + ", usuarioDestino=" + usuarioDestino + ", dataDe=" + dataDe + ", dataAte=" + dataAte + ", lido="
                + lido + "]";
    }

    public Usuario getUsuarioLogado( )
    {
        return usuarioLogado;
    }

    public void setUsuarioLogado( Usuario usuarioLogado )
    {
        this.usuarioLogado = usuarioLogado;
    }
}
