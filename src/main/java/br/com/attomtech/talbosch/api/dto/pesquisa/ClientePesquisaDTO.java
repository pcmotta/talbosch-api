package br.com.attomtech.talbosch.api.dto.pesquisa;

import br.com.attomtech.talbosch.api.model.Model;
import br.com.attomtech.talbosch.api.model.enums.TipoPessoa;

public class ClientePesquisaDTO implements Model
{
    private Long    codigo;
    private String  nome;
    private Long    documento;
    private boolean isPessoaFisica;
    private boolean isBandeiraVermelha;
    
    public ClientePesquisaDTO( Long codigo, String nome, Long documento, TipoPessoa tipo, boolean isBandeiraVermelha )
    {
        this.codigo = codigo;
        this.nome = nome;
        this.documento = documento;
        this.isPessoaFisica = tipo == TipoPessoa.FISICA;
        this.isBandeiraVermelha = isBandeiraVermelha;
    }

    public Long getCodigo( )
    {
        return codigo;
    }

    public String getNome( )
    {
        return nome;
    }

    public Long getDocumento( )
    {
        return documento;
    }

    public boolean isPessoaFisica( )
    {
        return isPessoaFisica;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public void setNome( String nome )
    {
        this.nome = nome;
    }

    public void setDocumento( Long documento )
    {
        this.documento = documento;
    }

    public void setPessoaFisica( boolean isPessoaFisica )
    {
        this.isPessoaFisica = isPessoaFisica;
    }

    public boolean isBandeiraVermelha( )
    {
        return isBandeiraVermelha;
    }

    public void setBandeiraVermelha( boolean isBandeiraVermelha )
    {
        this.isBandeiraVermelha = isBandeiraVermelha;
    }
}
