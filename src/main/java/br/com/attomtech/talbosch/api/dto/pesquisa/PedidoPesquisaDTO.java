package br.com.attomtech.talbosch.api.dto.pesquisa;

import java.time.LocalDate;

import br.com.attomtech.talbosch.api.model.Model;
import br.com.attomtech.talbosch.api.model.enums.StatusPedido;
import br.com.attomtech.talbosch.api.utils.LabelValue;

public class PedidoPesquisaDTO implements Model
{
    private Long       codigo;
    private String     pedido;
    private LocalDate  data;
    private LabelValue status;
    private String     pedidoPor;
    private String     cliente;
    private Long       ordemServico;
    
    public PedidoPesquisaDTO( Long codigo, String pedido, LocalDate data, StatusPedido status, String pedidoPor, String cliente, Long ordemServico )
    {
        this.codigo = codigo;
        this.pedido = pedido;
        this.data = data;
        this.status = new LabelValue( status.toString( ), status.getDescricao( ) );
        this.pedidoPor = pedidoPor;
        this.cliente = cliente;
        this.ordemServico = ordemServico;
    }

    public Long getCodigo( )
    {
        return codigo;
    }

    public String getPedido( )
    {
        return pedido;
    }

    public LocalDate getData( )
    {
        return data;
    }

    public LabelValue getStatus( )
    {
        return status;
    }

    public String getPedidoPor( )
    {
        return pedidoPor;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public void setPedido( String pedido )
    {
        this.pedido = pedido;
    }

    public void setData( LocalDate data )
    {
        this.data = data;
    }

    public void setStatus( LabelValue status )
    {
        this.status = status;
    }

    public void setPedidoPor( String pedidoPor )
    {
        this.pedidoPor = pedidoPor;
    }

    public String getCliente( )
    {
        return cliente;
    }

    public Long getOrdemServico( )
    {
        return ordemServico;
    }

    public void setCliente( String cliente )
    {
        this.cliente = cliente;
    }

    public void setOrdemServico( Long ordemServico )
    {
        this.ordemServico = ordemServico;
    }
}
