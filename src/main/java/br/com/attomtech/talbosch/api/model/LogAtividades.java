package br.com.attomtech.talbosch.api.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;

@Entity
@Table(name = "log_de_atividades")
public class LogAtividades implements Model
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Enumerated(EnumType.STRING)
    private AreaLog area;

    @Enumerated(EnumType.STRING)
    private AcaoLog       acao;
    
    @Column(name = "codigo_objeto")
    private Long          codigoObjeto;
    
    @Column(name = "codigo_peca")
    private String        codigoPeca;
    private LocalDateTime data;

    @JsonIgnoreProperties(value = { "codigo", "login", "ultimoAcesso", "ativo", "permissoes", "auditoria" })
    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;
    private String texto;
    
    public LogAtividades( )
    {
        
    }
    
    public LogAtividades( AcaoLog acao, AreaLog area, Long codigoObjeto, String codigoPeca, Usuario usuario, String texto )
    {
        this.acao = acao;
        this.area = area;
        this.codigoObjeto = codigoObjeto;
        this.codigoPeca = codigoPeca;
        this.usuario = usuario;
        this.texto = texto;
        this.data = LocalDateTime.ofInstant( Instant.now( ), ZoneId.of( "GMT-3" ) );
    }
    
    public Long getCodigo( )
    {
        return codigo;
    }

    public AreaLog getArea( )
    {
        return area;
    }

    public AcaoLog getAcao( )
    {
        return acao;
    }

    public Long getCodigoObjeto( )
    {
        return codigoObjeto;
    }

    public String getCodigoPeca( )
    {
        return codigoPeca;
    }

    public LocalDateTime getData( )
    {
        return data;
    }

    public Usuario getUsuario( )
    {
        return usuario;
    }

    public String getTexto( )
    {
        return texto;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public void setArea( AreaLog area )
    {
        this.area = area;
    }

    public void setAcao( AcaoLog acao )
    {
        this.acao = acao;
    }

    public void setCodigoObjeto( Long codigoObjeto )
    {
        this.codigoObjeto = codigoObjeto;
    }

    public void setCodigoPeca( String codigoPeca )
    {
        this.codigoPeca = codigoPeca;
    }

    public void setData( LocalDateTime data )
    {
        this.data = data;
    }

    public void setUsuario( Usuario usuario )
    {
        this.usuario = usuario;
    }

    public void setTexto( String texto )
    {
        this.texto = texto;
    }

    @Override
    public int hashCode( )
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( codigo == null ) ? 0 : codigo.hashCode( ) );
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass( ) != obj.getClass( ) )
            return false;
        LogAtividades other = (LogAtividades)obj;
        if( codigo == null )
        {
            if( other.codigo != null )
                return false;
        }
        else if( !codigo.equals( other.codigo ) )
            return false;
        return true;
    }

}
