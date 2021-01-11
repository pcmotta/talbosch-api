package br.com.attomtech.talbosch.api.repository.filter;

import java.time.LocalDate;

import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.model.Tecnico;
import br.com.attomtech.talbosch.api.model.enums.Aparelho;
import br.com.attomtech.talbosch.api.model.enums.Fabricante;
import br.com.attomtech.talbosch.api.model.enums.StatusOrdem;
import br.com.attomtech.talbosch.api.model.enums.TipoOrdem;

public class OrdemServicoFilter
{
    public static final String NUMERO          = "numero",
                               CLIENTE         = "cliente",
                               TIPO            = "tipo",
                               DATACHAMADA     = "dataChamada",
                               DATAATENDIMENTO = "dataAtendimento",
                               DATABAIXA       = "dataBaixa",
                               TECNICO         = "tecnico",
                               STATUS          = "status",
                               ENDERECO        = "endereco",
                               LOGRADOURO      = "logradouro",
                               BAIRRO          = "bairro",
                               PRODUTO         = "produto",
                               APARELHO        = "aparelho",
                               FABRICANTE      = "fabricante";
                                
    
    private Long        numero;
    private Cliente     cliente;
    private TipoOrdem   tipo;
    private LocalDate   dataChamadaDe;
    private LocalDate   dataChamadaAte;
    private LocalDate   dataAtendimentoDe;
    private LocalDate   dataAtendimentoAte;
    private LocalDate   dataBaixaDe;
    private LocalDate   dataBaixaAte;
    private Tecnico     tecnico;
    private StatusOrdem status;
    private String      logradouro;
    private String      bairro;
    private Aparelho    aparelho;
    private Fabricante  fabricante;

    public Long getNumero( )
    {
        return numero;
    }

    public void setNumero( Long numero )
    {
        this.numero = numero;
    }

    public Cliente getCliente( )
    {
        return cliente;
    }

    public void setCliente( Cliente cliente )
    {
        this.cliente = cliente;
    }

    public TipoOrdem getTipo( )
    {
        return tipo;
    }

    public void setTipo( TipoOrdem tipo )
    {
        this.tipo = tipo;
    }

    public LocalDate getDataChamadaDe( )
    {
        return dataChamadaDe;
    }

    public void setDataChamadaDe( LocalDate dataChamadaDe )
    {
        this.dataChamadaDe = dataChamadaDe;
    }

    public LocalDate getDataChamadaAte( )
    {
        return dataChamadaAte;
    }

    public void setDataChamadaAte( LocalDate dataChamadaAte )
    {
        this.dataChamadaAte = dataChamadaAte;
    }

    public LocalDate getDataAtendimentoDe( )
    {
        return dataAtendimentoDe;
    }

    public void setDataAtendimentoDe( LocalDate dataAtendimentoDe )
    {
        this.dataAtendimentoDe = dataAtendimentoDe;
    }

    public LocalDate getDataAtendimentoAte( )
    {
        return dataAtendimentoAte;
    }

    public void setDataAtendimentoAte( LocalDate dataAtendimentoAte )
    {
        this.dataAtendimentoAte = dataAtendimentoAte;
    }

    public LocalDate getDataBaixaDe( )
    {
        return dataBaixaDe;
    }

    public void setDataBaixaDe( LocalDate dataBaixaDe )
    {
        this.dataBaixaDe = dataBaixaDe;
    }

    public LocalDate getDataBaixaAte( )
    {
        return dataBaixaAte;
    }

    public void setDataBaixaAte( LocalDate dataBaixaAte )
    {
        this.dataBaixaAte = dataBaixaAte;
    }

    public Tecnico getTecnico( )
    {
        return tecnico;
    }

    public void setTecnico( Tecnico tecnico )
    {
        this.tecnico = tecnico;
    }

    public StatusOrdem getStatus( )
    {
        return status;
    }

    public void setStatus( StatusOrdem status )
    {
        this.status = status;
    }

    public String getLogradouro( )
    {
        return logradouro;
    }

    public void setLogradouro( String logradouro )
    {
        this.logradouro = logradouro;
    }

    public String getBairro( )
    {
        return bairro;
    }

    public void setBairro( String bairro )
    {
        this.bairro = bairro;
    }

    public Aparelho getAparelho( )
    {
        return aparelho;
    }

    public void setAparelho( Aparelho aparelho )
    {
        this.aparelho = aparelho;
    }

    public Fabricante getFabricante( )
    {
        return fabricante;
    }

    public void setFabricante( Fabricante fabricante )
    {
        this.fabricante = fabricante;
    }
}
