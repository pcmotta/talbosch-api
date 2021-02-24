package br.com.attomtech.talbosch.api.dto.pesquisa;

import java.time.LocalDate;

import br.com.attomtech.talbosch.api.model.Model;
import br.com.attomtech.talbosch.api.model.enums.Aparelho;
import br.com.attomtech.talbosch.api.model.enums.Fabricante;
import br.com.attomtech.talbosch.api.model.enums.StatusOrdem;
import br.com.attomtech.talbosch.api.model.enums.TipoOrdem;
import br.com.attomtech.talbosch.api.utils.LabelValue;

public class OrdemServicoPesquisaDTO implements Model
{
    private Long       numero;
    private String     cliente;
    private LocalDate  dataAtendimento;
    private LabelValue aparelho;
    private LabelValue fabricante;
    private LabelValue status;
    private LabelValue tipo;

    public OrdemServicoPesquisaDTO( Long numero, String cliente, LocalDate dataAtendimento, Aparelho aparelho,
            Fabricante fabricante, StatusOrdem status, TipoOrdem tipo )
    {
        this.numero = numero;
        this.cliente = cliente;
        this.dataAtendimento = dataAtendimento;
        this.aparelho = new LabelValue( aparelho.toString( ), aparelho.getDescricao( ) );
        this.fabricante = new LabelValue( fabricante.toString( ), fabricante.getNome( ) );
        this.status = new LabelValue( status.toString( ), status.getDescricao( ) );
        this.tipo = new LabelValue( tipo.toString( ), tipo.getDescricao( ) );
    }

    public Long getNumero( )
    {
        return numero;
    }

    public String getCliente( )
    {
        return cliente;
    }

    public void setNumero( Long numero )
    {
        this.numero = numero;
    }

    public void setCliente( String cliente )
    {
        this.cliente = cliente;
    }

    public LabelValue getAparelho( )
    {
        return aparelho;
    }

    public LabelValue getFabricante( )
    {
        return fabricante;
    }

    public void setAparelho( LabelValue aparelho )
    {
        this.aparelho = aparelho;
    }

    public void setFabricante( LabelValue fabricante )
    {
        this.fabricante = fabricante;
    }

    public LocalDate getDataAtendimento( )
    {
        return dataAtendimento;
    }

    public void setDataAtendimento( LocalDate dataAtendimento )
    {
        this.dataAtendimento = dataAtendimento;
    }

    public LabelValue getStatus( )
    {
        return status;
    }

    public LabelValue getTipo( )
    {
        return tipo;
    }

    public void setStatus( LabelValue status )
    {
        this.status = status;
    }

    public void setTipo( LabelValue tipo )
    {
        this.tipo = tipo;
    }
}
