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

@Table(name = "estoque_observacao")
@Entity
public class EstoqueObservacao
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    
    @NotNull(message = "O código do estoque é obrigatório")
    @ManyToOne
    @JoinColumn(name = "codigo_estoque")
    private Estoque estoque;
    
    @NotNull(message = "O texto é obrigatório")
    private String texto;
    
    @NotNull(message = "O usuário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;
    
    @NotNull(message = "A data é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime data;

    public Long getCodigo( )
    {
        return codigo;
    }

    public Estoque getEstoque( )
    {
        return estoque;
    }

    public String getTexto( )
    {
        return texto;
    }

    public Usuario getUsuario( )
    {
        return usuario;
    }

    public LocalDateTime getData( )
    {
        return data;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public void setEstoque( Estoque estoque )
    {
        this.estoque = estoque;
    }

    public void setTexto( String texto )
    {
        this.texto = texto;
    }

    public void setUsuario( Usuario usuario )
    {
        this.usuario = usuario;
    }

    public void setData( LocalDateTime data )
    {
        this.data = data;
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
        EstoqueObservacao other = (EstoqueObservacao)obj;
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
        return "EstoqueObservacao [codigo=" + codigo + ", estoque=" + estoque + ", texto=" + texto + ", usuario="
                + usuario + ", data=" + data + "]";
    }
}
