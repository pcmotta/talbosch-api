package br.com.attomtech.talbosch.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;

public class LogAtividadesFilter
{
    public static final String AREA         = "area",
                               ACAO         = "acao",
                               CODIGOOBJETO = "codigoObjeto",
                               CODIGOPECA   = "codigoPeca",
                               DATA         = "data",
                               USUARIO      = "usuario";
    
    private AreaLog area;
    private AcaoLog acao;
    private String  codigoObjeto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAte;
    private Usuario   usuario;

    public AreaLog getArea( )
    {
        return area;
    }

    public AcaoLog getAcao( )
    {
        return acao;
    }

    public String getCodigoObjeto( )
    {
        return codigoObjeto;
    }

    public LocalDate getDataDe( )
    {
        return dataDe;
    }

    public LocalDate getDataAte( )
    {
        return dataAte;
    }

    public Usuario getUsuario( )
    {
        return usuario;
    }

    public void setArea( AreaLog area )
    {
        this.area = area;
    }

    public void setAcao( AcaoLog acao )
    {
        this.acao = acao;
    }

    public void setCodigoObjeto( String codigoObjeto )
    {
        this.codigoObjeto = codigoObjeto;
    }

    public void setDataDe( LocalDate dataDe )
    {
        this.dataDe = dataDe;
    }

    public void setDataAte( LocalDate dataAte )
    {
        this.dataAte = dataAte;
    }

    public void setUsuario( Usuario usuario )
    {
        this.usuario = usuario;
    }
}
