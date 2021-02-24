package br.com.attomtech.talbosch.api.dto.pesquisa;

import java.time.LocalDate;

import br.com.attomtech.talbosch.api.model.Model;
import br.com.attomtech.talbosch.api.model.enums.StatusEstoque;
import br.com.attomtech.talbosch.api.model.enums.TipoEstoque;

public class EstoquePesquisaDTO implements Model
{
    private Long      codigo;
    private String    peca;
    private String    cliente;
    private Long      ordemServico;
    private LocalDate agendadoPara;
    private String    tipo;
    private String    tipoColor;
    private String    status;

    public EstoquePesquisaDTO( Long codigo, String codigoPeca, String descricaoPeca, String cliente, Long ordemServico,
            LocalDate agendadoPara, TipoEstoque tipo, StatusEstoque status )
    {
        this.codigo = codigo;
        this.peca = String.format( "%s - %s", codigoPeca, descricaoPeca );
        this.cliente = cliente;
        this.ordemServico = ordemServico;
        this.agendadoPara = agendadoPara;
        this.tipo = tipo.getDescricao( );
        this.tipoColor = tipo.getColor( );
        this.status = status.getDescricao( );
    }

    public Long getCodigo( )
    {
        return codigo;
    }

    public String getPeca( )
    {
        return peca;
    }

    public String getCliente( )
    {
        return cliente;
    }

    public Long getOrdemServico( )
    {
        return ordemServico;
    }

    public LocalDate getAgendadoPara( )
    {
        return agendadoPara;
    }

    public String getTipo( )
    {
        return tipo;
    }

    public String getStatus( )
    {
        return status;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public void setPeca( String peca )
    {
        this.peca = peca;
    }

    public void setCliente( String cliente )
    {
        this.cliente = cliente;
    }

    public void setOrdemServico( Long ordemServico )
    {
        this.ordemServico = ordemServico;
    }

    public void setAgendadoPara( LocalDate agendadoPara )
    {
        this.agendadoPara = agendadoPara;
    }

    public void setTipo( String tipo )
    {
        this.tipo = tipo;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public String getTipoColor( )
    {
        return tipoColor;
    }

    public void setTipoColor( String tipoColor )
    {
        this.tipoColor = tipoColor;
    }
}
