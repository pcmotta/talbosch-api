package br.com.attomtech.talbosch.api.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.model.OrdemServico;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.model.enums.StatusPedido;

public class PedidoFilter
{
    public static final String CODIGO       = "codigo",
                               PEDIDO       = "pedido",
                               NOTAFISCAL   = "notaFiscal",
                               DATA         = "data",
                               PEDIDOPOR    = "pedidoPor",
                               STATUS       = "status",
                               CLIENTE      = "cliente",
                               ORDEMSERVICO = "ordemServico";
    
    private Long   codigo;
    private String pedido;
    private String notaFiscal;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAte;
    private Usuario pedidoPor;
    private StatusPedido status;
    private Cliente cliente;
    private OrdemServico ordemServico;

    public Long getCodigo( )
    {
        return codigo;
    }

    public String getPedido( )
    {
        return pedido;
    }

    public String getNotaFiscal( )
    {
        return notaFiscal;
    }

    public LocalDate getDataDe( )
    {
        return dataDe;
    }

    public LocalDate getDataAte( )
    {
        return dataAte;
    }

    public Usuario getPedidoPor( )
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

    public void setNotaFiscal( String notaFiscal )
    {
        this.notaFiscal = notaFiscal;
    }

    public void setDataDe( LocalDate dataDe )
    {
        this.dataDe = dataDe;
    }

    public void setDataAte( LocalDate dataAte )
    {
        this.dataAte = dataAte;
    }

    public void setPedidoPor( Usuario pedidoPor )
    {
        this.pedidoPor = pedidoPor;
    }

    public StatusPedido getStatus( )
    {
        return status;
    }

    public void setStatus( StatusPedido status )
    {
        this.status = status;
    }

    public Cliente getCliente( )
    {
        return cliente;
    }

    public OrdemServico getOrdemServico( )
    {
        return ordemServico;
    }

    public void setCliente( Cliente cliente )
    {
        this.cliente = cliente;
    }

    public void setOrdemServico( OrdemServico ordem )
    {
        this.ordemServico = ordem;
    }

}
