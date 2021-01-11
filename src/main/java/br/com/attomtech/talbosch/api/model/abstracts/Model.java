package br.com.attomtech.talbosch.api.model.abstracts;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import br.com.attomtech.talbosch.api.model.Auditoria;

@MappedSuperclass
public abstract class Model
{
    @Embedded
    protected Auditoria auditoria = new Auditoria( );
    
    public Auditoria getAuditoria( )
    {
        return auditoria;
    }

    public void setAuditoria( Auditoria auditoria )
    {
        this.auditoria = auditoria;
    }
}
