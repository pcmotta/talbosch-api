package br.com.attomtech.talbosch.api.reports;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.attomtech.talbosch.api.model.Tecnico;

public class EstoqueTecnicoReport
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;
    private boolean   isGarantia;
    private boolean   isForaGarantia;
    private boolean   isBuffer;
    private Tecnico   tecnico;
    
    public boolean isTodos( )
    {
        return (isGarantia && isForaGarantia && isBuffer) ||
               (!isGarantia && !isForaGarantia && !isBuffer);
    }

    public LocalDate getData( )
    {
        return data;
    }

    public boolean isGarantia( )
    {
        return isGarantia;
    }

    public boolean isForaGarantia( )
    {
        return isForaGarantia;
    }

    public boolean isBuffer( )
    {
        return isBuffer;
    }

    public Tecnico getTecnico( )
    {
        return tecnico;
    }

    public void setData( LocalDate data )
    {
        this.data = data;
    }

    public void setGarantia( boolean isGarantia )
    {
        this.isGarantia = isGarantia;
    }

    public void setForaGarantia( boolean isForaGarantia )
    {
        this.isForaGarantia = isForaGarantia;
    }

    public void setBuffer( boolean isBuffer )
    {
        this.isBuffer = isBuffer;
    }

    public void setTecnico( Tecnico tecnico )
    {
        this.tecnico = tecnico;
    }
}
