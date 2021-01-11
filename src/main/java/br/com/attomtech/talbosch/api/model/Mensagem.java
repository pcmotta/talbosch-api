package br.com.attomtech.talbosch.api.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table(name = "mensagem")
@Entity
public class Mensagem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @JsonIgnoreProperties(value = { "login", "ultimoAcesso", "ativo", "permissoes", "auditoria" })
    @ManyToOne
    @JoinColumn(name = "codigo_usuario_origem")
    private Usuario usuarioOrigem;

    @JsonIgnoreProperties(value = { "login", "ultimoAcesso", "ativo", "permissoes", "auditoria" })
    @NotNull(message = "Usuário Destino é obrigatório")
    @ManyToOne
    @JoinColumn(name = "codigo_usuario_destino")
    private Usuario usuarioDestino;

    @NotNull(message = "Mensagem é obrigatória")
    private String  mensagem;
    private boolean lido = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime data;
    private boolean       deletadoOrigem  = false;
    private boolean       deletadoDestino = false;

    @JsonIgnore
    public boolean excluida( )
    {
        return deletadoDestino && deletadoOrigem;
    }

    @JsonIgnore
    public boolean naoLida( )
    {
        return !this.lido;
    }

    public Long getCodigo( )
    {
        return codigo;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public Usuario getUsuarioOrigem( )
    {
        return usuarioOrigem;
    }

    public void setUsuarioOrigem( Usuario usuarioOrigem )
    {
        this.usuarioOrigem = usuarioOrigem;
    }

    public Usuario getUsuarioDestino( )
    {
        return usuarioDestino;
    }

    public void setUsuarioDestino( Usuario usuarioDestino )
    {
        this.usuarioDestino = usuarioDestino;
    }

    public String getMensagem( )
    {
        return mensagem;
    }

    public void setMensagem( String mensagem )
    {
        this.mensagem = mensagem;
    }

    public boolean isLido( )
    {
        return lido;
    }

    public void setLido( boolean lido )
    {
        this.lido = lido;
    }

    public LocalDateTime getData( )
    {
        return data;
    }

    public void setData( LocalDateTime data )
    {
        this.data = data;
    }

    public boolean isDeletadoOrigem( )
    {
        return deletadoOrigem;
    }

    public void setDeletadoOrigem( boolean deletadoOrigem )
    {
        this.deletadoOrigem = deletadoOrigem;
    }

    public boolean isDeletadoDestino( )
    {
        return deletadoDestino;
    }

    public void setDeletadoDestino( boolean deletadoDestino )
    {
        this.deletadoDestino = deletadoDestino;
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
        Mensagem other = (Mensagem)obj;
        if( codigo == null )
        {
            if( other.codigo != null )
                return false;
        }
        else if( !codigo.equals( other.codigo ) )
            return false;
        return true;
    }

    @Override
    public String toString( )
    {
        return "Mensagem [codigo=" + codigo + ", usuarioOrigem=" + usuarioOrigem + ", usuarioDestino=" + usuarioDestino
                + ", mensagem=" + mensagem + ", lido=" + lido + ", data=" + data + ", deletadoOrigem=" + deletadoOrigem
                + ", deletadoDestino=" + deletadoDestino + "]";
    }
}
