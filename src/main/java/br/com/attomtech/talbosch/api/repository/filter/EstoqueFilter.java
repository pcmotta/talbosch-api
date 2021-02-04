package br.com.attomtech.talbosch.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.attomtech.talbosch.api.model.enums.StatusEstoque;
import br.com.attomtech.talbosch.api.model.enums.TipoEstoque;

public class EstoqueFilter
{
    public static final String PECA          = "peca",
                               PECACODIGO    = "codigo",
                               CLIENTE       = "cliente",
                               CLIENTENOME   = "nome",
                               OS            = "ordemServico",
                               OSNUMERO      = "numero",
                               TECNICO       = "tecnico",
                               TECNICOCODIGO = "codigo",
                               TIPO          = "tipo",
                               STATUS        = "status",
                               AGENDADOPARA  = "agendadoPara";
    
    private String        peca;
    private String        cliente;
    private Long          ordemServico;
    private Long          tecnico;
    private TipoEstoque   tipo;
    private StatusEstoque status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate agendadoParaDe;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate agendadoParaAte;

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

    public Long getTecnico( )
    {
        return tecnico;
    }

    public TipoEstoque getTipo( )
    {
        return tipo;
    }

    public StatusEstoque getStatus( )
    {
        return status;
    }

    public LocalDate getAgendadoParaDe( )
    {
        return agendadoParaDe;
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

    public void setTecnico( Long tecnico )
    {
        this.tecnico = tecnico;
    }

    public void setTipo( TipoEstoque tipo )
    {
        this.tipo = tipo;
    }

    public void setStatus( StatusEstoque status )
    {
        this.status = status;
    }

    public void setAgendadoParaDe( LocalDate agendadoPara )
    {
        this.agendadoParaDe = agendadoPara;
    }

    public LocalDate getAgendadoParaAte( )
    {
        return agendadoParaAte;
    }

    public void setAgendadoParaAte( LocalDate agendadoParaAte )
    {
        this.agendadoParaAte = agendadoParaAte;
    }
}
